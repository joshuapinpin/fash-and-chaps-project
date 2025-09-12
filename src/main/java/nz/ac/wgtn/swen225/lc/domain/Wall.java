package nz.ac.wgtn.swen225.lc.domain;

public class Wall extends Tile{

    //Constructor (does it have to be public?)
    Wall(Position pos) {
        super(pos);
    }

    //Only tile type you player cannot walk on so override
    @Override
    public boolean isAccessible(){return false;}

    //Does nothing, player cannot go on Wall
    @Override
    public void onEnter(Player p){
        throw new UnsupportedOperationException();
    }
}
