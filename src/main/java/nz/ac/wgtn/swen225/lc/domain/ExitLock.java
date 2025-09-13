package nz.ac.wgtn.swen225.lc.domain;

/**
 * ExitLock class representing the exit lock entity in the game
 * ExitLock is impassable until all treasures are collected
 * Implements Entity interface for interaction
 */
public class ExitLock implements Entity{
    private boolean isPassable = false; //state of the exit lock

    /**
     * Constructor for exit lock
     * Subject to change in terms of parameters
     */
    ExitLock(){throw new UnsupportedOperationException();}

    /**
     * Method to handle player interaction with the exit lock
     * To be able to pass through if all treasures are collected
     * @param p player interacting with the exit lock
     */
    @Override
    public void onInteract(Player p){
        throw new UnsupportedOperationException();
    }
}
