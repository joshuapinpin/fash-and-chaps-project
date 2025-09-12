package nz.ac.wgtn.swen225.lc.domain;

/**
 * Represents a key entity that can be collected by player
 */
public class Key implements Entity {
    //Field
    String color;

    //Constructor
    Key(String color){throw new UnsupportedOperationException();}

    //Player to collect key when stepping on its tile
    public void onInteract(Player p){
        throw new UnsupportedOperationException();
    }

    //Getter
    public String getColor(){
        throw new UnsupportedOperationException();
    }
}
