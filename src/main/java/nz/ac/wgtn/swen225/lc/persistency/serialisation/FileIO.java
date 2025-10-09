package nz.ac.wgtn.swen225.lc.persistency.serialisation;

import java.io.File;
import java.io.IOException;

/**
 * Write a reduced representation of the game's state to file, or read it from file.
 * @param <S> - the game state.
 * @author Thomas Ru - 300658840
 */
public interface FileIO<S extends GameState> {
    void save(S state, File file) throws IOException;

    S load(File file) throws IOException;
}
