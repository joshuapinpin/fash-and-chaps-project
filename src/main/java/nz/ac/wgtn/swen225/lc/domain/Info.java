package nz.ac.wgtn.swen225.lc.domain;

/**
 * Represents an info help tile
 * Player stepping on this tile displays a message to the player
 */
public class Info extends Tile{
    //Field
    String message;

    //Constructor
    Info(String message, Position pos){
        super(pos);
        this.message = message;
    }

    //Help text to display when player enters this tile
    @Override
    public void onEnter(Player p){
        throw new UnsupportedOperationException();
    }

    //Getter
    public String getMessage(){
        throw new UnsupportedOperationException();
    }
}
