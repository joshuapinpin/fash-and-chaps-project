package nz.ac.wgtn.swen225.lc.persistency.levelloader;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;
import nz.ac.wgtn.swen225.lc.persistency.levelloader.parse.TileParsers;

import java.util.Arrays;
import java.util.Objects;

/**
 * Creates/loads level from human friendly JSON format.
 * @author Thomas Ru - 300658840
 */
public class LevelMaker {
    private final int rows;
    private final int cols;
    private int keyCount = 0;
    private int treasureCount = 0;
    private boolean loaded = false;

    @JsonSerialize(using = BoardSerializer.class) // for pretty 2D array printing
    private String[][] board;

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
            @JsonProperty("cols") int cols
    ) {
        boolean minSize = rows > 0 && cols > 0;
        if (!minSize) {
            throw new IllegalArgumentException("Game board must be at least 1x1 in size.");
        }

        this.rows = rows;
        this.cols = cols;
        board = new String[rows][cols];
    }

    /**
     * Gets the board with String symbols representing each tile.
     * Used by Jackson to infer serialisation.
     * @return - the String[][] board, as a deep copy for safety.
     */
    public String[][] getBoard() {
        String[][] copy  = new String[rows][cols];
        for (int y = 0; y < rows; y++) {
            System.arraycopy(board[y], 0, copy[y], 0, cols);
        }
        return copy;
    }

    /**
     * Sets the board of String symbols representing all the tiles in a level.
     * @param board - the String[][] board.
     */
    public void setBoard(String[][] board) {
        this.board = board;
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
     * in the board. Note key and treasure counts are also determined as loading occurs.
     * @return - the Maze object.
     */
    public Maze loadLevel() {
        Maze maze = new Maze(rows, cols);
        String symbol;
        Position position;

        // parse String symbol at each board position
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                symbol = Objects.requireNonNull(board[y][x], "Board is null at row="+x+", col="+y);
                if (symbol.isEmpty()) {
                    throw new IllegalArgumentException("Symbol cannot be empty");
                }
                position = new Position(x, y);
                Tile tile = TileParsers.parseTile(this, symbol, position);
                maze.setTileAt(tile);
            }
        }
        loaded = true;
        return maze;
    }

    public void incrementKeys() { keyCount++; }

    public int keyCount() {
        if (!loaded) { loadLevel(); }
        return keyCount;
    }

    public void incrementTreasures() { treasureCount++; }

    public int treasureCount() {
        if (!loaded) { loadLevel(); }
        return treasureCount;
    }

    /**
     * Gives the String representation of a LevelMaker's board.
     * @return - a prettified version of the LevelMaker's 2D array board.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[\n");
        for (String[] row : board) {
            result.append(Arrays.toString(row));
            result.append("\n");
        }
        result.append("]");
        return result.toString();
    }

    /**
     * Compare whether some object is structurally equal to a LevelMaker.
     * @param obj - the reference object with which to compare.
     * @return - true if they are equal, false otherwise.
     */
    @Override public boolean equals(Object obj) {
        if (this == obj) return true;
        if(obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        LevelMaker other = (LevelMaker) obj;
        return rows==other.rows
                && cols==other.cols
                && Arrays.deepEquals(board, other.board);
    }

    /**
     * Gives the hash of a LevelMaker, where equally sized boards with equal contents
     * implies equal hashes.
     * @return - the hash as an integer.
     */
    @Override public int hashCode() {
        return Objects.hash(rows, cols, Arrays.deepHashCode(board));
    }
}
