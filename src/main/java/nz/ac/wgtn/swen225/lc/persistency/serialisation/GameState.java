package nz.ac.wgtn.swen225.lc.persistency.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nz.ac.wgtn.swen225.lc.domain.*;

import java.util.*;

/**
 * Represents a game in a serialisation-friendly and human-friendly format.
 * Ready to be converted to/from JSON.
 * @author Thomas Ru - 300658840
 */
public class GameState {
    private static Mapper<Maze, GameState> mapper = new GameMapper();

    private final int rows;
    private final int cols;
    private int keyCount = 0;
    private int treasureCount = 0;
    private boolean loaded = false;
    private final List<Monster> monsters = new ArrayList<>();

    @JsonSerialize(using = BoardSerializer.class) // for pretty 2D array printing
    private String[][] board;

    /**
     * Create GameState object from ASCII art type array representing the board.
     * @param rows - the positive integer number of rows on the game board.
     * @param cols - the positive integer number of columns on the game board.
     */
    @JsonCreator
    public GameState(
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
     * Add a monster to the level.
     * @param monster - the Monster (e.g. crab).
     */
    public void setMonster(Monster monster) {
        monsters.add(monster);
    }

    /**
     * Get the list of monsters in the game.
     * @return - an unmodifiable BUT MUTABLE list
     */
    // TODO: discuss making monsters immutable w/ Hayley
    public List<Monster> getMonsters() {
        return Collections.unmodifiableList(monsters);
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
     * Adds 1 to the number of keys in the level.
     */
    public void incrementKeys() { keyCount++; }

    /**
     * Gets the number of keys in the level (i.e. maximum).
     * Useful for testing.
     * @return - the integer number of keys.
     */
    public int keyCount() {
        if (!loaded) {
            mapper.fromGameState(this);
            loaded = true;
        }
        return keyCount;
    }

    /**
     * Adds 1 to the number of treasures in the level.
     */
    public void incrementTreasures() { treasureCount++; }

    /**
     * Gets the number of treasures in the level (i.e. maximum).
     * Useful for testing.
     * @return - the integer number of treasures.
     */
    public int treasureCount() {
        if (!loaded) {
            mapper.fromGameState(this);
            loaded = true;
        }
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
        GameState other = (GameState) obj;
        return rows==other.rows
                && cols==other.cols
                && Arrays.deepEquals(board, other.board);
    }

    /**
     * Gives the hash, where equally sized boards with equal contents
     * implies equal hashes.
     * @return - the hash as an integer.
     */
    @Override public int hashCode() {
        return Objects.hash(rows, cols, Arrays.deepHashCode(board));
    }
}
