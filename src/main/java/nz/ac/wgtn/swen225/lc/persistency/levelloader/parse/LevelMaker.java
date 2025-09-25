package nz.ac.wgtn.swen225.lc.persistency.levelloader.parse;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.persistency.levelloader.BoardSerializer;
import java.util.*;

/**
 * Creates/loads level from human friendly JSON format.
 * @author Thomas Ru - 300658840
 */
public class LevelMaker {
    private final int rows;
    private final int cols;
    private final Map<String, TileParser> legend = new HashMap<>();

    @JsonSerialize(using = BoardSerializer.class)
    private final String[][] board;

    /**
     * Create LevelMaker object from ASCII art type array representing the board.
     * Useful for testing, but in-game LevelMaker instances will be constructed
     * directly from JSON.
     * @param rows - the positive integer number of rows on the game board.
     * @param cols - the positive integer number of columns on the game board.
     */
    @JsonCreator
    public LevelMaker(
            @JsonProperty("rows") int rows,
            @JsonProperty("cols") int cols) {
        assert rows > 0 && cols > 0 : "Game board must be at least 1x1 in size.";
        this.rows = rows;
        this.cols = cols;
        board = new String[rows][cols];
        initialiseParsers();
    }

    /**
     * Sets all the mappings between String symbols and TileParsers in the legend.
     */
    private void initialiseParsers() {
        TileParserFactory factory = new TileParserFactory();
        setParser(factory.createParser("F", FreeParser::new));
        setParser(factory.createParser("W", WallParser::new));
        setParser(factory.createParser("~", WaterParser::new));
        setParser(factory.createParser("I", InfoParser::new));
        setParser(factory.createParser("E", ExitParser::new));
    }

    /**
     * Alter the mappings between String symbols and the corresponding Tiles created.
     * @param parser - the TileParser for a particular tile type.
     */
    private void setParser(TileParser parser) {
        Objects.requireNonNull(parser, "Tile parser cannot be null.");
        String symbol = Objects.requireNonNull(parser.symbol(), "Symbol cannot be null.");
        assert !symbol.isEmpty() : "Symbol cannot be empty.";
        legend.put(symbol, parser);
    }

    /**
     * Gets the board with String symbols representing each tile.
     * Used by Jackson to infer serialisation.
     * @return - the String[][] board, as a deep copy for safety.
     */
    public String[][] getBoard() {
        String[][] copy  = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, cols);
        }
        return copy;
    }

    /**
     * Get number of rows in game board.
     * Used by Jackson to infer serialisation.
     * @return - integer number of rows.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Get number of columns in game board.
     * Used by Jackson to infer serialisation.
     * @return - integer number of columns.
     */
    public int getCols() {
        return cols;
    }

    /**
     * Gives the Maze object corresponding to the tiles and entities listed
     * in the board.
     * @return - the Maze object.
     */
    public Maze loadLevel() {
        Maze maze = new Maze(rows, cols);
        TileParser parser;
        String symbol;
        Position position;

        // parse String symbol at each board position
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                symbol = board[y][x];
                assert !symbol.isEmpty() : "Symbol cannot be empty.";
                parser = legend.get(symbol.split(TileParser.separator)[0]);

                assert parser != null : "Unknown symbol: " + symbol;
                position = new Position(x, y);
                maze.setTileAt(parser.parse(this, symbol, position));
            }
        }
        return maze;
    }
}
