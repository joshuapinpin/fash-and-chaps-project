package nz.ac.wgtn.swen225.lc.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player in the game
 * It will track the player's position, treasure chest (items collected), score
 * For updating state when interacting with tiles
 */
public class Player{
    //Fields
    List<Key> keys = new ArrayList<>();
    Position pos;
    Direction direction; //enum type
    int treasureLeft;

    //Constructor
    Player(Position pos){throw new UnsupportedOperationException();}


    //Moving Player across the maze in given direction
    public void move(Direction d){
        throw new UnsupportedOperationException();
    }
    //to add keys collection
    public void addKey(Key k){
        throw new UnsupportedOperationException();
    }

    //Just to decrement treasureLeft counter
    public void collectTreasure(){
        throw new UnsupportedOperationException();
    }

    //Setters
    public void setPos(Position pos){
        throw new UnsupportedOperationException();
    }
    public void setDirection(Direction d){
        throw new UnsupportedOperationException();
    }

    //Getters
    public Position getPos(){
        throw new UnsupportedOperationException();
    }
    public Direction getDirection(){
        throw new UnsupportedOperationException();
    }

}
