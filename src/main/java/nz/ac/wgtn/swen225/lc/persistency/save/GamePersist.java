package nz.ac.wgtn.swen225.lc.persistency.save;

import nz.ac.wgtn.swen225.lc.domain.Maze;

/**
 * Concrete way for user to save and load Chaps Challenge games via Swing GUI.
 */
public class GamePersist extends Persist<Maze>{
    /**
     * Relies on the Maze class for construction.
     */
    public GamePersist() {
        super(()->new GamePersistManager<>(Maze.class));
    }
}
