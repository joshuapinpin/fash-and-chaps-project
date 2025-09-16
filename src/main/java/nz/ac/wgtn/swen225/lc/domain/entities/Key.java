package nz.ac.wgtn.swen225.lc.domain;

/**
 * Key class representing a key entity in the game
 * Keys have a color and can be collected by the player
 * Implements Entity interface for interaction
 */
public class Key implements Entity {
    private String color;

    /**
     * Constructor for key with specified color
     * @param color color of the key
     */
    Key(String color){throw new UnsupportedOperationException();}

    /**
     * Method to handle player interaction with the key
     * To be able to collect the key
     * @param p player interacting with the key
     */
    @Override
    public void onInteract(Player p){
        throw new UnsupportedOperationException();
    }

    /**
     * Getter for key color
     * @return color of the key
     */
    public String getColor(){
        throw new UnsupportedOperationException();
    }
}
