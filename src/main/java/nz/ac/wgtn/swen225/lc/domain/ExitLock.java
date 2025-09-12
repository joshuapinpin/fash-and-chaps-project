package nz.ac.wgtn.swen225.lc.domain;

public class ExitLock implements Entity{
    //Fields
    boolean isPassable = false;

    //Constructor
    ExitLock(){throw new UnsupportedOperationException();}

    //Player to pass through once all treasures are collected in their inventory
    public void onInteract(Player p){
        throw new UnsupportedOperationException();
    }

}
