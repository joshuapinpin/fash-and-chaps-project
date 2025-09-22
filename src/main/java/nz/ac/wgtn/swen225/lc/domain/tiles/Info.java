package nz.ac.wgtn.swen225.lc.domain.tiles;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;

/**
 * Info class representing an informational tile in the game
 * When the player enters this tile, a message is displayed
 * Inherits from Tile class
 * @author Hayley Far
 */
public class Info extends Tile {
    private final String message;

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
     * Static factory method to create an info tile with specified message and position
     * @param message informational message to display
     * @param pos position of the info tile
     * @return new Info instance
     */
    public static Info of(String message, Position pos){
        return new Info(message, pos);
    }

    /**
     * Method to handle player entering the info tile
     * Displays the informational message to the player
     * @param p player entering the info tile
     */
    @Override
    public void onEnter(Player p){
        //Need to sort out with app to access this
        //e.g. GameController.showMessage(message, "Info Field");
        //but not sure what tha method is for exactly
    }

    /**
     * Getter for informational message
     * @return informational message
     */
    public String getMessage(){
        return message;
    }

    /**
     * Override equals method to compare info tiles based on position and message
     * @param obj object to compare with
     * @return true if info tiles are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        Info info = (Info) obj;
        return this.getPos().equals(info.getPos()) && this.message.equals(info.message);
    }
}
