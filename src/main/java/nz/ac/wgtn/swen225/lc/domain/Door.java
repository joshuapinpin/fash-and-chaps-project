package nz.ac.wgtn.swen225.lc.domain;

public class Door implements Entity{
    //Fields
    String color;
    boolean isOpen = false;

    //Constructor
    Door(String color){throw new UnsupportedOperationException();}

    //Player to unlock door with correct key color (going through player inventory)
    public void onInteract(Player p){
        throw new UnsupportedOperationException();
    }

    //Getters
    public String getColor(){throw new UnsupportedOperationException();}

    public boolean isOpen() {
        throw new UnsupportedOperationException();
    }
}
