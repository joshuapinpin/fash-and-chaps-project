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
 * Player starts in the center of the maze, based on maze size from rows and cols
 * @author Hayley Far
 */
public class Player{
    private static final Player INSTANCE = new Player(); //singleton instance

    private List<Key> keys = new ArrayList<>(); //collection of keys the player has
    private Position pos; //current position of the player
    private Direction direction; //current direction the player is facing, enum Direction
    private int totalTreasures; //total number of treasures in the maze level
    private int treasuresCollected = 0; //number of treasures collected by player

    /**
     * Constructor for player with specified starting position
     * Made private for singleton pattern
     */
    private Player(){
        this.pos = new Position(0,0); //default starting position, to be set when maze is loaded
        this.direction = Direction.DOWN; //default starting direction
    }

    /**
     * Set the player's starting position to the center of the maze
     * Called when maze is loaded, based on maze dimensions
     * @param rows number of rows in maze
     * @param cols number of columns in maze
     */
    public void initialiseStartPos(int rows, int cols){
        if(rows <= 0 || cols <= 0){
            throw new IllegalArgumentException("Maze dimensions must be positive");
        }
        this.pos = new Position(cols/2, rows/2);
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
        if(direction == null){
            throw new IllegalArgumentException("Direction cannot be null");
        }
        this.direction = direction;
        this.pos = direction.apply(this.pos); //strategy pattern via enum
    }

    /**
     * Method to add a key to the player's collection
     * @param k key to add
     */
    public void addKey(Key k){
        if(k == null){
            throw new IllegalArgumentException("Key cannot be null");
        }
        keys.add(k);
        assert keys.contains(k) : "Key should have been added to collection";
    }

    /**
     * Method to decrement the treasure count when a treasure is collected
     */
    public void collectTreasure(){
        if(treasuresCollected < totalTreasures){
            treasuresCollected++;
        }
        assert treasuresCollected >= 0 : "Treasures left cannot be negative";
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
     * Set the total number of treasures to collect in the maze
     * @param i number of treasures in maze
     */
    public void setTotalTreasures(int i){
        assert i >= 0 : "Treasures in game cannot be negative";
        this.totalTreasures = i;
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
        if(treasuresCollected < 0){
            throw new IllegalStateException("Treasures left not initialized");
        }
        return treasuresCollected == totalTreasures;
    }

    /**
     * Get the number of treasures collected by the player
     * @return number of treasures collected
     */
    public int getTreasuresCollected() {return treasuresCollected;}

    /**
     * Get the total number of treasures in the maze level
     * @return total treasures
     */
    public int getTotalTreasures(){ return totalTreasures; }

    /**
     * Get the number of keys the player has collected
     * @return number of keys
     */
    public int getKeysLeft(){return keys.size();}

}
