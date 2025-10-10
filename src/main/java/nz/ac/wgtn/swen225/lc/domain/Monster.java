package nz.ac.wgtn.swen225.lc.domain;

/**
 * Represents a monster in the game
 * Monsters can move horizontally in the maze and interact with the player
 * If a monster collides with the player, the player dies
 * @author Hayley Far (300659131)
 */
public class Monster {

    private Position position; // Starting position of the monster
    private Direction direction = Direction.LEFT; // Initial direction of the monster

    /**
     * Constructor for monster with specified position
     * @param pos starting position of the monster
     */
    Monster(Position pos){
        if(pos == null){
            throw new IllegalArgumentException("Position cannot be null");
        }
        this.position = pos;
    }

    /**
     * Static factory method to create a new Monster instance
     * @return new Monster instance
     */
    public static Monster of(Position pos){
        return new Monster(pos);
    }

    /**
     * Update the direction of the monster (left or right)
     */
    public void updateDirection(){
        if(this.direction == Direction.RIGHT){
            this.direction = Direction.LEFT;
        } else {
            this.direction = Direction.RIGHT;
        }
    }

    /**
     * Move the monster in its current direction
     * Updates the monster's position based on its direction
     */
    public void move(){
        this.position = direction.apply(this.position);
    }

    /**
     * Getter for monster position
     * @return position of the monster
     */
    public Position getPos(){
        return this.position;
    }

    /**
     * Getter for monster direction
     * @return direction of the monster
     */
    public Direction getDirection(){
        return this.direction;
    }
}
