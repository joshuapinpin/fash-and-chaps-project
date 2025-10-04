package nz.ac.wgtn.swen225.lc.domain.tiles;

import nz.ac.wgtn.swen225.lc.domain.GameObserver;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;

import java.util.function.Consumer;

/**
 * Exit class representing the exit tile in the game
 * The player can enter this tile to finish the level
 * Extends Tile class to inherit tile properties
 * @author Hayley Far
 */
public class Exit extends Tile {

    //Potential field of GameController to trigger level complete?

    /**
     * Constructor for exit tile with specified position
     * @param pos position of the exit tile
     */
    Exit(Position pos){
        super(pos);
    }

    /**
     * Static factory method to create an exit tile with specified position
     * @param pos position of the exit tile
     * @return new Exit instance
     */
    public static Exit of(Position pos){
        return new Exit(pos);
    }

    /**
     * Method to handle player entering the exit tile
     * Triggers level completion when the player enters this tile
     * @param p player entering the exit tile
     * @return Consumer to notify observers of level completion
     */
    @Override
    public Consumer<GameObserver> onEnter(Player p){
        if(p == null){
            throw new IllegalArgumentException("Player cannot be null");
        }
        return observer -> observer.onLevelComplete();
    }

    /**
     * Override equals method to compare exit tiles based on position
     * @param obj object to compare with
     * @return true if exit tiles are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        Exit exit = (Exit) obj;
        return this.getPos().equals(exit.getPos());
    }
}
