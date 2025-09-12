package nz.ac.wgtn.swen225.lc.domain;

/**
 * Enum for the Player movement directions in the game
 * Having UP, DOWN, LEFT and RIGHT movements
 *
 * dx and dy fields represent how the Direction changes a Position
 */
public enum Direction {
    UP(0,-1),
    DOWN(0,1),
    LEFT(-1,0),
    RIGHT(1,0);

    private final int dx;
    private final int dy;

    //Constructor
    Direction(int dx, int dy){throw new UnsupportedOperationException();}

    //Getters, change in x and y
    public int getDx(){throw new UnsupportedOperationException();}
    public int getDy(){throw new UnsupportedOperationException();}
}
