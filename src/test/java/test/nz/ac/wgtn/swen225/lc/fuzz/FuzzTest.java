// java
package test.nz.ac.wgtn.swen225.lc.fuzz;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.domain.Position;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.awt.GraphicsEnvironment;
import java.time.Instant;
import java.util.*;

/**
 * Heuristic fuzz tests for levels 1 and 2.
 * Focus: exercise domain/app logic, surface unexpected exceptions.
 */
public class FuzzTest {
    /**
     * Single place to control how long each fuzz test runs (ms). Override with -Dfuzz.ms=NNNN if desired.
     * Adjust this constant (or provide the system property) instead of hunting through the method.
     */
    private static final long RUN_MS = Long.getLong("fuzz.ms", 15_000L); // change here e.g. 55_000L for longer sessions
    @Test
    @Timeout(60)
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

    private void runFuzzer(int initialLevel) {
        // Duration for this run (centralised in RUN_MS constant above)
        long durationMillis = RUN_MS;
        long seed = Long.getLong("fuzz.seed", System.currentTimeMillis());
        Random rnd = new Random(seed);
        boolean logSeed = Boolean.getBoolean("fuzz.logSeed");
        boolean slowMode = Boolean.getBoolean("fuzz.slow");
        boolean crossLevels = Boolean.getBoolean("fuzz.crossLevels");
        boolean enablePause = Boolean.parseBoolean(System.getProperty("fuzz.enablePause","false"));

        if (logSeed) {
            System.out.println("[FUZZ] seed=" + seed + " ms=" + durationMillis +
                    " level=" + initialLevel + " crossLevels=" + crossLevels +
                    " enablePause=" + enablePause);
        }

    final int MAX_AUDIO_FAILS = Integer.getInteger("fuzz.maxAudioFails", 5);
    int audioFailCount = 0;

        AppController controller = AppController.of();
        if (!safeStartNewGame(controller, initialLevel)) {
            System.out.println("[FUZZ][ABORT] Could not enter PlayState initially (audio failures).");
            return;
        }

        List<Input> movement = List.of(Input.MOVE_UP, Input.MOVE_DOWN, Input.MOVE_LEFT, Input.MOVE_RIGHT);
        List<Input> meta = enablePause
                ? List.of(Input.RESUME, Input.CONTINUE, Input.PAUSE, Input.SAVE)
                : List.of(Input.RESUME, Input.CONTINUE, Input.SAVE);
        List<Input> levelLoads = crossLevels
                ? List.of(Input.LOAD_LEVEL_1, Input.LOAD_LEVEL_2)
                : Collections.emptyList();
        boolean allowExit = Boolean.getBoolean("fuzz.allowExit");
        List<Input> rare = allowExit ? List.of(Input.SAVE, Input.EXIT) : List.of(Input.SAVE);

        List<Input> executed = new ArrayList<>(4096);

        Map<Input,Integer> success = new EnumMap<>(Input.class);
        Map<Input,Integer> fail = new EnumMap<>(Input.class);
        for (Input mv : movement) { success.put(mv,1); fail.put(mv,1); }

        Position lastPos = currentPlayerPos(controller);
        Input lastSuccessfulDir = null;
        int stagnationCounter = 0;
        int stagnationThreshold = 25;
        boolean inShake = false;
        int shakeRemaining = 0;

        Set<Position> visited = new HashSet<>();
        if (lastPos != null) visited.add(lastPos);

        int initialTreasure = safeCountTreasures(controller);
        int initialKeys = safeCountKeys(controller);

        Instant end = Instant.now().plusMillis(durationMillis);
        int moves = 0;

    boolean pausedRecently = false;
        long nonPlayStateStartMs = -1; // simplified: removed invalidInRow & forcedResumeAttempts

        int audioWarns = 0;
        final int AUDIO_WARN_LIMIT = 10;

        try {
            while (Instant.now().isBefore(end)) {

                String stateName = controller.state().getClass().getSimpleName();
                boolean isPlay = stateName.contains("Play");

                if (!isPlay) {
                    if (nonPlayStateStartMs < 0) nonPlayStateStartMs = System.currentTimeMillis();
                } else {
                    nonPlayStateStartMs = -1; // forcedResumeAttempts removed
                }

                // Auto-restart on Victory or Defeat to keep session active (no disabling logic anymore)
                if (stateName.contains("Victory") || stateName.contains("Defeat")) {
                    boolean ok = safeStartNewGame(controller, initialLevel);
                    if (!ok) {
                        audioFailCount++;
                        if (audioFailCount >= MAX_AUDIO_FAILS) {
                            System.out.println("[FUZZ][WARN] Too many audio-related restart failures (" + audioFailCount + ") continuing without restart.");
                        }
                    } else {
                        lastPos = currentPlayerPos(controller);
                        stagnationCounter = 0;
                        inShake = false;
                        shakeRemaining = 0;
                        pausedRecently = false;
                    }
                    // Regardless, proceed to next loop iteration (do not send meta inputs in terminal states)
                    continue;
                }

                if (!isPlay && (System.currentTimeMillis() - nonPlayStateStartMs) > 1000) {
                    boolean ok = forcePlayIfPaused(controller, initialLevel, rnd);
                    if (!ok) {
                        audioFailCount++;
                        // We no longer disable restarts; just log once threshold exceeded
                        if (audioFailCount == MAX_AUDIO_FAILS) {
                            System.out.println("[FUZZ][WARN] Reached audio fail threshold while paused.");
                        }
                    } else {
                        nonPlayStateStartMs = -1;
                        pausedRecently = false; // forcedResumeAttempts removed
                    }
                }

                // Final forced start block removed to avoid early termination.

                Input next;
                if (!isPlay) {
                    next = rnd.nextBoolean() ? Input.RESUME : Input.CONTINUE;
                } else if (pausedRecently) {
                    if (enablePause && rnd.nextInt(100) < 25) {
                        next = rnd.nextBoolean() ? Input.RESUME : Input.CONTINUE;
                    } else {
                        pausedRecently = false;
                        next = pickAction(rnd, movement, meta, levelLoads, rare,
                                success, fail, lastSuccessfulDir, stagnationCounter, inShake, crossLevels);
                    }
                } else {
                    next = pickAction(rnd, movement, meta, levelLoads, rare,
                            success, fail, lastSuccessfulDir, stagnationCounter, inShake, crossLevels);
                }

                executed.add(next);
                Position before = currentPlayerPos(controller);

                try {
                    controller.handleInput(next);
                } catch (UnsupportedOperationException | IllegalArgumentException ignored) {
                } catch (RuntimeException rte) {
                    if (rte.getMessage() != null && rte.getMessage().contains("background sound")) {
                        if (audioWarns++ < AUDIO_WARN_LIMIT) {
                            System.err.println("[FUZZ][AUDIO-WARN] Suppressed: " + rte.getMessage());
                            if (audioWarns == AUDIO_WARN_LIMIT) {
                                System.err.println("[FUZZ][AUDIO-WARN] Further audio warnings suppressed");
                            }
                        }
                        audioFailCount++;
                        // No longer disabling restarts; just note threshold
                        if (audioFailCount == MAX_AUDIO_FAILS) {
                            System.out.println("[FUZZ][WARN] Audio fail threshold reached (will continue attempts).");
                        }
                    } else {
                        dumpSequence(seed, executed, rte);
                        throw rte;
                    }
                }

                if (next == Input.PAUSE) pausedRecently = true;
                if (next == Input.RESUME || next == Input.CONTINUE) pausedRecently = false;

                boolean movementInput = movement.contains(next);
                if (movementInput) {
                    Position after = currentPlayerPos(controller);
                    boolean moved = after != null && before != null && !after.equals(before);
                    if (moved) {
                        success.merge(next,1,Integer::sum);
                        lastSuccessfulDir = next;
                        stagnationCounter = 0; // invalidInRow removed
                        if (after != null) visited.add(after);
                    } else {
                        fail.merge(next,1,Integer::sum);
                        stagnationCounter++; // invalidInRow removed
                    }
                } else {
                    // invalidInRow logic removed
                }

                if (movementInput && !inShake && stagnationCounter >= stagnationThreshold) {
                    inShake = true;
                    shakeRemaining = 15 + rnd.nextInt(10);
                    stagnationCounter = 0;
                    System.out.println("[FUZZ][HEURISTIC] Entering shake phase");
                }
                if (inShake && --shakeRemaining <= 0) {
                    inShake = false;
                    System.out.println("[FUZZ][HEURISTIC] Leaving shake phase");
                }

                if ((moves % 60) == 0) {
                    int treas = safeCountTreasures(controller);
                    int keys = safeCountKeys(controller);
                    System.out.printf(Locale.ROOT,
                            "[FUZZ][STATE] moves=%d lvl=%d visited=%d treas=%d/%d keys=%d/%d state=%s audioFails=%d dirSuc=%s%n",
                            moves,
                            controller.level(),
                            visited.size(),
                            treas, initialTreasure,
                            keys, initialKeys,
                            controller.state().getClass().getSimpleName(),
                            audioFailCount,
                            movementSuccessSummary(success, fail)
                    );
                }

                moves++;

                if (slowMode) {
                    sleepQuiet(35 + rnd.nextInt(55));
                } else if ((moves % 80) == 0) {
                    sleepQuiet(12 + rnd.nextInt(18));
                } else if ((moves % 15) == 0) {
                    sleepQuiet(2 + rnd.nextInt(6));
                }
            }
        } catch (RuntimeException | AssertionError e) {
            dumpSequence(seed, executed, e);
            throw e;
        } catch (Error err) {
            dumpSequence(seed, executed, err);
            throw err;
        }

        if (Boolean.getBoolean("fuzz.alwaysDump")) {
            dumpSequence(seed, executed, null);
        }
    }

