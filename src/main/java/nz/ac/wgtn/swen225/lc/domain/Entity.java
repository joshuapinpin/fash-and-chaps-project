package nz.ac.wgtn.swen225.lc.domain;

/**
 * Represents an interactable entity in the maze
 * e.g. Key, Door, ExitLock and Treasure
 * Each entity is to define what happens when a player interacts with it
 */
public interface Entity {

    //Called when a player interacts with this entity
    void onInteract(Player player);
}
