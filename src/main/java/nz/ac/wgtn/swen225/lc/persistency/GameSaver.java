package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Maze;

import java.awt.*;
import java.io.File;

/**
 * Game saving and loading API,
 * for saving current game board as JSON and loading previously saved game boards from JSON.
 */
public class GameSaver {
    /**
     * Given a specific game board, writes it to file as JSON.
     * @param maze - the Maze game board to save.
     */
    public static void saveGame(Maze maze) {
        throw new UnsupportedOperationException("To do");
    }

    /**
     * Utility method for loading a game from a previous save, e.g. as part of an ActionListener.
     * Allows the user to choose a file, then constructs and returns the corresponding Maze game board.
     * @param app - the Swing game window.
     * @return the Maze object representing the previously saved game.
     */
    public static Maze loadGameChooser(Window app) {
        throw new UnsupportedOperationException("To do");
    }

    /**
     * Given a JSON file representing a saved game, deserialises to a Maze game board
     * for resuming play.
     * @return the Maze object representing the saved game.
     */
    public static Maze loadGame(File file) {
        throw new UnsupportedOperationException("To do");
    }
}
