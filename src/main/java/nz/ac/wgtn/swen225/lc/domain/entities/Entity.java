package nz.ac.wgtn.swen225.lc.domain.entities;

import nz.ac.wgtn.swen225.lc.domain.GameObserver;
import nz.ac.wgtn.swen225.lc.domain.Player;

import java.util.function.Consumer;

/**
 * Represents an interactable entity in the maze
 * e.g. Key, Door, ExitLock and Treasure
 * Each entity is to define what happens when a player interacts with it
 * @author Hayley Far (300659141)
 */
public interface Entity {
    /**
     * Defines the interaction between the player and the entity
     * @param player the player interacting with the entity
     * @return a Consumer to notify observers of the interaction
     */
    Consumer<GameObserver> onInteract(Player player);

    /**
     * Defines whether the entity should be removed from the tile after interaction
     * e.g. Keys and Treasures are removed after collection, Doors and ExitLocks depend on their state
     * default is true, and overridden for Doors and ExitLocks
     * @return true if the entity should be removed, false otherwise
     */
    default boolean removeEntity(){return true;}

    /**
     * Check if the entity is accessible
     * Used to check if player can go onto the tile beforehand, checking the entity on it if any
     * default is true, and overridden for Doors and ExitLocks
     * @return true if accessible, false otherwise
     */
    default boolean canInteract(Player p){return true;}

    /**
     * Accept a visitor to perform operations based on the entity type
     * @param visitor the visitor to accept
     * @param <T> the return type of the visitor's operation
     * @return the result of the visitor's operation
     */
    <T> T accept(EntityVisitor<T> visitor);
}
