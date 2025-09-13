package nz.ac.wgtn.swen225.lc.domain;

/**
 * Door class representing a door entity in the game
 * Doors have a color and can be opened with a matching key
 * Implements Entity interface for interaction
 */
public class Door implements Entity{
    private String color;
    private boolean isOpen = false; //state of the door

    /**
     * Constructor for door with specified color
     * @param color color of the door
     */
    Door(String color){throw new UnsupportedOperationException();}

    /**
     * Method to handle player interaction with the door
     * To be able to unlock door with the correct key
     * @param p player interacting with the door
    */
    @Override
    public void onInteract(Player p){
        throw new UnsupportedOperationException();
    }

    /**
     * Getter for door color
     * @return color of the door
     */
    public String getColor(){throw new UnsupportedOperationException();}

    /**
     * Check if the door is open
     * @return true if door is open, false otherwise
     */
    public boolean isOpen() {
        throw new UnsupportedOperationException();
    }
}