    private boolean safeStartNewGame(AppController controller, int level) {
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                controller.startNewGame(level);
                if (controller.state().getClass().getSimpleName().contains("Play")) {
                    return true;
                }
            } catch (RuntimeException e) {
                if (e.getMessage() != null && e.getMessage().contains("background sound")) {
                    System.err.println("[FUZZ][AUDIO-WARN] Suppressed audio init failure (attempt " + attempt + ")");
                    sleepQuiet(30);
                    continue;
                }
                throw e;
            }
        }
        return controller.state().getClass().getSimpleName().contains("Play");
    }

    private boolean forcePlayIfPaused(AppController controller, int level, Random rnd) {
        String stateName = controller.state().getClass().getSimpleName();
        if (!stateName.contains("Paused")) return true;
        for (int attempt = 0; attempt < 4; attempt++) {
            try {
                controller.handleInput(Input.RESUME);
                controller.handleInput(Input.CONTINUE);
                controller.handleInput(Input.MOVE_UP);
            } catch (Exception ignored) {}
            if (controller.state().getClass().getSimpleName().contains("Play")) {
                System.out.println("[FUZZ][RECOVERY] Forced into PlayState after " + (attempt + 1) + " attempts");
                return true;
            }
        }
        try {
            controller.startNewGame(level);
        } catch (RuntimeException ex) {
            if (ex.getMessage() == null || !ex.getMessage().contains("background sound")) throw ex;
        }
        return controller.state().getClass().getSimpleName().contains("Play");
    }

    private Input pickAction(Random rnd,
                             List<Input> movement,
                             List<Input> meta,
                             List<Input> levelLoads,
                             List<Input> rare,
                             Map<Input,Integer> success,
                             Map<Input,Integer> fail,
                             Input lastSuccessfulDir,
                             int stagnationCounter,
                             boolean inShake,
                             boolean crossLevels) {

        if (shouldDoMeta(rnd, crossLevels)) {
            return meta.get(rnd.nextInt(meta.size()));
        } else if (!levelLoads.isEmpty() && rnd.nextInt(100) < 3) {
            return levelLoads.get(rnd.nextInt(levelLoads.size()));
        } else if (rnd.nextInt(100) < 5 && !rare.isEmpty()) {
            return rare.get(rnd.nextInt(rare.size()));
        } else {
            return pickMovementHeuristic(rnd, movement, success, fail, lastSuccessfulDir, stagnationCounter, inShake);
        }
    }

    private boolean shouldDoMeta(Random rnd, boolean crossLevels) {
        int roll = rnd.nextInt(100);
        if (crossLevels) return roll < 8;
        return roll < 5;
    }

    private Input pickMovementHeuristic(Random rnd,
                                        List<Input> movement,
                                        Map<Input,Integer> success,
                                        Map<Input,Integer> fail,
                                        Input lastSuccessDir,
                                        int stagnationCounter,
                                        boolean inShake) {
        if (inShake) return movement.get(rnd.nextInt(movement.size()));
        if (lastSuccessDir != null && rnd.nextInt(100) < 30) return lastSuccessDir;

        double total = 0;
        double[] weights = new double[movement.size()];
        for (int i = 0; i < movement.size(); i++) {
            Input mv = movement.get(i);
            int s = success.getOrDefault(mv,1);
            int f = fail.getOrDefault(mv,1);
            double ratio = (double)s / (s+f);
            double w = 0.2 + (ratio * ratio * 2.5);
            if (stagnationCounter > 10) w *= 1.2;
            weights[i] = w;
            total += w;
        }
        double pick = rnd.nextDouble() * total;
        for (int i = 0; i < weights.length; i++) {
            pick -= weights[i];
            if (pick <= 0) {
                if (Boolean.getBoolean("fuzz.verboseWeights")) {
                    System.out.println("[FUZZ][WEIGHTS] " + weightDebug(movement, weights));
                }
                return movement.get(i);
            }
        }
        return movement.get(0);
    }

    private String weightDebug(List<Input> moves, double[] w) {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < moves.size(); i++) {
            sb.append(moves.get(i).name()).append('=')
              .append(String.format(Locale.ROOT,"%.2f", w[i]));
            if (i < moves.size()-1) sb.append(", ");
        }
        sb.append('}');
        return sb.toString();
    }

    private String movementSuccessSummary(Map<Input,Integer> success, Map<Input,Integer> fail) {
        StringBuilder sb = new StringBuilder();
        for (Input mv : success.keySet()) {
            int s = success.getOrDefault(mv,1);
            int f = fail.getOrDefault(mv,1);
            double ratio = (double)s/(s+f);
            sb.append(mv.name().charAt(5)).append('=')
              .append(String.format(Locale.ROOT,"%.2f", ratio)).append(' ');
        }
        return sb.toString().trim();
    }

    private Position currentPlayerPos(AppController c) {
        try {
            Maze m = c.domain();
            return (m != null && m.getPlayer() != null) ? m.getPlayer().getPos() : null;
        } catch (Exception ignored) { return null; }
    }

    private int safeCountTreasures(AppController controller) {
        try {
            Maze m = controller.domain();
            if (m == null) return 0;
            return countTreasures(m);
        } catch (Exception e) { return 0; }
    }

    private int safeCountKeys(AppController controller) {
        try {
            Maze m = controller.domain();
            if (m == null) return 0;
            return countKeys(m);
        } catch (Exception e) { return 0; }
    }

    private int countTreasures(Maze maze) {
        int rows = maze.getRows(), cols = maze.getCols(), count = 0;
        for (int r=0;r<rows;r++) for (int c=0;c<cols;c++)
            if ("T".equals(maze.getSymbol(new Position(c,r)))) count++;
        return count;
    }

    private int countKeys(Maze maze) {
        int rows = maze.getRows(), cols = maze.getCols(), count = 0;
        for (int r=0;r<rows;r++) for (int c=0;c<cols;c++) {
            String s = maze.getSymbol(new Position(c,r));
            if ("K".equals(s) || "k".equals(s)) count++;
        }
        return count;
    }

    private void dumpSequence(long seed, List<Input> executed, Throwable t) {
        System.out.println("===== FUZZ SEQUENCE DUMP =====");
        System.out.println("Seed: " + seed + " length=" + executed.size());
        if (t != null) System.out.println("Failure: " + t);
        StringBuilder sb = new StringBuilder();
        for (Input i : executed) sb.append(i).append(',');
        System.out.println(sb);
        System.out.println("Re-run with: -Dfuzz.seed=" + seed);
        System.out.println("================================");
        if (Boolean.getBoolean("fuzz.issue.markdown")) {
            writeIssueMarkdown(seed, executed, t, sb.toString());
        }
    }

    private void writeIssueMarkdown(long seed, List<Input> executed, Throwable t, String csv) {
        java.io.File dir = new java.io.File("target/fuzz-issues");
        if (!dir.exists() && !dir.mkdirs()) {
            System.err.println("[FUZZ][ISSUE] Could not create directory: " + dir);
            return;
        }
        String shortName = (t == null ? "no-exception" : t.getClass().getSimpleName());
        String commit = readGitHead();
        String fileName = String.format("fuzz-%s-seed-%d.md", shortName, seed);
        java.io.File f = new java.io.File(dir, fileName);
        try (java.io.PrintWriter pw =
                     new java.io.PrintWriter(f, java.nio.charset.StandardCharsets.UTF_8)) {
            pw.println("# Fuzzer Detected Exception: " + shortName);
            pw.println();
            pw.println("Commit: `" + commit + "`");
            pw.println("Java: `" + System.getProperty("java.version") + "` OS: `" +
                    System.getProperty("os.name") + " " + System.getProperty("os.arch") + "`");
            pw.println();
            pw.println("## Summary");
            pw.println("Exception during fuzz run: `" + (t == null ? "(none)" : t.toString()) + "`.");
            pw.println();
            pw.println("## Reproduction");
            pw.println("```bash");
            pw.println("mvn -Dfuzz.seed=" + seed + " -Dfuzz.ms=15000 test");
            pw.println("```");
            pw.println();
            pw.println("## Stack Trace (trimmed)");
            pw.println("```");
            if (t != null) {
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
            pw.println("## Suggested Steps");
            pw.println("- Inspect stack frames.");
            pw.println("- Re-run with same seed.");
            pw.println("- Add regression test after fix.");
        } catch (Exception e) {
            System.err.println("[FUZZ][ISSUE] Failed to write issue markdown: " + e);
        }
        System.out.println("[FUZZ][ISSUE] Markdown written: " + f.getPath());
    }

    private String readGitHead() {
        try {
            java.nio.file.Path head = java.nio.file.Paths.get(".git/HEAD");
            if (!java.nio.file.Files.exists(head)) return "unknown";
            String ref = java.nio.file.Files.readString(head).trim();
            if (ref.startsWith("ref:")) {
                java.nio.file.Path refPath = java.nio.file.Paths.get(".git", ref.substring(5));
                if (java.nio.file.Files.exists(refPath)) {
                    return java.nio.file.Files.readString(refPath).trim();
                }
            }
            return ref;
        } catch (Exception e) { return "unknown"; }
    }

    @SuppressWarnings("unused")
    private void replay(String csv, int startLevel) {
        AppController controller = AppController.of();
        controller.startNewGame(startLevel);
        for (String tok : csv.split(",")) {
            if (tok.isBlank()) continue;
            try {
                controller.handleInput(Input.valueOf(tok.trim()));
                sleepQuiet(50);
            } catch (Exception e) {
                System.out.println("Replay halted on input=" + tok + " due to " + e);
                break;
            }
        }
    }

    private void sleepQuiet(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
    }
}
