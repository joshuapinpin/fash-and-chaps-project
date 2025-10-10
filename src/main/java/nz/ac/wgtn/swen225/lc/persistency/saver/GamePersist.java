package nz.ac.wgtn.swen225.lc.persistency.saver;

import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.GameFileIO;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.GameMapper;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.GameState;

/**
 * Concrete way for user to save and load Chaps Challenge games via Swing GUI.
 * @author Thomas Ru - 300658840
 */
public class GamePersist extends Persist{
    /**
     * Relies on the GameState class for construction.
     */
    public GamePersist() {
        super(
                ()->new GamePersistManager(
                        new GameFileIO<>(GameState.class),
                        new GameMapper()
                )
        );
    }
}
