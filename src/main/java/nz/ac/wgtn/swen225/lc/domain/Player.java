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
    List<Treasure> inventory = new ArrayList<>();
    Position pos;
    Direction direction; //enum type
    int chipsLeft;


    public void move(Direction d){
        throw new UnsupportedOperationException();
    }
    //to add treasure and decrement chipsLeft int
    public void addTreasure(Treasure t){
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
