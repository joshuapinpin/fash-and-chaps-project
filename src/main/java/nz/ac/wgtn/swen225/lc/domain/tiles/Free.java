package nz.ac.wgtn.swen225.lc.domain.tiles;

import nz.ac.wgtn.swen225.lc.domain.entities.Entity;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;

import java.util.Optional;

/**
 * Free class representing a free tile in the game
 * Free tiles can optionally contain a collectable entity (Key, Door, ExitLock or Treasure)
 * Inherits from Tile class
 * @author Hayley Far
 */
public class Free extends Tile {
    private Optional<Entity> collectable = Optional.empty(); // Optional collectable entity on the tile

    /**
     * Constructor for free tile with specified position
     * @param pos position of the free tile
     */
    Free(Position pos){
        super(pos);
    }

    /**
     * Static factory method to create a free tile with specified position
     * @param pos position of the free tile
     * @return new Free instance
     */
    public static Free of(Position pos){
        return new Free(pos);
    }

    /**
     * Method to handle player entering the free tile
     * If there is a collectable entity, the player interacts with it
     * Checks to see if the entity should be removed after interaction
     * @param p player entering the free tile
     */
    @Override
    public void onEnter(Player p){
        collectable.ifPresent(entity-> {
            entity.onInteract(p);
            if(entity.removeEntity()) {
                collectable = Optional.empty(); // Remove the entity after collection
            }
        });
    }



    /**
     * Free tile is accessible unless it contains an impassable entity (ExitLock or closed Door)
     * Used to check if the player can move onto the tile beforehand
     * @return true if the tile is accessible, false otherwise
     */
    @Override
    public boolean isAccessible(Player p){
        if(collectable.isPresent()){
            return collectable.get().canInteract(p);
        }
        return true;
    }

    /**
     * Setter for the collectable entity on the tile
     * @param collectable entity to be placed on the tile (Key, Door, ExitLock or Treasure)
     */
    public void setCollectable(Entity collectable){
        if(collectable==null){throw new IllegalArgumentException("Collectable cannot be null");}
        this.collectable = Optional.of(collectable);
    }

    /**
     * Getter for the collectable entity on the tile
     * @return Optional containing the collectable entity if present, otherwise empty
     */
    public Optional<Entity> getCollectable(){
        return collectable;
    }

    /**
     * Override equals method to compare free tiles based on position and collectable entity
     * @param obj object to compare with
     * @return true if free tiles are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        Free free = (Free) obj;
        return this.getPos().equals(free.getPos()) && this.collectable.equals(free.collectable);
    }
}
