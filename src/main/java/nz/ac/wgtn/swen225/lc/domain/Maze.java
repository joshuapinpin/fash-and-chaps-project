package nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.entities.*;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;
import nz.ac.wgtn.swen225.lc.domain.tiles.*;

/**
 * Maze class representing the maze structure
 * Contains tiles, player reference, and dimensions
 * Provides methods to load maze from file and get tiles at positions
 */
public class Maze {
    private Tile[][] tileGrid; //2D array of tiles: [rows][cols]
    private Player player; //reference to player in maze
    private int rows; int cols; //dimensions of maze

    /**
     * Constructor for maze with specified dimensions
     * @param rows number of rows
     * @param cols number of columns
     */
    public Maze(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.tileGrid = new Tile[rows][cols];

        //initialise player and set start position
        this.player = Player.getInstance();
        this.player.initialiseStartPos(rows, cols);
    }

    /**
     * Get 2D array of tiles in maze
     * @return 2D array of tiles
     */
    public Tile[][] getTileGrid() {
        return tileGrid;
    }

    /**
     * Get tile at specified position
     * @param p position to get tile at
     * @return tile at position
     */
    public Tile getTileAt(Position p){
        int x = p.getX();
        int y = p.getY();
        if (x < 0 || x >= cols || y < 0 || y >= rows) {
            throw new IndexOutOfBoundsException("Position out of maze bounds: " + p);
        }
        return tileGrid[y][x];
    }

    /**
     * Add tile to 2D tile array at specified position
     * @param tile tile to set
     */
    public void setTileAt(Tile tile) {
        assert tile != null : "Tile cannot be null";
        Position pos = tile.getPos();
        assert pos != null : "Tile position cannot be null";

        int x = pos.getX();
        int y = pos.getY();
        if (x < 0 || x >= cols || y < 0 || y >= rows) {
            throw new IndexOutOfBoundsException("Position out of maze bounds: " + pos);
        }
        tileGrid[y][x] = tile;

        assert getTileAt(pos) == tile : "Tile was not set correctly at position: " + pos;
    }

    /**
     * Move player in specified direction if target tile is accessible
     * The direction dependent on what user input is
     * @param direction direction to move player
     */
    public void movePlayer(Direction direction){
        //find tile in the direction player wants to move
        if(player == null){
            throw new NullPointerException("Player not set in maze");
        }

        Position toMove = direction.apply(player.getPos());
        Tile targetTile = getTileAt(toMove);

        if(targetTile.isAccessible(this.player)){
            player.move(direction);
            targetTile.onEnter(player);
        }
    }

    /**
     * Getter for number of rows in maze
     * @return number of rows
     */
    public int getRows(){
        return this.rows;
    }

    /**
     * Getter for number of columns in maze
     * @return number of columns
     */
    public int getCols(){
        return this.cols;
    }

    /**
     * Setter for player reference in maze
     * @param player player to set
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * Getter for player reference in maze
     * @return player in maze
     */
    public Player getPlayer(){
        return this.player;
    }

    /**
     * String representation of maze for debugging in JUnit tests
     * Going through each tile in maze dimensions and getting symbol for it
     * @return string representation of maze
     */
    @Override
    public String toString(){
        String result = "";
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                Position pos = new Position(c, r);
                String symbol = getSymbol(pos);
                result += symbol + " ";
            }
            result += "\n";
        }
        return result;
    }

    /**
     * Get symbol for tile at specified position for string representation
     * P = Player, K = Key, D = Door, T = Treasure, L = ExitLock
     * ~ = Water, W = Wall, F = Free, E = Exit, I = Info
     * @param pos position to get symbol for
     * @return symbol representing tile at position
     */
    public String getSymbol(Position pos) {
        if (player != null && player.getPos().equals(pos)) return "P";
        Tile tile = getTileAt(pos);

        if (tile instanceof Wall) {
            return "W";
        } else if (tile instanceof Free) {
            Free freeTile = (Free) tile;
            if (freeTile.getCollectable().isPresent()) {
                Entity entity = freeTile.getCollectable().get();
                if (entity instanceof Key) {
                    return "K";
                } else if (entity instanceof Door) {
                    return "D";
                } else if (entity instanceof Treasure) {
                    return "T";
                } else if (entity instanceof ExitLock) {
                    return "L";
                }
            }
        } else if (tile instanceof Exit) {
            return "E";
        } else if (tile instanceof Water) {
            return "~";
        } else if (tile instanceof Info){
            return "I";
        }
        return "F";
    }
}
