package nz.ac.wgtn.swen225.lc.domain;

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
}
