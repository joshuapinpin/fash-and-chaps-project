package test.nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.persistency.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GamePersistManagerTest {
    static class MockFileDialog implements FileDialog {
        Optional<File> saveFile = Optional.empty();
        Optional<File> openFile = Optional.empty();

        @Override
        public Optional<File> showSaveDialog(JFrame parent, String defaultName, String extension) {
            return saveFile;
        }

        @Override
        public Optional<File> showOpenDialog(JFrame parent, String extension) {
            return openFile;
        }
    }

    static class MockFileIO implements FileIO<GameState> {
        GameState savedState;
        File savedFile;
        boolean throwOnSave = false;
        boolean throwOnLoad = false;
        GameState toLoad;

        @Override
        public void save(GameState data, File file) throws IOException {
            if (throwOnSave) throw new IOException("save failure");
            savedState = data;
            savedFile = file;
        }

        @Override
        public GameState load(File file) throws IOException {
            if (throwOnLoad) throw new IOException("load failure");
            return toLoad;
        }
    }

    static class MockMapper implements Mapper<LoadedMaze, GameState> {
        @Override
        public GameState toState(LoadedMaze data) {
            Maze m = data.maze();
            LevelInfo li = data.levelInfo();
            int t = data.time();
            PlayerState ps = new PlayerState(0, 0, 0, 0, null, null);
            return new GameState(m.getRows(), m.getCols(), t, li, ps);
        }

        @Override
        public LoadedMaze fromState(GameState state) {
            Maze m = new Maze(state.getRows(), state.getCols());
            return new LoadedMaze(m, state.getTime(), state.getLevelInfo());
        }
    }

    private MockFileDialog fileDialog;
    private MockFileIO fileIO;
    private MockMapper mapper;
    private GamePersistManager manager;

    @BeforeEach
    void setUp() {
        fileDialog = new MockFileDialog();
        fileIO = new MockFileIO();
        mapper = new MockMapper();

        manager = new GamePersistManager(
                new Message.Stub(),
                fileDialog,
                fileIO,
                mapper
        );
    }

    @Test
    void saveGameState() {
        fileDialog.saveFile = Optional.of(new File("save_test.json"));

        Maze maze = new Maze(6, 7);
        boolean ok = manager.save(maze, 1, 1, 1, 100, new JFrame());

        assertTrue(ok);
        assertNotNull(fileIO.savedState);
        assertEquals(6, fileIO.savedState.getRows());
        assertEquals(7, fileIO.savedState.getCols());
        assertEquals(100, fileIO.savedState.getTime());
    }

    @Test
    void saveCancelFalse() {
        fileDialog.saveFile = Optional.empty();

        Maze maze = new Maze(6, 7);
        boolean ok = manager.save(maze, 1, 1, 1, 1, new JFrame());

        assertFalse(ok);
        assertNull(fileIO.savedState);
    }

    @Test
    void loadGameState() {
        fileDialog.openFile = Optional.of(new File("load_test.json"));

        LevelInfo li = new LevelInfo(1, 1, 1);
        PlayerState ps = new PlayerState(0, 0, 0, 0, null, null);
        GameState gs = new GameState(6, 7, 67, li, ps);
        fileIO.toLoad = gs;

        Optional<LoadedMaze> result = manager.load(new JFrame());

        assertTrue(result.isPresent());
        LoadedMaze lm = result.get();
        assertEquals(6, lm.maze().getRows());
        assertEquals(7, lm.maze().getCols());
        assertEquals(67, lm.time());
    }

    @Test
    void loadCancelEmpty() {
        fileDialog.openFile = Optional.empty();
        Optional<LoadedMaze> result = manager.load(new JFrame());
        assertTrue(result.isEmpty());
    }

    @Test
    void loadExceptionEmpty() {
        fileDialog.openFile = Optional.of(new File("broken.json"));
        fileIO.throwOnLoad = true;

        Optional<LoadedMaze> result = manager.load(new JFrame());
        assertTrue(result.isEmpty());
    }
}
