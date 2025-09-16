package nz.ac.wgtn.swen225.lc.domain.tiles;

import nz.ac.wgtn.swen225.lc.domain.entities.Entity;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;

import java.util.Optional;

/**
 * Free class representing a free tile in the game
 * Free tiles can optionally contain a collectable entity (Key, Door, ExitLock or Treasure)
 * Inherits from Tile class
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
     * Method to handle player entering the free tile
     * If there is a collectable entity, the player interacts with it
     * @param p player entering the free tile
     */
    @Override
    public void onEnter(Player p){
        collectable.ifPresent(entity-> {
            entity.onInteract(p);
            collectable = Optional.empty(); // Remove the entity after collection
        });
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
}
