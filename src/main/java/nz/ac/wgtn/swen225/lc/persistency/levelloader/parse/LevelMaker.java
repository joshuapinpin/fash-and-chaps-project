package nz.ac.wgtn.swen225.lc.persistency.levelloader.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import nz.ac.wgtn.swen225.lc.domain.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Creates/loads level from human friendly JSON format.
 * @author Thomas Ru - 300658840
 */
public class LevelMaker {
    private final int rows;
    private final int cols;
    private final String[][] board;
    private final Map<String, TileParser> legend = new HashMap<>();

    /**
     * Create LevelMaker object from ASCII art type array representing the board.
     * Useful for testing, but in-game LevelMaker instances will be constructed
     * directly from JSON.
     * @param rows - the positive integer number of rows on the game board.
     * @param cols - the positive integer number of columns on the game board.
     */
    public LevelMaker(int rows, int cols) {
        assert rows > 0 && cols > 0 : "Game board must be at least 1x1 in size.";
        this.rows = rows;
        this.cols = cols;
        board = new String[rows][cols];

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
     * @return - integer number of rows.
     */
    public int rows() {
        return rows;
    }

    /**
     * Get number of columns in game board.
     * @return - integer number of columns.
     */
    public int cols() {
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
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                symbol = board[i][j].split(TileParser.separator)[0];
                assert !symbol.isEmpty() : "Symbol cannot be empty.";
                parser = legend.get(symbol);
                position = new Position(i, j);
                maze.setTileAt(parser.parse(this, symbol, position));
            }
        }
        return maze;
    }

    /**
     * Main function for bootstrapping. Use this to write a blank level to JSON, then
     * edit the symbols in the JSON directly to create levels.
     * @param args - unused CLI arguments.
     */
    public static void main(String[] args) {
        int mapSize = 23;
        String defaultSymbol = "F";

        // default board
        LevelMaker bootstrap =  new LevelMaker(mapSize, mapSize);
        for (int i = 0; i < mapSize; i++) {
            Arrays.fill(bootstrap.board[i], defaultSymbol);
        }

        // write default board to JSON file
        Path path = Paths.get(System.getProperty("user.home"), "bootstrap.json");
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(path.toFile(), bootstrap.board);
        } catch (IOException e) {
            throw new Error("Unable to serialize bootstrap level", e);
        }
        System.out.println("Wrote file to: " + path.toAbsolutePath());
    }
}
