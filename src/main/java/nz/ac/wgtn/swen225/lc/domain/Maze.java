package nz.ac.wgtn.swen225.lc.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the world/maze of the game
 * Keeping collection of tiles in game
 */
public class Maze {
    //Fields
    Map<Position,Tile> tileMap = new HashMap<>();
    List<Tile> allTiles = new ArrayList<>();
    Player player;
    int rows; int cols; //bounds of maze

    //Constructor
    Maze(int rows, int cols){throw new UnsupportedOperationException();}

    //For persistency?
    public void loadMaze(){
        throw new UnsupportedOperationException();
    }

    //Getter to get certain tile at position
    public Tile getTileAt(Position p){
        throw new UnsupportedOperationException();
    }

}
