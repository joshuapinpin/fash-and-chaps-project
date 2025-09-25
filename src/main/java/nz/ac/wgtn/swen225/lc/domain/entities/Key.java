package nz.ac.wgtn.swen225.lc.domain.entities;

import nz.ac.wgtn.swen225.lc.domain.Player;

/**
 * Key class representing a key entity in the game
 * Keys have a color and can be collected by the player
 * Implements Entity interface for interaction
 * @author Hayley Far
 */
public class Key implements Entity {
    private final EntityColor keyColor;

    /**
     * Constructor for key with specified color
     * @param keyColor color of the key
     */
    Key(EntityColor keyColor){
        this.keyColor = keyColor;
    }

    /**
     * Static factory method to create a key with specified color
     * @param keyColor color of the key
     * @return new Key instance
     */
    public static Key of(EntityColor keyColor){
        return new Key(keyColor);
    }

    /**
     * Method to handle player interaction with the key
     * To be able to collect the key
     * @param p player interacting with the key
     */
    @Override
    public void onInteract(Player p){
        if(p == null){
            throw new IllegalArgumentException("Player cannot be null");
        }
        p.addKey(this);

        assert p.getKeys().contains(this);
    }

    /**
     * Getter for key color
     * @return color of the key
     */
    public EntityColor getColor(){
        return keyColor;
    }

    /**
     * Override equals method to compare keys based on color
     * @param obj object to compare with
     * @return true if keys are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        Key key = (Key) obj;
        return this.keyColor == key.keyColor;
    }
}
