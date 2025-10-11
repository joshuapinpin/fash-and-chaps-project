package nz.ac.wgtn.swen225.lc.persistency;

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
                        new Message.Default(),
                        new SwingFileDialog(),
                        new GameFileIO<>(GameState.class),
                        new GameMapper()
                )
        );
    }
}
