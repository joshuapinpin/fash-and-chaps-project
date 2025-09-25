package nz.ac.wgtn.swen225.lc.domain;
import nz.ac.wgtn.swen225.lc.domain.entities.*;

/**
 * Observer interface for game events
 * Implemented by classes that need to respond to game events
 * For app and renderer to observe
 * @author Hayley Far
 */
public interface GameObserver {
    void onPlayerMove(Position newPosition);

    void onKeyCollected(Player player, Key key);
    void onTreasureCollected(Player player, int remainingTreasures);
    void onInfoMessage(String message);
    void onLevelComplete(Player player); //exit tile

    //for level two
    void onPlayerDrown(Player player); //water
    void onPlayerDie(Player player); //crab/monster

    //need to have list/runnable for observers in player class
    //and an addObserver(GameObserver observer) method in Player class
}
