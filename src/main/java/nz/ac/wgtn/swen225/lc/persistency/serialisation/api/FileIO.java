package nz.ac.wgtn.swen225.lc.persistency.serialisation.api;

import java.io.File;
import java.io.IOException;

/**
 * Write a reduced representation of some state to file, or read it from file.
 * @param <S> - the state, e.g. of a game.
 * @author Thomas Ru - 300658840
 */
public interface FileIO<S> {
    /**
     * Save some state to file.
     * @param state - the state
     * @param file - the file to save to.
     * @throws IOException if issue writing state to file, or serialising
     */
    void save(S state, File file) throws IOException;

    /**
     * Load some state from file
     * @param file - the file to load from
     * @return - the state of type S
     * @throws IOException if issue reading state from file, or deserialising
     */
    S load(File file) throws IOException;
}
