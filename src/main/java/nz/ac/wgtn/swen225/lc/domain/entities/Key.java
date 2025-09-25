package nz.ac.wgtn.swen225.lc.domain.entities;

import nz.ac.wgtn.swen225.lc.domain.Player;

/**
 * Key class representing a key entity in the game
 * Keys have a color and can be collected by the player
 * Implements Entity interface for interaction
 * @author Hayley Far
 */
public class Key implements Entity {
<<<<<<< HEAD
    private final EntityColor keyColor;
=======
    private final Color keyColor;
>>>>>>> 102d9c53670d59a807add360581fce7bcfb1ad47

    /**
     * Constructor for key with specified color
     * @param keyColor color of the key
     */
<<<<<<< HEAD
    Key(EntityColor keyColor){
=======
    Key(Color keyColor){
>>>>>>> 102d9c53670d59a807add360581fce7bcfb1ad47
        this.keyColor = keyColor;
    }

    /**
     * Static factory method to create a key with specified color
     * @param keyColor color of the key
     * @return new Key instance
     */
<<<<<<< HEAD
    public static Key of(EntityColor keyColor){
=======
    public static Key of(Color keyColor){
>>>>>>> 102d9c53670d59a807add360581fce7bcfb1ad47
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
<<<<<<< HEAD
    public EntityColor getColor(){
=======
    public Color getColor(){
>>>>>>> 102d9c53670d59a807add360581fce7bcfb1ad47
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
