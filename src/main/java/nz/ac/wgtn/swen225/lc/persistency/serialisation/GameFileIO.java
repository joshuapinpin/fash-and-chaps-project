package nz.ac.wgtn.swen225.lc.persistency.serialisation;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Concrete implementation of FileIO which reads/writes to JSON files.
 * @param <S> - the reduced representation of the game's state.
 * @author Thomas Ru - 300658840
 */
public class GameFileIO<S extends GameState> implements FileIO<S> {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Class<S> type;

    /**
     * Constructor requires the specific class of GameState in order to
     * deserialise from JSON.
     * @param type - the GameState type's class.
     */
    public GameFileIO(Class<S> type) {
        this.type = type;
    }

    /**
     * Save a GameState to file.
     * @param state - the GameState.
     * @param file - the File.
     * @throws IOException
     */
    @Override
    public void save(S state, File file) throws IOException {
        mapper.writeValue(file, state);
    }

    /**
     * Loads a GameState from a given file.
     * @param file - the File.
     * @return - the GameState, if readable.
     * @throws IOException
     */
    @Override
    public S load(File file) throws IOException {
        return mapper.readValue(file, type);
    }
}
