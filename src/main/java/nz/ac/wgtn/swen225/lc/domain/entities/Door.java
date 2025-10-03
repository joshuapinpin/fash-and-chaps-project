package nz.ac.wgtn.swen225.lc.domain.entities;

import nz.ac.wgtn.swen225.lc.domain.GameObserver;
import nz.ac.wgtn.swen225.lc.domain.Player;

import java.util.function.Consumer;

/**
 * Door class representing a door entity in the game
 * Doors have a color and can be opened with a matching key
 * Implements Entity interface for interaction
 * @author Hayley Far
 */
public class Door implements Entity {
    private final EntityColor doorColor;
    private boolean isOpen = false; //state of the door

    /**
     * Constructor for door with specified color
     * @param doorColor color of the door
     */
    Door(EntityColor doorColor){
        this.doorColor = doorColor;
    }

    /**
     * Static factory method to create a door with specified color
     * @param doorColor color of the door
     * @return new Door instance
     */
    public static Door of(EntityColor doorColor){
        return new Door(doorColor);
    }

    /**
     * Check if the player has the correct key for the door
     * @param p player to check
     * @return true if player has the correct key, false otherwise
     */
    public boolean hasCorrectKey(Player p) {
        if(p == null){
            throw new IllegalArgumentException("Player cannot be null");
        }
        return p.getKeys().stream().anyMatch(key -> key.getColor().equals(this.doorColor));
    }

    /**
     * Method to handle player interaction with the door
     * To be able to unlock door with the correct key
     * @param p player interacting with the door
     * @return Consumer to notify observers of door opening
    */
    @Override
    public Consumer<GameObserver> onInteract(Player p){
        if(p == null){
            throw new IllegalArgumentException("Player cannot be null");
        }

        boolean hasKey = hasCorrectKey(p);

        if(!isOpen && !hasKey){
            throw new IllegalStateException("Door is closed and player does not have the correct key");
        }

        if(hasKey){isOpen = true;}
        return observer -> observer.onDoorOpened(this);
    }

    /**
     * Getter for door color
     * @return color of the door
     */
    public EntityColor getColor(){return doorColor;}

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
        if(p == null){
            throw new IllegalArgumentException("Player cannot be null");
        }
        return isOpen || hasCorrectKey(p);
    }

    /**
     * Override equals method to compare doors based on color and state
     * @param obj object to compare with
     * @return true if doors are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        Door door = (Door) obj;
        return this.doorColor == door.doorColor && this.isOpen == door.isOpen;
    }
}
