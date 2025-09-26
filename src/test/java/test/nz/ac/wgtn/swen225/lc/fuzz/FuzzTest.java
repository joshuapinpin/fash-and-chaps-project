package test.nz.ac.wgtn.swen225.lc.fuzz;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.util.Input;

import java.security.SecureRandom;
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
        runFuzzer(1, 15_000); // ~15 seconds budget inside 60s test timeout
    }

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
        // Repro seed: allow override via -Dfuzz.seed=...
        long seed = Long.getLong("fuzz.seed", System.currentTimeMillis());
        Random rnd = new SecureRandom(); // SecureRandom for better distribution; could switch to new Random(seed)

        boolean showSeed = Boolean.getBoolean("fuzz.logSeed");
        if(showSeed) System.out.println("[FUZZ] LEVEL="+level+" SEED="+seed);

        GameController controller = GameController.of();
        controller.startNewGame(level);

        Instant end = Instant.now().plus(Duration.ofMillis(durationMillis));

        // Input pools
        List<Input> movement = List.of(Input.MOVE_UP, Input.MOVE_DOWN, Input.MOVE_LEFT, Input.MOVE_RIGHT);
        List<Input> meta = List.of(Input.PAUSE, Input.RESUME, Input.CONTINUE, Input.SAVE);
        List<Input> levelLoads = List.of(Input.LOAD_LEVEL_1, Input.LOAD_LEVEL_2);

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
                controller.handleInput(next);
            } catch(UnsupportedOperationException | IllegalArgumentException ignored) {
                // expected occasionally
            }

            moves++;
            if((moves % 25)==0) sleepQuiet(5 + (rnd.nextInt(10))); // throttle a little
        }
    }

    private void sleepQuiet(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException ignored) { Thread.currentThread().interrupt(); }
    }
}
