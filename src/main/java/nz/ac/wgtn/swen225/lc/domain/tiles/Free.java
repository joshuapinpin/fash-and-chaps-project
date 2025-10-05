package nz.ac.wgtn.swen225.lc.domain.tiles;

import nz.ac.wgtn.swen225.lc.domain.GameObserver;
import nz.ac.wgtn.swen225.lc.domain.entities.Entity;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;

import java.util.Optional;
import java.util.function.Consumer;

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
        if(pos == null){
            throw new IllegalArgumentException("Position cannot be null");
        }
        return new Free(pos);
    }

    /**
     * Method to handle player entering the free tile
     * If there is a collectable entity, the player interacts with it
     * Checks to see if the entity should be removed after interaction
     * @param p player entering the free tile
     * @return Consumer to notify observers of player movement and entity interaction
     */
    @Override
    public Consumer<GameObserver> onEnter(Player p){
        if(p == null){
            throw new IllegalArgumentException("Player cannot be null");
        }

        final Consumer<GameObserver> entityEvent;

        //using if else because consumer needs to be final or effectively final
        if(collectable.isPresent()){
            entityEvent = collectable.get().onInteract(p);
            if(collectable.get().removeEntity()) {
                collectable = Optional.empty();// Remove the entity after collection
                assert !collectable.isPresent() : "Collectable should have been removed";
            }
        }
        else {
            entityEvent = observer -> {};
        }

        //returning an observer which first notifies of player move, then of entity interaction
        return observer -> {
            observer.onPlayerMove(p.getPos());
            entityEvent.accept(observer);
        };
    }

    /**
     * Free tile is accessible unless it contains an impassable entity (ExitLock or closed Door)
     * Used to check if the player can move onto the tile beforehand
     * @return true if the tile is accessible, false otherwise
     */
    @Override
    public boolean isAccessible(Player p){
        if(p == null){
            throw new IllegalArgumentException("Player cannot be null");
        }
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

        assert this.collectable.isPresent() : "Collectable should be present";
        assert this.collectable.get() == collectable : "Collectable should be the one that was set";
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

    /**
     * Accept method for visitor pattern
     * @param visitor TileVisitor instance
     * @return result from visitor's visitFree method
     */
    @Override
    public <T> T accept(TileVisitor<T> visitor) {
        return visitor.visitFree(this);
    }
}
