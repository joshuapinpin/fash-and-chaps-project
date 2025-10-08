package nz.ac.wgtn.swen225.lc.persistency.saver;

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
public class Persist<M extends Maze> {
    private final PersistManager<M> gamePersistence;
    /**
     * Dependency injection for construction, useful for testing.
     * @param managerSupplier - supplies a PersistManager<Maze>
     */
    public Persist(Supplier<PersistManager<M>> managerSupplier) {
        this.gamePersistence = Objects.requireNonNull(managerSupplier).get();
    }

    /**
     * Given a specific game board, writes it to file.
     * Useful inside a listener.
     * @param maze - the Maze game board to save.
     * @param app - the Swing game window, for choosing where to save.
     */
    public void saveGame(M maze, JFrame app) {
        Objects.requireNonNull(maze, "Cannot save null game board.");
        Objects.requireNonNull(app, "Cannot create file dialog with null window.");
        gamePersistence.save(maze, app);
    }

    /**
     * Load a game from a previously saved JSON file.
     * Allows the user to choose a file, then constructs and returns the corresponding Maze game board.
     * @param app - the Swing game window, for choosing which game to load.
     * @return an Optional<Maze> object representing the previously saved game.
     */
    public Optional<M> loadGame(JFrame app) {
        return gamePersistence.load(app);
    }
}

