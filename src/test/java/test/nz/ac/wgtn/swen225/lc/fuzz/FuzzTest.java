// java
package test.nz.ac.wgtn.swen225.lc.fuzz;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import nz.ac.wgtn.swen225.lc.app.util.Input;

import java.awt.*; // GraphicsEnvironment used to detect headless CI environments
import java.security.SecureRandom; // Strong RNG; not seeded below for reproducibility (see comment in runFuzzer)
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;

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
        // In CI containers there is no GUI display (no X11/Wayland), so any attempt to create Swing windows
        // will throw java.awt.HeadlessException. This assumption skips the test in that case to keep the
        // pipeline green while still allowing the test to run locally with a desktop.
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(), "Headless CI - skipping GUI fuzzer");

        // Run the fuzzer against level 1 with a small time budget to keep tests fast and deterministic enough
        // for CI stability.
        runFuzzer(1, 15_000); // ~15 seconds budget inside 60s test timeout
    }

// Disabled level 2 for Integration Day as level 2 has not been implemented yet.
//    @Test
//    @Timeout(60)
//    public void testLevel2() {
//        runFuzzer(2, 15_000);
//    }

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
    private void runFuzzer(int level, long durationMillis) {
        // A seed value is captured (optionally override via -Dfuzz.seed) to allow reproducibility when needed.
        // Note: The RNG below uses SecureRandom(), which ignores the seed. To reproduce a run exactly,
        // replace SecureRandom with new Random(seed). Keeping SecureRandom favors input diversity over replay.
        long seed = Long.getLong("fuzz.seed", System.currentTimeMillis());
        Random rnd = new SecureRandom(); // SecureRandom for better distribution; could switch to new Random(seed)

        // Optional logging of the seed to stdout so that a failing run can be investigated locally.
        boolean showSeed = Boolean.getBoolean("fuzz.logSeed");
        if(showSeed) System.out.println("[FUZZ] LEVEL="+level+" SEED="+seed);

        // Build a controller via the regular factory. This path initializes the GUI in non-headless mode, which
        // is why the headless assumption above is required to prevent failures on CI.
        AppController controller = AppController.of();
        controller.startNewGame(level); // Ensure a clean game state before fuzzing begins.

        Instant end = Instant.now().plus(Duration.ofMillis(durationMillis));

        // Input pools
        List<Input> movement = List.of(Input.MOVE_UP, Input.MOVE_DOWN, Input.MOVE_LEFT, Input.MOVE_RIGHT);
        List<Input> meta = List.of(Input.PAUSE, Input.RESUME, Input.CONTINUE, Input.SAVE);
        List<Input> levelLoads = List.of(Input.LOAD_LEVEL_1, Input.LOAD_LEVEL_2);

        // The main loop biases actions by bucket percentage:
        //  - 0..69: movement (~70%)
        //  - 70..84: meta actions (~15%)
        //  - 85..94: level load actions (~10%)
        //  - 95..99: rare action placeholder (~5%)
        int moves = 0;
        while(Instant.now().isBefore(end)) {
            Input next;
            int bucket = rnd.nextInt(100);
            if(bucket < 70) {
                next = movement.get(rnd.nextInt(movement.size()));
            } else if(bucket < 85) {
                next = meta.get(rnd.nextInt(meta.size()));
            } else if(bucket < 95) {
                next = levelLoads.get(rnd.nextInt(levelLoads.size()));
            } else {
                next = Input.SAVE; // placeholder for rarer action bucket
            }

            try {
                // Drive the public controller API; any unexpected runtime exception here indicates a robustness issue.
                controller.handleInput(next);
            } catch(UnsupportedOperationException | IllegalArgumentException ignored) {
                // Some inputs may be legitimately unsupported in certain states; those are not test failures.
            }

            moves++;
            if((moves % 25)==0) {
                // Brief sleeps give the EDT time to process events and simulate more realistic user pacing.
                // Keeping this small prevents the test from exceeding the overall timeout.
                sleepQuiet(5 + (rnd.nextInt(10))); // throttle a little
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
