package nz.ac.wgtn.swen225.lc.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maze class representing the maze structure
 * Contains tiles, player reference, and dimensions
 * Provides methods to load maze from file and get tiles at positions
 */
public class Maze {
    private Map<Position,Tile> tileMap = new HashMap<>(); //map of positions to tiles
    private List<Tile> allTiles = new ArrayList<>(); //list of all tiles in maze
    private Player player; //reference to player in maze
    private int rows; int cols; //dimensions of maze

    /**
     * Constructor for maze with specified dimensions
     * @param rows number of rows
     * @param cols number of columns
     */
    Maze(int rows, int cols){throw new UnsupportedOperationException();}

    /**
     * Load maze from file
     * Setting up tiles collections
     * Subject to change based on file format
     */
    public void loadMaze(){
        throw new UnsupportedOperationException();
    }

    /**
     * Get tile at specified position
     * @param p position to get tile at
     * @return tile at position
     */
    public Tile getTileAt(Position p){
        throw new UnsupportedOperationException();
    }

}
