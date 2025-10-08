package nz.ac.wgtn.swen225.lc.persistency.serialisation;

import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.domain.Monster;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.entities.Entity;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;
import nz.ac.wgtn.swen225.lc.persistency.parse.EntityParsers;
import nz.ac.wgtn.swen225.lc.persistency.parse.MonsterParser;
import nz.ac.wgtn.swen225.lc.persistency.parse.TileParsers;

import java.util.Objects;

/**
 * Concrete Mapper which converts to/from Maze and its serialisation friendly counterpart GameState.
 *
 * @author Thomas Ru - 300658840
 */
public class GameMapper implements Mapper<Maze, GameState> {

    /**
     * Convert from a Maze to its GameState representation.
     * @param maze - the Maze.
     * @return - a GameState representation ready for writing to file.
     */
    @Override
    public GameState toGameState(Maze maze) {
        GameState gameState = new GameState(maze.getRows(), maze.getCols());
        StringTileVisitor tileToString = new StringTileVisitor();
        Tile tile;

        // tiles and entities as strings
        String[][] board = gameState.getBoard();
        for (int y = 0; y < maze.getRows(); y++) {
            for (int x = 0; x < maze.getCols(); x++) {
                tile =  maze.getTileAt(new Position(x, y));
                board[y][x] = tile.accept(tileToString);
            }
        }

        // append monster strings on
        maze.getMonsters().forEach(m->{
            Position pos = m.getPos();
            board[pos.getY()][pos.getX()] += TileParsers.separator + monsterToString(m);
        });

        gameState.setBoard(board);
        return gameState;
    }


    /**
     * Gives the Maze object corresponding to the tiles and entities stored
     * in the GameState. Note key and treasure counts are also determined as loading occurs,
     * and the GameState object is mutated to reflect this.
     * @param gameState - the gameState, which may be constructed from JSON.
     * @return - the Maze object.
     */
    @Override
    public Maze fromGameState(GameState gameState) {
        int rows = gameState.getRows();
        int cols = gameState.getCols();
        String[][] board = gameState.getBoard();
        Maze maze = new Maze(rows, cols);
        String symbol;
        Position position;

        // parse String symbol at each board position
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                symbol = Objects.requireNonNull(board[y][x], "Board is null at row=" + x + ", col=" + y);
                if (symbol.isEmpty()) {
                    throw new IllegalArgumentException("Symbol cannot be empty");
                }
                position = new Position(x, y);
                Tile tile = TileParsers.parseTile(gameState, symbol, position); // TODO: this sets the no. of keys/treasures in gameState, please refactor!
                maze.setTileAt(tile);
            }
        }

        gameState.getMonsters().forEach(maze::setMonster);
        return maze;
    }

    /**
     * Converts a crab into a representation ready for suitable for later parsing.
     * @param monster - the crab.
     * @return - 'Crab'.
     */
    private String monsterToString(Monster monster) {
        StringBuilder result = new StringBuilder();
        result.append("Crab");
        result.append(MonsterParser.separator);
        result.append(monster.getDirection().name());
        return result.toString();
    }
}
