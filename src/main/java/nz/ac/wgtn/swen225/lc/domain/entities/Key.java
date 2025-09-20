package nz.ac.wgtn.swen225.lc.domain.entities;

import nz.ac.wgtn.swen225.lc.domain.Player;

/**
 * Key class representing a key entity in the game
 * Keys have a color and can be collected by the player
 * Implements Entity interface for interaction
 */
public class Key implements Entity {
    private final String color;

    /**
     * Constructor for key with specified color
     * @param color color of the key
     */
    Key(String color){this.color = color;}

    /**
     * Static factory method to create a key with specified color
     * @param color color of the key
     * @return new Key instance
     */
    public static Key of(String color){
        return new Key(color);
    }

    /**
     * Method to handle player interaction with the key
     * To be able to collect the key
     * @param p player interacting with the key
     */
    @Override
    public void onInteract(Player p){
        p.addKey(this);
    }

    /**
     * Getter for key color
     * @return color of the key
     */
    public String getColor(){
        return color;
    }
}
