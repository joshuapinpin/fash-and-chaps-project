package nz.ac.wgtn.swen225.lc.domain;
import nz.ac.wgtn.swen225.lc.domain.entities.*;

/**
 * Observer interface for game events
 * Implemented by classes that need to respond to game events
 * For app and renderer to observe
 * App: to use for exiting level, updating info panel
 * Renderer: to update visuals/sounds based on game state changes
 * @author Hayley Far
 */
public interface GameObserver {
    //For App
    default void onInfoMessage(String message){}
    default void onLevelComplete(){} //exit tile

    //For Renderer
    default void onPlayerMove(Position newPosition){}
    default void onKeyCollected(Key key){}
    default void onTreasureCollected(){}
    default void onDoorOpened(Door door){}

    //For Level 2
    default void onPlayerDrown(Player player){} //water
    default void onPlayerDie(Player player){} //crab/monster
}
