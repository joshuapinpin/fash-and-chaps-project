// java
package test.nz.ac.wgtn.swen225.lc.fuzz;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.domain.*;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.awt.GraphicsEnvironment;
import java.time.Instant;
import java.util.*;

/**
 * Heuristic fuzz tests for Levels 1 and 2 of the game.
 *
 * Goals:
 * - Exercise domain and application logic end-to-end via.
 * - Surface unexpected exceptions and write reproduction issues when failures occur.
 * - Keep execution bounded by time for predictable CI runs.
 *
 * Usage:
 * - Global variable to control run duration (default 15s).
 * - Re-run a failing sequence with {@code -Dfuzz.seed=...}.
 * - Extra logs with {@code -Dfuzz.logSeed=true} and {@code -Dfuzz.verboseWeights=true}.
 *
 * @author Al-Bara Al-Sakkaf
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

    /**
     * Fuzz Level 2 within the configured duration.
     * Same behavior and controls as {@link #testLevel1()}, but starts at Level 2.
     */
    @Test
    @Timeout(60)
    public void testLevel2() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(), "Headless CI - skipping GUI fuzzer");
        runFuzzer(2);
    }

    /**
     * Runs the core fuzzing loop for the given starting level.
     * The loop mixes movement inputs with occasional meta actions (pause/resume/save)
     * and optional level loads. It adapts movement choices based on recent success/failure
     * and uses a "shake" phase to escape stagnation. Exceptions related to background audio
     * initialization are suppressed to keep the run going; other runtime exceptions are dumped
     * with a reproduction artifact and rethrown.
     *
     * @param initialLevel starting level number (e.g., 1 or 2)
     */
    private void runFuzzer(int initialLevel) {
        // Duration for this run (centralised in RUN_MS constant above)
        long durationMillis = RUN_MS;
        long seed = Long.getLong("fuzz.seed", System.currentTimeMillis());
        Random rnd = new Random(seed);
        boolean logSeed = Boolean.getBoolean("fuzz.logSeed");
        boolean slowMode = Boolean.getBoolean("fuzz.slow");
        boolean crossLevels = Boolean.getBoolean("fuzz.crossLevels");
    boolean enablePause = Boolean.parseBoolean(System.getProperty("fuzz.enablePause","false"));
    boolean allowSave = Boolean.parseBoolean(System.getProperty("fuzz.allowSave", "false"));
    boolean allowResume = Boolean.parseBoolean(System.getProperty("fuzz.allowResume", "false"));

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
        List<Input> meta = new ArrayList<>();
        // Never include RESUME by default to avoid GUI load dialog; allow opt-in via fuzz.allowResume=true
        meta.add(Input.CONTINUE);
        if (allowResume) meta.add(Input.RESUME);
        if (enablePause) meta.add(Input.PAUSE);
        if (allowSave) meta.add(Input.SAVE);
        List<Input> levelLoads = crossLevels
                ? List.of(Input.LOAD_LEVEL_1, Input.LOAD_LEVEL_2)
                : Collections.emptyList();
    boolean allowExit = Boolean.getBoolean("fuzz.allowExit");
    List<Input> rare = allowExit
        ? (allowSave ? List.of(Input.EXIT, Input.SAVE) : List.of(Input.EXIT))
        : (allowSave ? List.of(Input.SAVE) : Collections.emptyList());

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
                    // Avoid RESUME to prevent triggering load dialog; use CONTINUE to return to Play
                    next = Input.CONTINUE;
                } else if (pausedRecently) {
                    if (enablePause && rnd.nextInt(100) < 25) {
                        // Avoid RESUME when recovering from pause; prefer CONTINUE only
                        next = Input.CONTINUE;
            } else {
                pausedRecently = false;
                next = pickAction(controller, rnd, movement, meta, levelLoads, rare,
                    success, fail, lastSuccessfulDir, stagnationCounter, inShake, crossLevels);
                    }
                } else {
            next = pickAction(controller, rnd, movement, meta, levelLoads, rare,
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

    /**
     * Helper method used by the fuzzer incase of audio issues.
     * Attempts to start a new game for the specified level, retrying a few times if
     * an audio initialization failure occurs. Non-audio exceptions are rethrown.
     *
     * @param controller the application controller
     * @param level      the level to start (1 or 2)
     * @return {@code true} if the controller transitions to a Play state; otherwise {@code false}
     */
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

    /**
     * Used incase game Pauses for too long and cannot resume.
     * If the game is currently in a paused state, attempts to resume back into Play.
     * Falls back to starting a new game if resume attempts do not succeed.
     *
     * @param controller the application controller
     * @param level      the level to start if resume fails
     * @param rnd        source of randomness for minor delays/choices
     * @return whether the game ends up in a Play state
     */
    private boolean forcePlayIfPaused(AppController controller, int level, Random rnd) {
        String stateName = controller.state().getClass().getSimpleName();
        if (!stateName.contains("Paused")) return true;
        for (int attempt = 0; attempt < 4; attempt++) {
            try {
                // Avoid RESUME entirely to prevent GUI load dialog in tests
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

    /**
     * Chooses the next input to send to the controller, balancing between movement,
     * meta (pause/resume/save), and optional level-load actions.
     *
     * @param rnd random source
     * @param movement movement inputs (up/down/left/right)
     * @param meta meta inputs (resume/continue/pause/save)
     * @param levelLoads optional level-load inputs
     * @param rare rare actions (e.g., save/exit)
     * @param success success counters by movement
     * @param fail failure counters by movement
     * @param lastSuccessfulDir last successful movement direction
     * @param stagnationCounter how long movement has been ineffective
     * @param inShake whether the fuzzer is in a shake phase
     * @param crossLevels whether level load actions are allowed
     * @return chosen input
     */
    private Input pickAction(AppController controller,
                             Random rnd,
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
            return pickMovementHeuristic(controller, rnd, movement, success, fail, lastSuccessfulDir, stagnationCounter, inShake);
        }
    }

    /**
     * Lightweight heuristic for when to emit a meta action instead of movement.
     *
     * @param rnd random source
     * @param crossLevels whether level load actions are allowed
     * @return true if a meta action should be emitted
     */
    private boolean shouldDoMeta(Random rnd, boolean crossLevels) {
        int roll = rnd.nextInt(100);
        if (crossLevels) return roll < 8;
        return roll < 5;
    }

    /**
     * Picks a movement input using simple bandit-style weighting of past success
     * ratios, with additional randomness and a shake mode to escape getting stuck.
     *
     * @param rnd random source
     * @param movement movement inputs
     * @param success success counters
     * @param fail failure counters
     * @param lastSuccessDir last successful direction (may be null)
     * @param stagnationCounter how long movement has been ineffective
     * @param inShake whether the fuzzer is in a shake phase
     * @return chosen movement input
     */
    private Input pickMovementHeuristic(AppController controller,
                                        Random rnd,
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
            // Bias away from hazards and toward rewards by peeking the next tile symbol
            try {
                String sym = peekNextSymbol(controller, mv);
                if (sym == null) {
                    w *= 0.3; // out of bounds/unknown
                } else if ("~".equals(sym)) {
                    w *= 0.10; // water is lethal
                } else if ("M".equals(sym)) {
                    w *= 0.20; // monster
                } else if ("W".equals(sym)) {
                    w *= 0.20; // wall
                } else if ("D".equals(sym)) {
                    w *= 0.60; // door: de-prioritize unless we happen to have key
                } else if ("T".equals(sym) || "K".equals(sym)) {
                    w *= 1.60; // treasure/key
                } else if ("E".equals(sym)) {
                    w *= 1.20; // exit is okay
                }
            } catch (Exception ignore) {
                // if domain not ready, keep base w
            }
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

    /**
     * Peek the symbol at the next cell if the given movement were applied.
     * Returns null if outside maze bounds or if the domain/player is unavailable.
     */
    private String peekNextSymbol(AppController controller, Input mv) {
        Maze m = controller.domain();
        if (m == null || m.getPlayer() == null) return null;
        Position p = m.getPlayer().getPos();
        int x = p.getX();
        int y = p.getY();
        switch (mv) {
            case MOVE_UP -> y -= 1;
            case MOVE_DOWN -> y += 1;
            case MOVE_LEFT -> x -= 1;
            case MOVE_RIGHT -> x += 1;
            default -> { return null; }
        }
        if (x < 0 || y < 0 || y >= m.getRows() || x >= m.getCols()) return null;
        return m.getSymbol(new Position(x, y));
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

    /**
     * Summarizes recent movement success ratios for periodic status logging.
     *
     * @param success success counters
     * @param fail failure counters
     * @return short summary string
     */
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

    /**
     * Safely retrieves the player's current {@link Position}, returning {@code null}
     * if unavailable or if any error occurs.
     *
     * @param c the application controller
     * @return player's position or {@code null}
     */
    private Position currentPlayerPos(AppController c) {
        try {
            Maze m = c.domain();
            return (m != null && m.getPlayer() != null) ? m.getPlayer().getPos() : null;
        } catch (Exception ignored) { return null; }
    }

    /**
     * Counts treasures using {@link #countTreasures(Maze)} while guarding against nulls/errors.
     *
     * @param controller the application controller
     * @return treasure count or 0 on error
     */
    private int safeCountTreasures(AppController controller) {
        try {
            Maze m = controller.domain();
            if (m == null) return 0;
            return countTreasures(m);
        } catch (Exception e) { return 0; }
    }

    /**
     * Counts keys using {@link #countKeys(Maze)} while guarding against nulls/errors.
     *
     * @param controller the application controller
     * @return key count or 0 on error
     */
    private int safeCountKeys(AppController controller) {
        try {
            Maze m = controller.domain();
            if (m == null) return 0;
            return countKeys(m);
        } catch (Exception e) { return 0; }
    }

    /**
     * Counts treasure tiles in the given maze by scanning symbols.
     *
     * @param maze maze to scan
     * @return number of treasures
     */
    private int countTreasures(Maze maze) {
        int rows = maze.getRows(), cols = maze.getCols(), count = 0;
        for (int r=0;r<rows;r++) for (int c=0;c<cols;c++)
            if ("T".equals(maze.getSymbol(new Position(c,r)))) count++;
        return count;
    }

    /**
     * Counts key tiles in the given maze by scanning symbols (both upper/lower case).
     *
     * @param maze maze to scan
     * @return number of keys
     */
    private int countKeys(Maze maze) {
        int rows = maze.getRows(), cols = maze.getCols(), count = 0;
        for (int r=0;r<rows;r++) for (int c=0;c<cols;c++) {
            String s = maze.getSymbol(new Position(c,r));
            if ("K".equals(s) || "k".equals(s)) count++;
        }
        return count;
    }

    /**
     * Prints a minimal reproduction bundle for the executed inputs and optionally writes
     * a Markdown issue artifact if {@code -Dfuzz.issue.markdown=true} is set.
     *
     * @param seed random seed used
     * @param executed sequence of executed inputs
     * @param t failure that occurred (nullable)
     */
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

    /**
     * Emits a Markdown report under {@code target/fuzz-issues/} containing the seed, stack trace,
     * platform info, and the executed input sequence in CSV form.
     *
     * @param seed random seed used
     * @param executed sequence of executed inputs
     * @param t failure that occurred (nullable)
     * @param csv CSV representation of {@code executed}
     */
    private void writeIssueMarkdown(long seed, List<Input> executed, Throwable t, String csv) {
        java.io.File dir = new java.io.File("target/fuzz-issues");
        if (!dir.exists() && !dir.mkdirs()) {
            System.err.println("[FUZZ][ISSUE] Could not create directory: " + dir);
            return;
        }
    String shortName = (t == null ? "no-exception" : t.getClass().getSimpleName());
    String commit = Optional.ofNullable(System.getenv("CI_COMMIT_SHA"))
        .or(() -> Optional.ofNullable(System.getenv("GIT_COMMIT")))
        .orElse("unknown");
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

    /**
     * Best-effort retrieval of the current commit hash from {@code .git/HEAD}.
     *
     * @return the commit hash, or "unknown" if it cannot be determined
     */
    // removed readGitHead(): commit id now taken from CI env if available


    /**
     * Sleeps without throwing checked exceptions; preserves interrupt status.
     *
     * @param millis milliseconds to sleep
     */
    private void sleepQuiet(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
    }

    // ====== Tiny domain micro-tests (no GUI) to stabilize coverage ======
    // These are quick, deterministic checks that bump domain coverage without adding new files.

    private static class CountingObserver implements GameObserver {
        int doors, drowns;
        @Override public void onPlayerMove(Position newPosition) { /* not asserted */ }
        @Override public void onKeyCollected(Key key) { /* not asserted */ }
        @Override public void onTreasureCollected() { /* not asserted */ }
        @Override public void onDoorOpened(Door door) { doors++; }
        @Override public void onPlayerDrown(Player player) { drowns++; }
    }

    private Maze newFilledMaze(int rows, int cols) {
        Maze m = new Maze(rows, cols);
        for (int r = 0; r < rows; r++) for (int c = 0; c < cols; c++)
            m.setTileAt(Free.of(new Position(c, r)));
        return m;
    }

    @Test
    public void microDomainCoverage() {
        // 3x3 maze fully free; player starts at center (1,1)
        Maze m = newFilledMaze(3,3);
        CountingObserver obs = new CountingObserver();
        m.addObserver(obs);

        // Water on the right; moving RIGHT should drown and notify
        m.setTileAt(Water.of(new Position(2,1)));
        m.movePlayer(Direction.RIGHT);
        org.junit.jupiter.api.Assertions.assertFalse(m.getPlayer().isAlive());
        org.junit.jupiter.api.Assertions.assertEquals(1, obs.drowns);

        // Reset: new maze; test door blocks then opens with key
        m = newFilledMaze(3,3); obs = new CountingObserver(); m.addObserver(obs);
        Free doorFree = Free.of(new Position(0,1));
        doorFree.setCollectable(Door.of(EntityColor.ORANGE));
        m.setTileAt(doorFree);
        Position start = m.getPlayer().getPos();
        m.movePlayer(Direction.LEFT);
        org.junit.jupiter.api.Assertions.assertEquals(start, m.getPlayer().getPos());
        m.getPlayer().addKey(Key.of(EntityColor.ORANGE));
        m.movePlayer(Direction.LEFT);
        org.junit.jupiter.api.Assertions.assertEquals(new Position(0,1), m.getPlayer().getPos());
        org.junit.jupiter.api.Assertions.assertEquals(1, obs.doors);

        // ExitLock blocks until all treasures collected
        m = newFilledMaze(3,3);
        m.getPlayer().setTotalTreasures(1);
        Free lockFree = Free.of(new Position(1,0));
        lockFree.setCollectable(ExitLock.of());
        m.setTileAt(lockFree);
        start = m.getPlayer().getPos();
        m.movePlayer(Direction.UP);
        org.junit.jupiter.api.Assertions.assertEquals(start, m.getPlayer().getPos());
        m.getPlayer().collectTreasure();
        m.movePlayer(Direction.UP);
        org.junit.jupiter.api.Assertions.assertEquals(new Position(1,0), m.getPlayer().getPos());

        // Symbols and monster
        m = newFilledMaze(3,3);
        m.setTileAt(Wall.of(new Position(0,0)));
        Free k = Free.of(new Position(2,0)); k.setCollectable(Key.of(EntityColor.GREEN)); m.setTileAt(k);
        Free t = Free.of(new Position(0,2)); t.setCollectable(Treasure.of()); m.setTileAt(t);
        m.setTileAt(Exit.of(new Position(2,2)));
        m.setTileAt(Info.of(new Position(1,0)));
        m.setTileAt(Water.of(new Position(0,1)));
        org.junit.jupiter.api.Assertions.assertEquals("W", m.getSymbol(new Position(0,0)));
        org.junit.jupiter.api.Assertions.assertEquals("K", m.getSymbol(new Position(2,0)));
        org.junit.jupiter.api.Assertions.assertEquals("T", m.getSymbol(new Position(0,2)));
        org.junit.jupiter.api.Assertions.assertEquals("E", m.getSymbol(new Position(2,2)));
        org.junit.jupiter.api.Assertions.assertEquals("I", m.getSymbol(new Position(1,0)));
        org.junit.jupiter.api.Assertions.assertEquals("~", m.getSymbol(new Position(0,1)));
        org.junit.jupiter.api.Assertions.assertEquals("P", m.getSymbol(m.getPlayer().getPos()));
        m.setMonster(Monster.of(new Position(1,2)));
        org.junit.jupiter.api.Assertions.assertEquals("M", m.getSymbol(new Position(1,2)));

        // Monster ping: place wall to force direction flip, then collide into player
        m = newFilledMaze(3,3);
        m.setTileAt(Wall.of(new Position(0,1))); // facing LEFT -> flip to RIGHT
        m.getPlayer().setPos(new Position(2,1));
        Monster mon = Monster.of(new Position(1,1));
        m.setMonster(mon);
        m.ping();
        org.junit.jupiter.api.Assertions.assertEquals(Direction.RIGHT, mon.getDirection());
        org.junit.jupiter.api.Assertions.assertEquals(new Position(2,1), mon.getPos());
        org.junit.jupiter.api.Assertions.assertFalse(m.getPlayer().isAlive());

        // Position negative setters throw
        Position p = new Position(1,1);
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> p.setX(-1));
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> p.setY(-1));
    }
}
