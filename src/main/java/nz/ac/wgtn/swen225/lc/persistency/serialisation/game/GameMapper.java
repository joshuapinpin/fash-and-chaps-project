package nz.ac.wgtn.swen225.lc.persistency.serialisation.game;

import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.domain.Monster;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;
import nz.ac.wgtn.swen225.lc.persistency.domainutil.StringTileVisitor;
import nz.ac.wgtn.swen225.lc.persistency.parse.MonsterParser;
import nz.ac.wgtn.swen225.lc.persistency.parse.ParsedTile;
import nz.ac.wgtn.swen225.lc.persistency.parse.TileParsers;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.player.PlayerMapper;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.api.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Concrete Mapper which converts to/from Maze and its
 * serialisation friendly counterpart GameState.
 * @author Thomas Ru - 300658840
 */
public class GameMapper implements Mapper<LoadedMaze, GameState> {
    private final PlayerMapper playerMapper = new PlayerMapper();

    /**
     * Convert from a Maze to its GameState representation.
     * @param data - the Maze.
     * @return - a GameState representation ready for writing to file.
     */
    public GameState toState(Maze data, int levelNumber, int maxTreasures, int maxKeys, int time) {
        GameState gameState = new GameState(
                data.getRows(),
                data.getCols(),
                time,
                new LevelInfo(levelNumber, maxKeys, maxTreasures),
                playerMapper.toState(data.getPlayer())
        );
        StringTileVisitor tileToString = new StringTileVisitor();

        // tiles and entities as strings
        Tile tile;
        String[][] board = gameState.getBoard();
        for (int y = 0; y < data.getRows(); y++) {
            for (int x = 0; x < data.getCols(); x++) {
                tile =  data.getTileAt(new Position(x, y));
                board[y][x] = tile.accept(tileToString);
            }
        }

        // append monster strings on
        data.getMonsters().forEach(m->{
            Position pos = m.getPos();
            board[pos.getY()][pos.getX()] += TileParsers.separator + monsterToString(m);
        });

        gameState.setBoard(board);
        return gameState;
    }


    /**
     * Mostly here for completeness and implementing the interface.
     * Converts from LoadedMaze to JSON serialisation friendly GameState.
     * @param data - the LoadedMaze to serialise.
     * @return - the GameState representation of the LoadedMaze.
     */
    @Override
    public GameState toState(LoadedMaze data) {
        LevelInfo meta = data.levelInfo();
        return toState(data.maze(), meta.levelNumber(), meta.maxTreasures(), meta.maxKeys(), data.time());
    }

    /**
     * Gives the LoadedMaze object corresponding to the tiles and entities
     * stored in the GameState.
     * @param state - the gameState, which may be constructed from JSON.
     * @return - the Maze object.
     */
    @Override
    public LoadedMaze fromState(GameState state) {
        int rows = state.getRows();
        int cols = state.getCols();
        String[][] board = state.getBoard();
        Maze maze = new Maze(rows, cols);

        // parse String symbol at each board position
        String symbol;
        Position position;
        List<ParsedTile<?>> parsedTiles = new ArrayList<>();
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                //preconditions
                symbol = Objects.requireNonNull(board[y][x], "Board is null at row=" + x + ", col=" + y);
                if (symbol.isEmpty()) {
                    throw new IllegalArgumentException("Symbol cannot be empty");
                }
                position = new Position(x, y);
                parsedTiles.add(TileParsers.parseTile(state, symbol, position));
            }
        }

        // append on monsters for each tile after tile parsing, since they're independent of tiles
        parsedTiles.forEach(info->{
            Tile tile = info.tile();
            if (info.monster().isPresent()) {maze.setMonster(info.monster().get());}
            maze.setTileAt(tile);
        });

        Player player = playerMapper.fromState(state.getPlayer());
        player.setTotalTreasures(state.maxTreasures());
        maze.setPlayer(player);
        return new LoadedMaze(maze, state.getTime(), state.getLevelInfo());
    }

    /**
     * Converts a Monster into a representation ready for suitable for later parsing.
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
