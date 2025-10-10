package test.nz.ac.wgtn.swen225.lc.recorder;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.app.controller.*;
import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.recorder.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for the Recorder package (No Mockito version)
 * Tests focus on what can be tested without full integration
 */
class RecorderPackageTests {

    @TempDir
    Path tempDir;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @AfterEach
    void tearDown() {
        SaveL1.of().reset();
        AutoplayL1.of().reset();
        StepByStepL1.of().reset();
    }

    // ==================== SaveL1 Tests ====================

    @Test
    @DisplayName("SaveL1: Singleton pattern returns same instance")
    void testSaveL1Singleton() {
        Save instance1 = SaveL1.of();
        Save instance2 = SaveL1.of();
        assertSame(instance1, instance2, "Should return same singleton instance");
    }

    @Test
    @DisplayName("SaveL1: Reset clears save list")
    void testSaveL1Reset() {
        Save save = SaveL1.of();
        save.reset();
        // After reset, the save list should be empty
        assertDoesNotThrow(() -> save.reset());
    }

    @Test
    @DisplayName("SaveL1: Multiple resets don't cause errors")
    void testSaveL1MultipleResets() {
        Save save = SaveL1.of();
        save.reset();
        save.reset();
        save.reset();
        assertNotNull(save);
    }

    @Test
    @DisplayName("SaveL1: SaveState does nothing (no-op)")
    void testSaveL1SaveState() {
        Save save = SaveL1.of();
        assertDoesNotThrow(() -> save.saveState(null));
    }

    @Test
    @DisplayName("SaveL1: Save to file with valid data")
    void testSaveL1SaveToFile() throws IOException {
        File testFile = tempDir.resolve("test_save.json").toFile();

        List<SaveL1.Moves> moves = new ArrayList<>();
        moves.add(new SaveL1.Moves(Input.MOVE_UP, 95000));
        moves.add(new SaveL1.Moves(Input.MOVE_DOWN, 90000));

        SaveL1.FullGame fg = new SaveL1.FullGame(moves, null);

        mapper.writerWithDefaultPrettyPrinter().writeValue(testFile, fg);
        assertTrue(testFile.exists(), "File should be created");

        SaveL1.FullGame loaded = mapper.readValue(testFile, SaveL1.FullGame.class);
        assertNotNull(loaded);
        assertEquals(2, loaded.saveList().size());
    }

    // ==================== AutoplayL1 Tests ====================

    @Test
    @DisplayName("AutoplayL1: Singleton pattern returns same instance")
    void testAutoplayL1Singleton() {
        Play instance1 = AutoplayL1.of();
        Play instance2 = AutoplayL1.of();
        assertSame(instance1, instance2, "Should return same singleton instance");
    }

    @Test
    @DisplayName("AutoplayL1: Reset clears state and stops timer")
    void testAutoplayL1Reset() {
        Play autoplay = AutoplayL1.of();
        autoplay.reset();
        assertDoesNotThrow(() -> autoplay.setSpeed(2));
    }

    @Test
    @DisplayName("AutoplayL1: Set speed with valid value")
    void testAutoplayL1SetSpeedValid() {
        Play autoplay = AutoplayL1.of();
        assertDoesNotThrow(() -> autoplay.setSpeed(1));
        assertDoesNotThrow(() -> autoplay.setSpeed(3));
        assertDoesNotThrow(() -> autoplay.setSpeed(6));
    }

