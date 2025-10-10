package nz.ac.wgtn.swen225.lc.persistency;

/**
 * API for converting between a data type and a serialisable representation of it.
 * E.g. whereas Maze represents the game directly, GameState holds the same information but
 * in a format which is suitable for writing to and reading from file.
 * @param <T> - the type of data.
 * @param <S> - the type of state.
 * @author Thomas Ru - 300658840
 */
public interface Mapper<T, S> {
    /**
     * Convert some fully fledged data instance into a reduced state instance
     * @param data - the data of type T
     * @return - the state of type S
     */
    S toState(T data);

    /**
     * Convert some reduced state instance into a  fully fledged data instance
     * @param state - the state of type S
     * @return - the data of type T
     */
    T fromState(S state);
}
