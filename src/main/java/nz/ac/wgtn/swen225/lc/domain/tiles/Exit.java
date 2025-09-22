package nz.ac.wgtn.swen225.lc.domain.tiles;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;

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
     */
    @Override
    public void onEnter(Player p){
        //Drafted but need more information
        //Need game controller? and to find what level we on
        /**
        int nextLevel = currentLevel + 1;
        if(nextLevel > MAXLEVELS){
            //change to victory state
            //GameController.exitGame();
        } else {
            //GameController.startNewGame(nextLevel);
        }
        //GameController.startNewGame();**/
        throw new UnsupportedOperationException();
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
