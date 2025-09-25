package nz.ac.wgtn.swen225.lc.domain.entities;

import nz.ac.wgtn.swen225.lc.domain.Player;

/**
 * Treasure class representing a treasure entity in the game
 * Treasures can be collected by the player to decrease the treasure count
 * Implements Entity interface for interaction
 * @author Hayley Far
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
        if(p == null){
            throw new IllegalArgumentException("Player cannot be null");
        }
        if(p.getTreasuresLeft() <= 0){
            throw new IllegalStateException("No treasures left to collect");
        }

        int oldSize = p.getTreasuresLeft();
        p.collectTreasure();

        assert p.getTreasuresLeft() == oldSize - 1;
        assert p.getTreasuresLeft() >= 0;

    }

    /**
     * Treasures can always be collected, so they are always removable
     * @return true
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        return true; // All treasures are considered equal
    }

    /**
     * Treasures can always be collected, so they are always removable
     * @return true
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        return true; // All treasures are considered equal
    }
}
