package nz.ac.wgtn.swen225.lc.domain;

/**
 * Info class representing an informational tile in the game
 * When the player enters this tile, a message is displayed
 * Inherits from Tile class
 */
public class Info extends Tile{
    private String message;

    /**
     * Constructor for info tile with specified message and position
     * @param message informational message to display
     * @param pos position of the info tile
     */
    Info(String message, Position pos){
        super(pos);
        this.message = message;
    }

    /**
     * Method to handle player entering the info tile
     * Displays the informational message to the player
     * @param p player entering the info tile
     */
    @Override
    public void onEnter(Player p){
        throw new UnsupportedOperationException();
    }

    /**
     * Getter for informational message
     * @return informational message
     */
    public String getMessage(){
        throw new UnsupportedOperationException();
    }
}
