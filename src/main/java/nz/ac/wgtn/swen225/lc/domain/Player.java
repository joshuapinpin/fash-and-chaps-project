package nz.ac.wgtn.swen225.lc.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Player class representing the player in the game
 * Player has position, direction, keys collection, and treasure count
 * Provides methods to move, collect treasure, and manage keys
 */
public class Player{
    private List<Key> keys = new ArrayList<>(); //collection of keys the player has
    private Position pos; //current position of the player
    private Direction direction; //current direction the player is facing, enum Direction
    private int treasuresLeft; //number of treasures left to collect in the maze

    /**
     * Constructor for player with specified starting position
     * @param pos starting position of the player
     */
    Player(Position pos){throw new UnsupportedOperationException();}

    /**
     * Method to move the player in a specified direction
     * Updates position and direction accordingly
     * @param d direction to move in
     */
    public void move(Direction d){
        throw new UnsupportedOperationException();
    }

    /**
     * Method to add a key to the player's collection
     * @param k key to add
     */
    public void addKey(Key k){
        throw new UnsupportedOperationException();
    }

    /**
     * Method to decrement the treasure count when a treasure is collected
     */
    public void collectTreasure(){
        throw new UnsupportedOperationException();
    }

    /**
     * Set the current position of the player
     * @param pos new position
     */
    public void setPos(Position pos){
        throw new UnsupportedOperationException();
    }

    /**
     * Set the current direction the player is facing
     * @param d new direction
     */
    public void setDirection(Direction d){
        throw new UnsupportedOperationException();
    }

    /**
     * Get the current position of the player
     * @return current position
     */
    public Position getPos(){
        throw new UnsupportedOperationException();
    }

    /**
     * Get the current direction the player is facing
     * @return current direction
     */
    public Direction getDirection(){
        throw new UnsupportedOperationException();
    }

    /**
     * Check if player has collected on treasures in current level of maze
     * Using the treasuresLeft counter
     * @return true if all treasures collected, false otherwise
     */
    public boolean allTreasuresCollected(){
        throw new UnsupportedOperationException();
    }

}
