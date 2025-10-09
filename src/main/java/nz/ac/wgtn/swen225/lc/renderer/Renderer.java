package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.GameObserver;
import nz.ac.wgtn.swen225.lc.domain.Monster;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.entities.Door;
import nz.ac.wgtn.swen225.lc.domain.entities.Key;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;
import nz.ac.wgtn.swen225.lc.renderer.imgs.Drawable;
import nz.ac.wgtn.swen225.lc.renderer.sounds.LoadingSounds;
import java.util.List;

/**
 * Controls how panels and tiles are drawn within the game
 * Controls when sounds are played
 * @author Emily Ung (300663254)
 */
public class Renderer {
    public static int X_PANEL_WIDTH;
    public static int Y_PANEL_HEIGHT;
    Drawable drawable; //current JPanel to be drawn

    /**
     * Gets the dimensions of the JPanel
     * @param width - width of the panel
     * @param height - height of the panel
     */
    public void setDimensions(int width, int height) {
        X_PANEL_WIDTH = width;
        Y_PANEL_HEIGHT = height;
    }

    /**
     * Constructor for Renderer that sets a Drawable
     */
    public Renderer(Tile[][] currentTiles, Player player, List<Monster> monsters){
        drawable = new Drawable(currentTiles, player, monsters);
    }

    /**
     * Returns the panel
     * @return - returns the JPanel
     */
    public Drawable getPanel(){ return drawable;}

    /**
     * Observes game events to play sound effects
     * @return - an observer
     */
    public static GameObserver playSounds(){
        return new GameObserver(){
            /**
             * Plays key collection sound
             * @param key - key object
             */
            @Override
            public void onKeyCollected(Key key) { LoadingSounds.KeySound.playSoundEffect(-20.f); }

            /**
             * Plays treasure collection sound
             */
            @Override
            public void onTreasureCollected() { LoadingSounds.CoinSound.playSoundEffect(-20.f); }

            /**
             * Plays door unlocking sound
             * @param door - door object
             */
            @Override
            public void onDoorOpened(Door door) { LoadingSounds.UnlockedSound.playSoundEffect(-10.f); }

            /**
             * Plays water sounds when player dies in water
             * @param player - current player
             */
            @Override
            public void onPlayerDrown(Player player) { LoadingSounds.PlayerDrownSound.playSoundEffect(-20.f); }
        };
    }
}
