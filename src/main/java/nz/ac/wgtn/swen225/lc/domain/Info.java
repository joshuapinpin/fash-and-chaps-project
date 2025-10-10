package nz.ac.wgtn.swen225.lc.domain;

import java.util.function.Consumer;

/**
 * Info class representing an informational tile in the game
 * When the player enters this tile, a message is displayed
 * Inherits from Tile class
 * @author Hayley Far (300659141)
 */
public class Info extends Tile {
    //private final String message;

    /**
     * Constructor for info tile with specified message and position
     * @param pos position of the info tile
     */
    Info(Position pos){
        super(pos);
        //this.message = message;
    }

    /**
     * Static factory method to create an info tile with specified message and position
     * @param pos position of the info tile
     * @return new Info instance
     */
    public static Info of(Position pos){
        return new Info(pos);
    }

    /**
     * Method to handle player entering the info tile
     * Displays the informational message to the player, by using observer pattern
     * @param p player entering the info tile
     * @return Consumer to notify observers of informational message
     */
    @Override
    public Consumer<GameObserver> onEnter(Player p){
        return observer -> observer.onInfoMessage();
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
        return this.getPos().equals(info.getPos());
    }


    /**
     * Accept method for visitor pattern
     * @param visitor TileVisitor instance
     * @param <T> return type of the visitor
     * @return result of visitor's visitInfo method
     */
    @Override
    public <T> T accept(TileVisitor<T> visitor) {
        return visitor.visitInfo(this);
    }
}
