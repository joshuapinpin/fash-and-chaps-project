package nz.ac.wgtn.swen225.lc.domain;

/**
 * Abstract Tile class representing a tile in the game
 * Tiles can have different types and behaviors
 * Each tile has a position and accessibility status
 */
public abstract class Tile {
    private Position pos; //position of the tile in the maze

    /**
     * Constructor for tile with specified position
     * @param pos position of the tile
     */
    public Tile(Position pos){}

    /**
     * Getter for tile position
     * @return position of the tile
     */
    public Position getPos(){return null;}

    /**
     * Check if the tile is accessible
     * Only to be overridden by impassable tiles like Wall
     * @return true if accessible, false otherwise
     */
    public boolean isAccessible(){return true;}

    /**
     * Abstract method to handle player entering the tile
     * To be implemented by subclasses for specific behaviors
     * @param player player entering the tile
     */
    public abstract void onEnter(Player player);
}
