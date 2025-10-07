// java
package test.nz.ac.wgtn.swen225.lc.fuzz;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import nz.ac.wgtn.swen225.lc.app.util.Input;

import java.awt.*; // GraphicsEnvironment used to detect headless CI environments
import java.time.Instant;
import java.time.Duration; // used for level visitation timing
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.domain.Position;

/**
 * Fuzz tests for the application.
 * Generates random sequences of actions to test the robustness of the application module.
 * These tests will be used to help identify and trigger exceptions as the following:
 * - NullPointerException
 * - ArrayIndexOutOfBoundsException
 * - IllegalArgumentException
 * - IllegalStateException
 * - ConcurrentModificationException
 * - StackOverflowError
 * - AssertionError
 * @author Al-Bara Al-Sakkaf
 */
public class FuzzTest {

    @Test
    @Timeout(60)  // max 1 min runtime
    public void testLevel1() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(), "Headless CI - skipping GUI fuzzer");
        runFuzzer(1);
    }

    @Test
    @Timeout(60)
    public void testLevel2() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(), "Headless CI - skipping GUI fuzzer");
        runFuzzer(2);
    }


    /**
     * Core fuzzing loop: starts target level then randomly feeds inputs through the public controller API.
     * Strategy (simple for Integration Day):
     *  - Bias towards movement inputs (exploration) ~70%(lowk easier to setup)
     *  - Occasionally pause/resume / continue
     *  - Occasionally restart the same level or switch level (test transition thing)
     *  - Random short sleeps to let Swing event update
     * Stops on: uncaught runtime exception/assertion error i.e. where i tryna catch the errors/find them
     * No assertions: any thrown exception other than UnsupportedOperationException / IllegalArgumentException
     * (which can be legitimate for unsupported inputs in states) and is considered a finding.
     */
    private void runFuzzer(int initialLevel) {
        // Configurable duration (default 15s) via -Dfuzz.ms=15000 ; seed via -Dfuzz.seed ; log sequence via -Dfuzz.logSequence
        long durationMillis = Long.getLong("fuzz.ms", 15_000L);
        long seed = Long.getLong("fuzz.seed", System.currentTimeMillis());
        Random rnd = new Random(seed); // reproducible when seed provided
        boolean logSeed = Boolean.getBoolean("fuzz.logSeed");
        if(logSeed) System.out.println("[FUZZ] seed="+seed+" durationMs="+durationMillis+" startLevel="+initialLevel);

    AppController controller = AppController.of();
    safeStartNewGame(controller, initialLevel);

        // Action buckets
        List<Input> movement = List.of(Input.MOVE_UP, Input.MOVE_DOWN, Input.MOVE_LEFT, Input.MOVE_RIGHT);
        List<Input> meta = List.of(Input.PAUSE, Input.RESUME, Input.CONTINUE, Input.SAVE);
        List<Input> levelLoads = List.of(Input.LOAD_LEVEL_1, Input.LOAD_LEVEL_2);
    boolean allowExit = Boolean.getBoolean("fuzz.allowExit");
    List<Input> rare = allowExit ? List.of(Input.SAVE, Input.EXIT) : List.of(Input.SAVE); // avoid killing JVM early

        List<Input> executed = new ArrayList<>(1024); // store sequence for reproduction

    Instant end = Instant.now().plusMillis(durationMillis);
    int moves = 0;
    boolean sawLevel1 = (initialLevel == 1);
    boolean sawLevel2 = (initialLevel == 2);
    boolean slowMode = Boolean.getBoolean("fuzz.slow");
        try {
            while(Instant.now().isBefore(end)) {
                // If victory/defeat reached, restart random level.
                String stateName = controller.state().getClass().getSimpleName();
                if(stateName.contains("Victory") || stateName.contains("Defeat")) {
                    int nextLevel = ThreadLocalRandom.current().nextBoolean() ? 1 : 2;
                    safeStartNewGame(controller, nextLevel);
                    if(nextLevel==1) sawLevel1 = true; else sawLevel2 = true;
                }

                // Force visiting both levels: halfway through time, if one level never seen, switch.
                long remaining = Duration.between(Instant.now(), end).toMillis();
                if(remaining < (durationMillis/2) && !(sawLevel1 && sawLevel2)) {
                    if(!sawLevel1) { safeStartNewGame(controller,1); sawLevel1 = true; }
                    else if(!sawLevel2) { safeStartNewGame(controller,2); sawLevel2 = true; }
                }

                Input next = pickNext(rnd, movement, meta, levelLoads, rare);
                executed.add(next);
                try {
                    controller.handleInput(next);
                } catch(UnsupportedOperationException | IllegalArgumentException ignored) {
                    // expected sometimes depending on state
                } catch(RuntimeException rte) {
                    if(rte.getMessage()!=null && rte.getMessage().contains("background sound")) {
                        System.err.println("[FUZZ][AUDIO-WARN] Suppressed background sound failure mid-run: " + rte.getMessage());
                    } else throw rte;
                }

                // Periodic logging of treasure count & position every 40 moves or when switching level
                if((moves % 40)==0) {
                    logProgress(controller, moves);
                }

                moves++;
                // Adaptive pacing: quicker early exploration, slow down a bit later so EDT can repaint.
                if(slowMode) {
                    sleepQuiet(40 + rnd.nextInt(60));
                } else if((moves % 50)==0) {
                    sleepQuiet(10 + rnd.nextInt(25));
                } else if((moves % 10)==0) {
                    sleepQuiet(1 + rnd.nextInt(4));
                }
            }
        } catch(RuntimeException | AssertionError e) {
            dumpSequence(seed, executed, e);
            throw e; // rethrow so JUnit marks failure
        } catch(Error err) { // include other serious errors (StackOverflowError, etc.)
            dumpSequence(seed, executed, err);
            throw err;
        }

        if(Boolean.getBoolean("fuzz.alwaysDump")) {
            dumpSequence(seed, executed, null);
        }
    }

    private void logProgress(AppController controller, int moves) {
        try {
            Maze m = controller.domain();
            if(m == null) return;
            int treasures = countTreasures(m);
            Position p = m.getPlayer()!=null? m.getPlayer().getPos():null;
            System.out.printf("[FUZZ][STATE] moves=%d level=%d treasuresRemaining=%d player=%s state=%s%n",
                    moves, controller.level(), treasures, p, controller.state().getClass().getSimpleName());
        } catch(Exception e) {
            System.out.println("[FUZZ][STATE] (error gathering state) " + e.getMessage());
        }
    }

    private int countTreasures(Maze maze) {
        int rows = maze.getRows();
        int cols = maze.getCols();
        int count = 0;
        for(int r=0;r<rows;r++) {
            for(int c=0;c<cols;c++) {
                if("T".equals(maze.getSymbol(new Position(c,r)))) count++;
            }
        }
        return count;
    }

    private void safeStartNewGame(AppController controller, int level) {
        try {
            controller.startNewGame(level);
        } catch(RuntimeException e) {
            // Suppress known audio background issues while still exercising logic.
            if(e.getMessage()!=null && e.getMessage().contains("background sound")) {
                System.err.println("[FUZZ][AUDIO-WARN] Suppressed audio init failure: " + e.getMessage());
            } else throw e;
        }
    }

    private Input pickNext(Random rnd, List<Input> movement, List<Input> meta, List<Input> levelLoads, List<Input> rare) {
        int bucket = rnd.nextInt(100);
        if(bucket < 68) return movement.get(rnd.nextInt(movement.size()));      // ~68%
        if(bucket < 83) return meta.get(rnd.nextInt(meta.size()));             // ~15%
        if(bucket < 94) return levelLoads.get(rnd.nextInt(levelLoads.size())); // ~11%
        return rare.get(rnd.nextInt(rare.size()));                             // ~6%
    }

    private void dumpSequence(long seed, List<Input> executed, Throwable t) {
        System.out.println("===== FUZZ SEQUENCE DUMP =====");
        System.out.println("Seed: " + seed + " length=" + executed.size());
        if(t!=null) System.out.println("Failure: " + t);
        StringBuilder sb = new StringBuilder();
        for(Input i : executed) sb.append(i).append(',');
        System.out.println(sb);
        System.out.println("Re-run with: -Dfuzz.seed="+seed);
        System.out.println("================================");

        // Optional artifact: write a markdown issue draft if enabled
        if(Boolean.getBoolean("fuzz.issue.markdown")) {
            writeIssueMarkdown(seed, executed, t, sb.toString());
        }
    }

    private void writeIssueMarkdown(long seed, List<Input> executed, Throwable t, String csv) {
        java.io.File dir = new java.io.File("target/fuzz-issues");
        if(!dir.exists() && !dir.mkdirs()) {
            System.err.println("[FUZZ][ISSUE] Could not create directory: " + dir);
            return;
        }
        String shortName = (t==null?"no-exception":t.getClass().getSimpleName());
        String fileName = String.format("fuzz-%s-seed-%d.md", shortName, seed);
        java.io.File f = new java.io.File(dir, fileName);
        try(java.io.PrintWriter pw = new java.io.PrintWriter(f, java.nio.charset.StandardCharsets.UTF_8)) {
            pw.println("# Fuzzer Detected Exception : " + shortName);
            pw.println();
            pw.println("## Summary");
            pw.println("Exception during fuzz run: `" + (t==null?"(none)":t.toString()) + "`.");
            pw.println();
            pw.println("## Reproduction");
            pw.println("```bash");
            pw.println("mvn -Dfuzz.seed=" + seed + " -Dfuzz.ms=15000 test");
            pw.println("```");
            pw.println();
            pw.println("## Stack Trace (trimmed)");
            pw.println("```");
            if(t!=null) {
                java.io.StringWriter sw = new java.io.StringWriter();
                t.printStackTrace(new java.io.PrintWriter(sw));
                pw.println(sw.toString());
            } else pw.println("(none)");
            pw.println("```");
            pw.println();
            pw.println("## Input Sequence (CSV)");
            pw.println("```");
            pw.println(csv);
            pw.println("```");
            pw.println();
            pw.println("## Labels");
            pw.println("#detectedByFuzzer");
            pw.println();
            pw.println("## Suggested Next Steps");
            pw.println("- Investigate stack trace in module indicated by top frames.");
            pw.println("- Re-run with same seed to confirm determinism.");
            pw.println("- Add regression unit test if a logic bug is confirmed.");
        } catch(Exception e) {
            System.err.println("[FUZZ][ISSUE] Failed to write issue markdown: " + e);
        }
        System.out.println("[FUZZ][ISSUE] Markdown written: " + f.getPath());
    }

    // Simple helper to manually replay a sequence if you copy it from a dump (comma-separated names)
    // Usage (developer only): call from a temporary @Test with a recorded string.
    @SuppressWarnings("unused")
    private void replay(String csv, int startLevel) {
        AppController controller = AppController.of();
        controller.startNewGame(startLevel);
        for(String tok : csv.split(",")) {
            if(tok.isBlank()) continue;
            try {
                controller.handleInput(Input.valueOf(tok.trim()));
                sleepQuiet(50); // slow for visibility
            } catch(Exception e) {
                System.out.println("Replay halted on input="+tok+" due to " + e);
                break;
            }
        }
    }

    private void sleepQuiet(long millis) {
        // Best-effort sleep that preserves the interrupt flag if interrupted, avoiding swallowed interrupts.
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
