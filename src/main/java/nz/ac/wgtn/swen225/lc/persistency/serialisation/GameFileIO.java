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
    private final ObjectMapper mapper = new ObjectMapper(); // add findAndRegisterModules() later
    private final Class<S> type;

    public GameFileIO(Class<S> type) {
        this.type = type;
    }

    @Override
    public void save(S state, File file) throws IOException {
        throw new UnsupportedOperationException("To do");
    }

    @Override
    public S load(File file) throws IOException {
        throw new UnsupportedOperationException("To do");
    }
}
