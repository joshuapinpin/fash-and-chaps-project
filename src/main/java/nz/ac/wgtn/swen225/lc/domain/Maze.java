package nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;

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
}