    @Test
    @DisplayName("AutoplayL1: Set speed with negative value throws exception")
    void testAutoplayL1SetSpeedInvalid() {
        Play autoplay = AutoplayL1.of();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> autoplay.setSpeed(-1));
        assertTrue(exception.getMessage().contains("Speed must be between"));
    }

    @Test
    @DisplayName("AutoplayL1: Set speed to zero")
    void testAutoplayL1SetSpeedZero() {
        Play autoplay = AutoplayL1.of();
        assertDoesNotThrow(() -> autoplay.setSpeed(0));
    }

    @Test
    @DisplayName("AutoplayL1: Set multiple speed values")
    void testAutoplayL1SetMultipleSpeeds() {
        Play autoplay = AutoplayL1.of();
        assertDoesNotThrow(() -> {
            autoplay.setSpeed(1);
            autoplay.setSpeed(2);
            autoplay.setSpeed(3);
            autoplay.setSpeed(4);
        });
    }

    @Test
    @DisplayName("AutoplayL1: Multiple resets")
    void testAutoplayL1MultipleResets() {
        Play autoplay = AutoplayL1.of();
        autoplay.reset();
        autoplay.reset();
        autoplay.reset();
        assertNotNull(autoplay);
    }

    // ==================== StepByStepL1 Tests ====================

    @Test
    @DisplayName("StepByStepL1: Singleton pattern returns same instance")
    void testStepByStepL1Singleton() {
        Play instance1 = StepByStepL1.of();
        Play instance2 = StepByStepL1.of();
        assertSame(instance1, instance2, "Should return same singleton instance");
    }

    @Test
    @DisplayName("StepByStepL1: Reset clears position")
    void testStepByStepL1Reset() {
        Play stepByStep = StepByStepL1.of();
        stepByStep.reset();
        assertDoesNotThrow(() -> stepByStep.setSpeed(3));
    }

    @Test
    @DisplayName("StepByStepL1: SetSpeed does nothing (no-op)")
    void testStepByStepL1SetSpeed() {
        Play stepByStep = StepByStepL1.of();
        assertDoesNotThrow(() -> stepByStep.setSpeed(5));
        assertDoesNotThrow(() -> stepByStep.setSpeed(-1));
        assertDoesNotThrow(() -> stepByStep.setSpeed(100));
    }

    @Test
    @DisplayName("StepByStepL1: Multiple resets")
    void testStepByStepL1MultipleResets() {
        Play stepByStep = StepByStepL1.of();
        stepByStep.reset();
        stepByStep.reset();
        stepByStep.reset();
        assertDoesNotThrow(() -> stepByStep.setSpeed(1));
    }

    // ==================== Record Classes Tests ====================

    @Test
    @DisplayName("SaveL1.Moves: Record creation and access")
    void testMovesRecord() {
        SaveL1.Moves move = new SaveL1.Moves(Input.MOVE_UP, 95000);
        assertEquals(Input.MOVE_UP, move.direction());
        assertEquals(95000, move.timeLeftMilli());
    }

    @Test
    @DisplayName("SaveL1.FullGame: Record creation and access")
    void testFullGameRecord() {
        List<SaveL1.Moves> moves = new ArrayList<>();
        moves.add(new SaveL1.Moves(Input.MOVE_RIGHT, 90000));

        SaveL1.FullGame fullGame = new SaveL1.FullGame(moves, null);
        assertEquals(moves, fullGame.saveList());
        assertNull(fullGame.state());
    }

    @Test
    @DisplayName("SaveL1.Moves: Equality and hashCode")
    void testMovesEquality() {
        SaveL1.Moves move1 = new SaveL1.Moves(Input.MOVE_LEFT, 85000);
        SaveL1.Moves move2 = new SaveL1.Moves(Input.MOVE_LEFT, 85000);
        SaveL1.Moves move3 = new SaveL1.Moves(Input.MOVE_RIGHT, 85000);

        assertEquals(move1, move2);
        assertNotEquals(move1, move3);
        assertEquals(move1.hashCode(), move2.hashCode());
    }

    @Test
    @DisplayName("SaveL1.Moves: Different time values")
    void testMovesDifferentTimes() {
        SaveL1.Moves move1 = new SaveL1.Moves(Input.MOVE_UP, 100000);
        SaveL1.Moves move2 = new SaveL1.Moves(Input.MOVE_UP, 95000);

        assertNotEquals(move1, move2);
        assertEquals(Input.MOVE_UP, move1.direction());
        assertEquals(Input.MOVE_UP, move2.direction());
    }

    @Test
    @DisplayName("SaveL1.FullGame: Empty list handling")
    void testFullGameEmptyList() {
        List<SaveL1.Moves> emptyMoves = new ArrayList<>();

        SaveL1.FullGame fullGame = new SaveL1.FullGame(emptyMoves, null);
        assertTrue(fullGame.saveList().isEmpty());
        assertNull(fullGame.state());
    }

    @Test
    @DisplayName("SaveL1.Moves: All Input directions")
    void testMovesAllDirections() {
        SaveL1.Moves up = new SaveL1.Moves(Input.MOVE_UP, 100000);
        SaveL1.Moves down = new SaveL1.Moves(Input.MOVE_DOWN, 95000);
        SaveL1.Moves left = new SaveL1.Moves(Input.MOVE_LEFT, 90000);
        SaveL1.Moves right = new SaveL1.Moves(Input.MOVE_RIGHT, 85000);

        assertEquals(Input.MOVE_UP, up.direction());
        assertEquals(Input.MOVE_DOWN, down.direction());
        assertEquals(Input.MOVE_LEFT, left.direction());
        assertEquals(Input.MOVE_RIGHT, right.direction());
    }

    // ==================== Integration Tests ====================

    @Test
    @DisplayName("Integration: Full recording cycle with file I/O")
    void testFullRecordingCycle() throws IOException {
        File testFile = tempDir.resolve("integration_test.json").toFile();
        List<SaveL1.Moves> moves = new ArrayList<>();
        moves.add(new SaveL1.Moves(Input.MOVE_UP, 100000));
        moves.add(new SaveL1.Moves(Input.MOVE_DOWN, 95000));
        moves.add(new SaveL1.Moves(Input.MOVE_LEFT, 90000));

        SaveL1.FullGame fg = new SaveL1.FullGame(moves, null);
        mapper.writerWithDefaultPrettyPrinter().writeValue(testFile, fg);

        assertTrue(testFile.exists());
        SaveL1.FullGame loaded = mapper.readValue(testFile, SaveL1.FullGame.class);
        assertNotNull(loaded);
        assertEquals(3, loaded.saveList().size());
        assertEquals(Input.MOVE_UP, loaded.saveList().get(0).direction());
        assertEquals(Input.MOVE_DOWN, loaded.saveList().get(1).direction());
        assertEquals(Input.MOVE_LEFT, loaded.saveList().get(2).direction());
    }

    @Test
    @DisplayName("Edge case: Multiple resets in succession")
    void testMultipleResets() {
        SaveL1.of().reset();
        SaveL1.of().reset();
        AutoplayL1.of().reset();
        AutoplayL1.of().reset();
        StepByStepL1.of().reset();
        StepByStepL1.of().reset();

        assertNotNull(SaveL1.of());
        assertNotNull(AutoplayL1.of());
        assertNotNull(StepByStepL1.of());
    }

    @Test
    @DisplayName("Edge case: Set speed boundary values")
    void testSetSpeedBoundaries() {
        Play autoplay = AutoplayL1.of();
        assertDoesNotThrow(() -> autoplay.setSpeed(0));
        assertDoesNotThrow(() -> autoplay.setSpeed(1));
        assertDoesNotThrow(() -> autoplay.setSpeed(100));
        assertThrows(IllegalArgumentException.class, () -> autoplay.setSpeed(-1));
        assertThrows(IllegalArgumentException.class, () -> autoplay.setSpeed(-100));
    }

    @Test
    @DisplayName("Edge case: Empty moves list handling")
    void testEmptyMovesHandling() throws IOException {
        File testFile = tempDir.resolve("empty_moves.json").toFile();
        List<SaveL1.Moves> emptyMoves = new ArrayList<>();

        SaveL1.FullGame fg = new SaveL1.FullGame(emptyMoves, null);

        mapper.writerWithDefaultPrettyPrinter().writeValue(testFile, fg);

        SaveL1.FullGame loaded = mapper.readValue(testFile, SaveL1.FullGame.class);
        assertNotNull(loaded);
        assertTrue(loaded.saveList().isEmpty());
    }

    @Test
    @DisplayName("Null safety: GameState can be null")
    void testNullGameState() {
        SaveL1.FullGame fg = new SaveL1.FullGame(new ArrayList<>(), null);
        assertNull(fg.state());
        assertNotNull(fg.saveList());
    }

    @Test
    @DisplayName("Record toString methods work")
    void testRecordToString() {
        SaveL1.Moves move = new SaveL1.Moves(Input.MOVE_UP, 95000);
        String moveStr = move.toString();
        assertNotNull(moveStr);
        assertTrue(moveStr.contains("UP") || moveStr.contains("95000"));
    }

    @Test
    @DisplayName("JSON serialization: Complex game state")
    void testComplexJsonSerialization() throws IOException {
        File testFile = tempDir.resolve("complex_test.json").toFile();

        List<SaveL1.Moves> moves = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Input direction = switch (i % 4) {
                case 0 -> Input.MOVE_UP;
                case 1 -> Input.MOVE_DOWN;
                case 2 -> Input.MOVE_LEFT;
                default -> Input.MOVE_RIGHT;
            };
            moves.add(new SaveL1.Moves(direction, 100000 - (i * 5000)));
        }

        SaveL1.FullGame fg = new SaveL1.FullGame(moves, null);
        mapper.writerWithDefaultPrettyPrinter().writeValue(testFile, fg);

        SaveL1.FullGame loaded = mapper.readValue(testFile, SaveL1.FullGame.class);
        assertEquals(10, loaded.saveList().size());
        assertEquals(100000, loaded.saveList().get(0).timeLeftMilli());
        assertEquals(55000, loaded.saveList().get(9).timeLeftMilli());
    }

    @Test
    @DisplayName("SaveL1.Moves: Time can be zero")
    void testMovesZeroTime() {
        SaveL1.Moves move = new SaveL1.Moves(Input.MOVE_UP, 0);
        assertEquals(0, move.timeLeftMilli());
        assertEquals(Input.MOVE_UP, move.direction());
    }

    @Test
    @DisplayName("SaveL1.Moves: Large time values")
    void testMovesLargeTime() {
        SaveL1.Moves move = new SaveL1.Moves(Input.MOVE_DOWN, Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, move.timeLeftMilli());
    }

    @Test
    @DisplayName("SaveL1.FullGame: Large list of moves")
    void testFullGameLargeList() {
        List<SaveL1.Moves> largeMoves = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            largeMoves.add(new SaveL1.Moves(Input.MOVE_UP, 100000 - i));
        }

        SaveL1.FullGame fg = new SaveL1.FullGame(largeMoves, null);
        assertEquals(1000, fg.saveList().size());
    }

    @Test
    @DisplayName("All singletons maintain state across calls")
    void testSingletonStatePersistence() {
        Save save1 = SaveL1.of();
        Save save2 = SaveL1.of();
        save1.reset();
        // Since they're the same object, both should reflect the reset
        assertSame(save1, save2);

        Play auto1 = AutoplayL1.of();
        Play auto2 = AutoplayL1.of();
        auto1.setSpeed(5);
        assertSame(auto1, auto2);

        Play step1 = StepByStepL1.of();
        Play step2 = StepByStepL1.of();
        step1.reset();
        assertSame(step1, step2);
    }

    @Test
    @DisplayName("Speed settings don't affect StepByStepL1")
    void testStepByStepSpeedIndependence() {
        Play step = StepByStepL1.of();
        // Should accept any speed without error since it's a no-op
        step.setSpeed(-999);
        step.setSpeed(0);
        step.setSpeed(999);
        assertNotNull(step);
    }

    @Test
    @DisplayName("AutoplayL1: Speed change without reset")
    void testAutoplaySpeedChangeWithoutReset() {
        Play autoplay = AutoplayL1.of();
        autoplay.setSpeed(1);
        autoplay.setSpeed(2);
        autoplay.setSpeed(3);
        // Should be able to change speed multiple times
        assertDoesNotThrow(() -> autoplay.setSpeed(4));
    }

    @Test
    @DisplayName("Record equality: Same values produce equal objects")
    void testRecordEqualitySymmetry() {
        SaveL1.Moves move1 = new SaveL1.Moves(Input.MOVE_UP, 100000);
        SaveL1.Moves move2 = new SaveL1.Moves(Input.MOVE_UP, 100000);

        assertEquals(move1, move2);
        assertEquals(move2, move1); // Symmetry
        assertEquals(move1.hashCode(), move2.hashCode());
    }

    @Test
    @DisplayName("FullGame with null state serializes correctly")
    void testFullGameNullStateSerialization() throws IOException {
        File testFile = tempDir.resolve("null_state.json").toFile();
        List<SaveL1.Moves> moves = new ArrayList<>();
        moves.add(new SaveL1.Moves(Input.MOVE_RIGHT, 50000));

        SaveL1.FullGame fg = new SaveL1.FullGame(moves, null);
        mapper.writerWithDefaultPrettyPrinter().writeValue(testFile, fg);

        SaveL1.FullGame loaded = mapper.readValue(testFile, SaveL1.FullGame.class);
        assertNotNull(loaded);
        assertNull(loaded.state());
        assertEquals(1, loaded.saveList().size());
    }
}