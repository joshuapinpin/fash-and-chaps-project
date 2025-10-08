package nz.ac.wgtn.swen225.lc.domain;

/**
 * Position class representing coordinates in the game grid
 * Used to track entity and player positions
 * @author Hayley Far (300659131)
 */
public class Position {
    private int x; //x-coordinate
    private int y; //y-coordinate

    /**
     * Constructor for position with specified coordinates
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Position(int x, int y){
        if(x < 0 || y < 0){
            throw new IndexOutOfBoundsException("Coordinates cannot be negative");
        }
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
        if(i < 0){
            throw new IllegalArgumentException("Coordinate cannot be negative");
        }
        this.x = i;
    }

    /**
     * Set the y-coordinate
     * @param i new y-coordinate
     */
    public void setY(int i){
        if(i < 0){
            throw new IllegalArgumentException("Coordinate cannot be negative");
        }
        this.y = i;
    }

    /**
     * Override equals method for position comparison
     * @param obj object to compare with
     * @return true if positions are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }

    /**
     * Override toString method for position
     * @return string representation of position
     */
    @Override
    public String toString() {
        return "x: "+this.x+" y: "+this.y;
    }
}
