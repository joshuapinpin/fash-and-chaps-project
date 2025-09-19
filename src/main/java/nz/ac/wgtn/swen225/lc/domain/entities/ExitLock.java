package nz.ac.wgtn.swen225.lc.domain.entities;

import nz.ac.wgtn.swen225.lc.domain.Player;

/**
 * ExitLock class representing the exit lock entity in the game
 * ExitLock is impassable until all treasures are collected
 * Implements Entity interface for interaction
 */
public class ExitLock implements Entity {
    private boolean isPassable = false; //state of the exit lock

    /**
     * Constructor for exit lock
     * Subject to change in terms of parameters
     */
    ExitLock(){}

    /**
     * Method to handle player interaction with the exit lock
     * To be able to pass through if all treasures are collected
     * @param p player interacting with the exit lock
     */
    @Override
    public void onInteract(Player p){
        if(p.allTreasuresCollected()){
            isPassable = true;
        }
    }

    /**
     * Check if the exit lock is passable
     * @return true if exit lock is passable, false otherwise
     */
    @Override
    public boolean removeEntity() {
        return isPassable;
    }
}
