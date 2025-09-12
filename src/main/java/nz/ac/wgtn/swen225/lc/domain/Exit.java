package nz.ac.wgtn.swen225.lc.domain;

/**
 * Represents the exit tile of the level
 * Player stepping on this tile finishes level or complete game
 */
public class Exit extends Tile{

    //Potential field of GameController to trigger level complete?

    //Constructor
    Exit(Position pos){
        super(pos);
    }

    //Player to finish level and move onto next or win game
    @Override
    public void onEnter(Player p){
        throw new UnsupportedOperationException();
    }
}
