package nz.ac.wgtn.swen225.lc.domain;

/**
 * Observer interface for game events
 * Implemented by classes that need to respond to game events
 * For app and renderer to observe
 * App: to use for exiting level, updating info panel
 * Renderer: to update sounds based on game state changes
 * @author Hayley Far (300659131)
 */
public interface GameObserver {
    //For App
    /**
     * When the player goes onto the info tile
     * To update the info panel in the app
     */
    default void onInfoMessage(){}

    /**
     * When the player completes the level
     * To exit the level in the app
     */
    default void onLevelComplete(){} //exit tile

    //For Renderer
    /**
     * When the player moves
     * To update the player's position in the renderer
     */
    default void onPlayerMove(Position newPosition){}

    /**
     * When the player collects a key
     * To play key collection sound in the renderer
     */
    default void onKeyCollected(Key key){}

    /**
     * When the player collects a treasure
     * To play treasure collection sound in the renderer
     */
    default void onTreasureCollected(){}

    /**
     * When the player opens a door
     * To play door opening sound in the renderer
     */
    default void onDoorOpened(Door door){}

    //For Level 2
    /**
     * When the player drowns
     * To play drowning sound in the renderer and defeat screen in the app
     */
    default void onPlayerDrown(Player player){} //water
    /**
     * When the player is killed by a crab or monster
     * To play death sound in the renderer and defeat screen in the app
     */
    default void onPlayerDie(Player player){} //crab/monster
}
