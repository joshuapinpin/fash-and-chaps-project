package nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.entities.Key;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Player class representing the player in the game
 * Player has position, direction, keys collection, and treasure count
 * Provides methods to move, collect treasure, and manage keys
 *
 * Player starts in the center of the maze at (7,7) for 15x15 maze
 */
public class Player{
    private static final Player INSTANCE = new Player(new Position(7,7)); //singleton instance

    private List<Key> keys = new ArrayList<>(); //collection of keys the player has
    private Position pos; //current position of the player
    private Direction direction; //current direction the player is facing, enum Direction
    private int treasuresLeft; //number of treasures left to collect in the maze

    /**
     * Constructor for player with specified starting position
     * Made private for singleton pattern
     * @param pos starting position of the player
     */
    private Player(Position pos){
        this.pos = pos;
        this.direction = Direction.DOWN; //default starting direction
        this.treasuresLeft = -1; //to be set when maze is loaded (check via assert when loading)
    }

    /**
     * Get the singleton instance of the player
     * @return singleton player instance
     */
    public static Player getInstance() {
        return INSTANCE;
    }

    /**
     * Method to move the player in a specified direction
     * Updates position and direction accordingly
     * @param direction direction to move in
     */
    public void move(Direction direction){
        this.direction = direction;
        this.pos = direction.apply(this.pos); //strategy pattern via enum
    }

    /**
     * Method to add a key to the player's collection
     * @param k key to add
     */
    public void addKey(Key k){
        keys.add(k);
    }

    /**
     * Method to decrement the treasure count when a treasure is collected
     */
    public void collectTreasure(){
        if(treasuresLeft > 0){
            treasuresLeft--;
        }
    }

    /**
     * Set the current position of the player
     * @param pos new position
     */
    public void setPos(Position pos){
        this.pos = pos;
    }

    /**
     * Set the current direction the player is facing
     * @param d new direction
     */
    public void setDirection(Direction d){
        this.direction = d;
    }

    /**
     * Set the number of treasures left to collect in the maze
     * @param i number of treasures left
     */
    public void setTreasuresLeft(int i){
        assert i >= 0 : "Treasures left cannot be negative";
        this.treasuresLeft = i;
    }

    /**
     * Get the current position of the player
     * @return current position
     */
    public Position getPos(){
        return pos;
    }

    /**
     * Get the current direction the player is facing
     * @return current direction
     */
    public Direction getDirection(){
        return direction;
    }

    /**
     * Get the list of keys the player has collected
     * @return list of keys
     */
    public List<Key> getKeys(){
        return Collections.unmodifiableList(keys);
    }

    /**
     * Check if player has collected on treasures in current level of maze
     * Using the treasuresLeft counter
     * @return true if all treasures collected, false otherwise
     */
    public boolean allTreasuresCollected(){
        assert treasuresLeft >=0 : "Treasures left cannot be negative";
        return treasuresLeft == 0;
    }

}
