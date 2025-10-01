package nz.ac.wgtn.swen225.lc.domain.entities;

import nz.ac.wgtn.swen225.lc.domain.GameObserver;
import nz.ac.wgtn.swen225.lc.domain.Player;

import java.util.function.Consumer;

/**
 * ExitLock class representing the exit lock entity in the game
 * ExitLock is impassable until all treasures are collected
 * Implements Entity interface for interaction
 * @author Hayley Far
 */
public class ExitLock implements Entity {
    private boolean isPassable = false; //state of the exit lock

    /**
     * Constructor for exit lock
     * Subject to change in terms of parameters
     */
    ExitLock(){}

    /**
     * Static factory method to create an exit lock
     * @return new ExitLock instance
     */
    public static ExitLock of(){
        return new ExitLock();
    }

    /**
     * Method to handle player interaction with the exit lock
     * To be able to pass through if all treasures are collected
     * @param p player interacting with the exit lock
     * @return Consumer to notify observers of exit lock state change (if any, empty for now)
     */
    @Override
    public Consumer<GameObserver> onInteract(Player p){
        if(p == null){
            throw new IllegalArgumentException("Player cannot be null");
        }

        if(p.allTreasuresCollected()){
            assert p.getTreasuresCollected() == p.getTotalTreasures() : "Player should have collected all treasures";
            isPassable = true;
        }
        return observer -> {}; // No notification needed when exit lock becomes passable
    }

    /**
     * Check if the exit lock is passable
     * @return true if exit lock is passable, false otherwise
     */
    @Override
    public boolean removeEntity() {
        return isPassable;
    }

    /**
     * Check if the exit lock can be interacted with (pre interaction check)
     * Exit lock can be interacted with if it is passable or if the player has collected all treasures
     * @return true if the exit lock can be interacted with, false otherwise
     */
    @Override
    public boolean canInteract(Player p) {
        if(p == null){
            throw new IllegalArgumentException("Player cannot be null");
        }
        return isPassable || p.allTreasuresCollected();
    }

    /**
     * Override equals method to compare exit locks
     * @param obj object to compare with
     * @return true if exit locks are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        ExitLock exitLock = (ExitLock) obj;
        return this.isPassable == exitLock.isPassable;
    }
}
