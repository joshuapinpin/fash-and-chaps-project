package nz.ac.wgtn.swen225.lc.domain;

/**
 * Enum for the Player movement directions in the game
 * Having UP, DOWN, LEFT and RIGHT movements
 * dx and dy fields represent how the Direction changes a Position
 */
public enum Direction {
    UP(0,-1), //move player up by decreasing y coordinate
    DOWN(0,1), //move player down by increasing y coordinate
    LEFT(-1,0), //move player left by decreasing x coordinate
    RIGHT(1,0); //move player right by increasing x coordinate

    private final int dx; //change in x
    private final int dy; //change in y

    /**
     * Constructor for direction with specified dx and dy
     * @param dx change in x
     * @param dy change in y
     */
    Direction(int dx, int dy){throw new UnsupportedOperationException();}

    /**
     * Apply this direction to a given position
     * Returns a new Position with updated coordinates
     * @param pos original position of player
     * @return new position after applying direction
     */
    public Position apply(Position pos){
        return new Position(pos.getX()+dx, pos.getY()+dy);
    }

    /**
     * Getter for change in x
     * @return change in x
     */
    public int getDx(){throw new UnsupportedOperationException();}

    /**
     * Getter for change in y
     * @return change in y
     */
    public int getDy(){throw new UnsupportedOperationException();}
}
