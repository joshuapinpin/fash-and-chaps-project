package nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;

import java.util.function.Consumer;

/**
 * Represents a monster in the game
 * Monsters can move horizontally in the maze and interact with the player
 * If a monster collides with the player, the player dies
 * @author Hayley Far
 */
public class Monster {

    private Position position; // Starting position of the monster
    private boolean isAlive = true; // Status of the monster
    private Direction direction = Direction.RIGHT;

    /**
     * Constructor for monster with specified position
     * @param pos starting position of the monster
     */
    Monster(Position pos){
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
     * Check for collision between the monster and the player
     * If positions are the same, the player dies
     * @param p player to check collision with
     * @return Consumer to notify observers of player death if collision occurs
     */
    public Consumer<GameObserver> checkCollisionWithPlayer(Player p){
        //check positions
        if(this.position.equals(p.getPos())){
            return observer -> observer.onPlayerDie(p);
        }
        return observer -> {};
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
