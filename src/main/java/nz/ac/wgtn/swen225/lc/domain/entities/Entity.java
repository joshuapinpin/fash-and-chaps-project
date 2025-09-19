package nz.ac.wgtn.swen225.lc.domain.entities;

import nz.ac.wgtn.swen225.lc.domain.Player;

/**
 * Represents an interactable entity in the maze
 * e.g. Key, Door, ExitLock and Treasure
 * Each entity is to define what happens when a player interacts with it
 */
public interface Entity {
    /**
     * Defines the interaction between the player and the entity
     * @param player the player interacting with the entity
     */
    void onInteract(Player player);

    /**
     * Defines whether the entity should be removed from the tile after interaction
     * e.g. Keys and Treasures are removed after collection, Doors and ExitLocks depend on their state
     * @return true if the entity should be removed, false otherwise
     */
    default boolean removeEntity(){return true;}

    /**
     * Check if the entity is accessible
     * Used to check if player can go onto the tile beforehand, checking the entity on it if any
     * @return true if accessible, false otherwise
     */
    default boolean canInteract(Player p){return true;}
}
