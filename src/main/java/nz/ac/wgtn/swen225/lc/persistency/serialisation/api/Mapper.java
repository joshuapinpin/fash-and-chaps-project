package nz.ac.wgtn.swen225.lc.persistency.serialisation.api;

/**
 * API for converting between a data type and a serialisable representation of it.
 * E.g. whereas Maze represents the game directly, GameState holds the same information but
 * in a format which is suitable for writing to and reading from file.
 * @param <T> - the type of data.
 * @param <S> - the type of state.
 * @author Thomas Ru - 300658840
 */
public interface Mapper<T, S> {
    S toState(T data);
    T fromState(S state);
}
