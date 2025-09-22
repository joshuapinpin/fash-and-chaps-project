package nz.ac.wgtn.swen225.lc.domain.entities;

import nz.ac.wgtn.swen225.lc.domain.Player;

/**
 * Door class representing a door entity in the game
 * Doors have a color and can be opened with a matching key
 * Implements Entity interface for interaction
 * @author Hayley Far
 */
public class Door implements Entity {
    private final Color doorColor;
    private boolean isOpen = false; //state of the door

    /**
     * Constructor for door with specified color
     * @param doorColor color of the door
     */
    Door(Color doorColor){
        this.doorColor = doorColor;
    }

    /**
     * Static factory method to create a door with specified color
     * @param doorColor color of the door
     * @return new Door instance
     */
    public static Door of(Color doorColor){
        return new Door(doorColor);
    }

    /**
     * Method to handle player interaction with the door
     * To be able to unlock door with the correct key
     * @param p player interacting with the door
    */
    @Override
    public void onInteract(Player p){
        if(p.getKeys().stream().anyMatch(key -> key.getColor().equals(this.doorColor))){
            isOpen = true;
        }
    }

    /**
     * Getter for door color
     * @return color of the door
     */
    public Color getColor(){return doorColor;}

    /**
     * Check if the door is open
     * @return true if door is open, false otherwise
     */
    @Override
    public boolean removeEntity() {
        return isOpen;
    }

    /**
     * Check if the door can be interacted with (pre interaction check)
     * Door can be interacted with if it is open or if the player has a matching key
     * @return true if the door can be interacted with, false otherwise
     */
    @Override
    public boolean canInteract(Player p) {
        return isOpen || p.getKeys().stream().anyMatch(key -> key.getColor().equals(this.doorColor));
    }
}
