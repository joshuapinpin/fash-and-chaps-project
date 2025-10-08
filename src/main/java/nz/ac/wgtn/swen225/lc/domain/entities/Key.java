package nz.ac.wgtn.swen225.lc.domain.entities;

import nz.ac.wgtn.swen225.lc.domain.GameObserver;
import nz.ac.wgtn.swen225.lc.domain.Player;

import java.util.function.Consumer;

/**
 * Key class representing a key entity in the game
 * Keys have a color and can be collected by the player
 * Implements Entity interface for interaction
 * @author Hayley Far (300659141)
 */
public class Key implements Entity {
    private final EntityColor keyColor; //color of the key

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
     * To be able to collect the key and add to player's inventory
     * @param p player interacting with the key
     * @return Consumer to notify observers of key collection
     */
    @Override
    public Consumer<GameObserver> onInteract(Player p){
        if(p == null){
            throw new IllegalArgumentException("Player cannot be null");
        }
        p.addKey(this);

        assert p.getKeys().contains(this);
        return observer -> observer.onKeyCollected(this);
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

    /**
     * Accept method for visitor pattern
     * @param visitor the visitor to accept
     * @param <T> the return type of the visitor's visitKey method
     * @return result of visitor's visitKey method
     */
    @Override
    public <T> T accept(EntityVisitor<T> visitor) {
        return visitor.visitKey(this);
    }
}
