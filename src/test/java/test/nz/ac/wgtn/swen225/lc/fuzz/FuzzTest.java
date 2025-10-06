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
        controller.startNewGame(initialLevel);

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
                    controller.startNewGame(nextLevel);
                    if(nextLevel==1) sawLevel1 = true; else sawLevel2 = true;
                }

                // Force visiting both levels: halfway through time, if one level never seen, switch.
                long remaining = Duration.between(Instant.now(), end).toMillis();
                if(remaining < (durationMillis/2) && !(sawLevel1 && sawLevel2)) {
                    if(!sawLevel1) { controller.startNewGame(1); sawLevel1 = true; }
                    else if(!sawLevel2) { controller.startNewGame(2); sawLevel2 = true; }
                }

                Input next = pickNext(rnd, movement, meta, levelLoads, rare);
                executed.add(next);
                try {
                    controller.handleInput(next);
                } catch(UnsupportedOperationException | IllegalArgumentException ignored) {
                    // expected sometimes depending on state
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
