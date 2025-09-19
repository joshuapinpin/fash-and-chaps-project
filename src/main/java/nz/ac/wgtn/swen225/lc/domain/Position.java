package nz.ac.wgtn.swen225.lc.domain;

/**
 * Position class representing coordinates in the game grid
 * Used to track entity and player positions
 */
public class Position {
    private int x;
    private int y;

    /**
     * Constructor for position with specified coordinates
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for x-coordinate
     * @return x-coordinate
     */
    public int getX(){return this.x;}

    /**
     * Getter for y-coordinate
     * @return y-coordinate
     */
    public int getY(){return this.y;}

    /**
     * Set the x-coordinate
     * @param i new x-coordinate
     */
    public void setX(int i){
        this.x = i;
    }

    /**
     * Set the y-coordinate
     * @param i new y-coordinate
     */
    public void setY(int i){
        this.y = i;
    }

}
