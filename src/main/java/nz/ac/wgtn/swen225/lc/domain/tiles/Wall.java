package nz.ac.wgtn.swen225.lc.domain.tiles;

import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;

/**
 * Wall class representing a wall tile in the game
 * Wall is impassable and does not allow player movement
 * Inherits from Tile class
 */
public class Wall extends Tile {
    /**
     * Constructor for wall tile with specified position
     * @param pos position of the wall tile
     */
    Wall(Position pos) {
        super(pos);
    }

    /**
     * Wall is not accessible by the player
     * @return false always
     */
    @Override
    public boolean isAccessible(){return false;}

    /**
     * Method to handle player entering the wall tile
     * Wall is impassable, so this method should not be called
     * @param p player attempting to enter the wall tile
     */
    @Override
    public void onEnter(Player p){
        throw new IllegalArgumentException("Player cannot enter a wall tile");
    }
}
