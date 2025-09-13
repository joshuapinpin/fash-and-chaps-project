package nz.ac.wgtn.swen225.lc.domain;

/**
 * Treasure class representing a treasure entity in the game
 * Treasures can be collected by the player to decrease the treasure count
 * Implements Entity interface for interaction
 */
public class Treasure implements Entity{
    /**
     * Constructor for treasure
     * Subject to change in terms of parameters
     */
    Treasure(){throw new UnsupportedOperationException();}

    /**
     * Method to handle player interaction with the treasure
     * To be able to collect the treasure
     * @param p player interacting with the treasure
     */
    @Override
    public void onInteract(Player p){
        throw new UnsupportedOperationException();
    }
}
