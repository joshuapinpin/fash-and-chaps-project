package nz.ac.wgtn.swen225.lc.persistency.serialisation.api;

import java.io.File;
import java.io.IOException;

/**
 * Write a reduced representation of some state to file, or read it from file.
 * @param <S> - the state, e.g. of a game.
 * @author Thomas Ru - 300658840
 */
public interface FileIO<S> {
    void save(S state, File file) throws IOException;

    S load(File file) throws IOException;
}
