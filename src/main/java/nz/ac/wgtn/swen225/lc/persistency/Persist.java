package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Maze;

import javax.swing.*;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Game saving and loading API,
 * for saving current game board as JSON and loading previously saved game boards from JSON
 * via Swing dialogs.
 * @author Thomas Ru - 300658840
 */
public class Persist {
    private final PersistManager<LoadedMaze> gamePersistence;
    /**
     * Dependency injection for construction, useful for testing.
     * @param filesManager - supplies a PersistManager<Maze>
     */
    public Persist(Supplier<PersistManager<LoadedMaze>> filesManager) {
        this.gamePersistence = Objects.requireNonNull(filesManager).get();
    }

    /**
     * Given a specific game board, writes it to file.
     * Useful inside a listener.
     * @param maze - the Maze game board to save.
     * @param levelNumber - the current level (e.g. 1, 2)
     * @param maxTreasures - the maximum number of treasures on the current level.
     * @param maxKeys - the maximum number of keys on the current level.
     * @param time - the time left for this play-through.
     * @param app - the Swing game window, for choosing where to save.
     */
    public boolean saveGame(Maze maze, int levelNumber, int maxTreasures, int maxKeys, int time, JFrame app) {
        Objects.requireNonNull(maze, "Cannot save null game board.");
        Objects.requireNonNull(app, "Cannot create file dialog with null window.");
        return gamePersistence.save(new LoadedMaze(maze, time, new LevelInfo(levelNumber, maxKeys, maxTreasures)), app);
    }

    /**
     * Load a game from a previously saved JSON file.
     * Allows the user to choose a file, then constructs and returns the corresponding Maze game board.
     * @param app - the Swing game window, for choosing which game to load.
     * @return an Optional<LoadedMaze> object representing the previously saved game.
     */
    public Optional<LoadedMaze> loadGame(JFrame app) {
        return gamePersistence.load(app);
    }
}

