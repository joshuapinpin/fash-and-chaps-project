package nz.ac.wgtn.swen225.lc.domain.entities;

import nz.ac.wgtn.swen225.lc.domain.Player;

/**
 * Treasure class representing a treasure entity in the game
 * Treasures can be collected by the player to decrease the treasure count
 * Implements Entity interface for interaction
 */
public class Treasure implements Entity {
    /**
     * Constructor for treasure
     * Subject to change in terms of parameters
     */
    Treasure(){}

    /**
     * Static factory method to create a treasure
     * @return new Treasure instance
     */
    public static Treasure of(){
        return new Treasure();
    }

    /**
     * Method to handle player interaction with the treasure
     * To be able to collect the treasure
     * @param p player interacting with the treasure
     */
    @Override
    public void onInteract(Player p){
        p.collectTreasure();
    }
}
