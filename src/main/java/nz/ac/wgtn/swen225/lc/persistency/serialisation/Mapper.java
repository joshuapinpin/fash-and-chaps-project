package nz.ac.wgtn.swen225.lc.persistency.serialisation;

import nz.ac.wgtn.swen225.lc.domain.Maze;

/**
 * API for converting between a type of Maze and a type of GameState.
 * Whereas Maze represents the game directly, GameState holds the same information but
 * in a format which is suitable for writing to and reading from file.
 * @param <M> - the type of Maze.
 * @param <S> - the type of GameState.
 * @author Thomas Ru - 300658840
 */
public interface Mapper<M extends Maze, S extends GameState> {
    S toGameState(M maze);
    M fromGameState(S gameState);
}
