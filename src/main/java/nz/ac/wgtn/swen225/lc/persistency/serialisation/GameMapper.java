package nz.ac.wgtn.swen225.lc.persistency.serialisation;

import nz.ac.wgtn.swen225.lc.domain.Maze;

/**
 * Concrete Mapper which converts to/from Maze and its serialisation friendly counterpart GameState.
 * @author Thomas Ru - 300658840
 */
public class GameMapper implements Mapper<Maze, GameState> {

    @Override
    public GameState toGameState(Maze maze) {
        throw new UnsupportedOperationException("To do");
    }

    @Override
    public Maze fromGameState(GameState gameState) {
        throw new UnsupportedOperationException("To do");
    }
}
