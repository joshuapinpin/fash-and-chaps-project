package test.nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.persistency.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class GamePersistManagerTest {

    /** Stub FileIO that just records if save/load was called. */
    private static class StubFileIO implements FileIO<GameState> {
        boolean saved = false;
        boolean loaded = false;

        @Override
        public void save(GameState data, File file) throws IOException {
            saved = true;
        }

        @Override
        public GameState load(File file) throws IOException {
            loaded = true;
            return new GameState(1, 1, 10, new LevelInfo(1, 1, 1),
                    new PlayerState(0, 0, 0, 0, "UP", java.util.List.of()));
        }
    }

    /** Stub Mapper that simply wraps/unpacks dummy data. */
    private static class StubMapper implements Mapper<LoadedMaze, GameState> {
        @Override
        public GameState toState(LoadedMaze maze) {
            return new GameState(1, 1, maze.time(), maze.levelInfo(),
                    new PlayerState(0, 0, 0, 0, "UP", java.util.List.of()));
        }

        @Override
        public LoadedMaze fromState(GameState state) {
            return new LoadedMaze(new Maze(1,1), state.getTime(), state.getLevelInfo());
        }
    }

    /** Test subclass that bypasses all JOptionPane dialogs and GUI popups. */
    private static class TestGamePersistManager extends GamePersistManager {

        private final Optional<File> fileToUse;

        public TestGamePersistManager(FileIO<GameState> fileIO, Mapper<LoadedMaze, GameState> mapper, Optional<File> fileToUse) {
            super(fileIO, mapper);
            this.fileToUse = fileToUse;
        }

        @Override
        public boolean save(Maze data, int levelNumber, int maxTreasures, int maxKeys, int time, JFrame parent) {
            // Skip any Swing dialogs
            if (fileToUse.isEmpty()) return false;
            try {
                LevelInfo levelInfo = new LevelInfo(levelNumber, maxKeys, maxTreasures);
                new GameFileIO<GameState>(GameState.class).save(new GameMapper().toState(new LoadedMaze(data, time, levelInfo)), fileToUse.get());
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        public Optional<LoadedMaze> load(JFrame parent) {
            if (fileToUse.isEmpty()) return Optional.empty();
            try {
                GameState state = new GameFileIO<GameState>(GameState.class).load(fileToUse.get());
                return Optional.of(new GameMapper().fromState(state));
            } catch (IOException e) {
                return Optional.empty();
            }
        }
    }

    @Test
    void testSaveCancelledByUser() {
        StubFileIO fileIO = new StubFileIO();
        StubMapper mapper = new StubMapper();

        var manager = new TestGamePersistManager(fileIO, mapper, Optional.empty());

        boolean result = manager.save(new Maze(1,1), 1, 2, 3, 100, new JFrame());
        assertFalse(result, "Save should return false when user cancels");
        assertFalse(fileIO.saved, "FileIO.save() should not be called if no file selected");
    }

    @Test
    void testLoadCancelledByUser() {
        StubFileIO fileIO = new StubFileIO();
        StubMapper mapper = new StubMapper();

        var manager = new TestGamePersistManager(fileIO, mapper, Optional.empty());

        Optional<LoadedMaze> result = manager.load(new JFrame());
        assertTrue(result.isEmpty(), "Load should return empty when user cancels");
        assertFalse(fileIO.loaded, "FileIO.load() should not be called");
    }
}


final class TestUtils {
    static void setPrivateField(Object target, String fieldName, Object newValue) {
        try {
            Field f = target.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(target, newValue);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}

