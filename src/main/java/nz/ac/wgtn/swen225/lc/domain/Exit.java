package nz.ac.wgtn.swen225.lc.domain;

/**
 * Exit class representing the exit tile in the game
 * The player can enter this tile to finish the level
 * Extends Tile class to inherit tile properties
 */
public class Exit extends Tile{

    //Potential field of GameController to trigger level complete?

    /**
     * Constructor for exit tile with specified position
     * @param pos position of the exit tile
     */
    Exit(Position pos){
        super(pos);
    }

    /**
     * Method to handle player entering the exit tile
     * Triggers level completion when the player enters this tile
     * @param p player entering the exit tile
     */
    @Override
    public void onEnter(Player p){
        throw new UnsupportedOperationException();
    }
}
