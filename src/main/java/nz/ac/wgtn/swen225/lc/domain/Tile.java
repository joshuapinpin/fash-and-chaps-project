package nz.ac.wgtn.swen225.lc.domain;

/**
 * Represents a single tile in the maze
 * Each tile has a position and defines behaviour when a player steps on it
 * Subclasses specify the type of tile (eg. wall, free, exit, info)
 */

public abstract class Tile {
    //Fields
    Position pos;

    //Constructor
    public Tile(Position pos){}

    //Getter for position
    public Position pos(){return null;}

    //Can player walk on the tile or is it blocked.
    public boolean isAccessible(){return true;}

    /*
    * What happens when a player steps on tile
    * eg. is it blocked? show message? trigger condition?
    * */
    public abstract void onEnter(Player player);
}
