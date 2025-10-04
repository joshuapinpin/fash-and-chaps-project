package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.GameObserver;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.entities.Door;
import nz.ac.wgtn.swen225.lc.domain.entities.Key;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;
import nz.ac.wgtn.swen225.lc.renderer.imgs.Drawable;
import nz.ac.wgtn.swen225.lc.renderer.sounds.LoadingSounds;

import javax.swing.*;


/**
 * Controls how panels and tiles within the game
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
    public Renderer(Tile[][] currentTiles, Player player){
        drawable = new Drawable(currentTiles, player);
    }

    /**
     * Returns the panel
     * @return - returns the JPanel
     */
    public Drawable getPanel(){
        return drawable;
    }

    /**
     * Observes game events to play sound effects
     * @return - an observer
     */
    public static GameObserver playSounds(){
        return new GameObserver(){
            @Override
            public void onKeyCollected(Key key) {
                LoadingSounds.KeySound.playSoundEffect(-20.f);
            }

            @Override
            public void onTreasureCollected() {
                LoadingSounds.CoinSound.playSoundEffect(-20.f);
            }

            @Override
            public void onDoorOpened(Door door) {
                LoadingSounds.UnlockedSound.playSoundEffect(-10.f);
            }

            @Override
            public void onPlayerDrown(Player player) {
                LoadingSounds.PlayerDrownSound.playSoundEffect(-20.f);
            }
        };
    }

    /*
    * Testing main to see if drawing are displayed.
     */
    public static void main(String[] args) throws InterruptedException {
        /*Renderer renderer = new Renderer(null, null);
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Image Display");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(renderer.getPanel());
            frame.pack(); // Use pack() to respect preferred size
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });*/
        LoadingSounds.PlayerDrownSound.playSoundEffect(-20.0f);
        try {
            Thread.sleep(3000); // wait long enough for the sound to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LoadingSounds.KeySound.playBackgroundMusic(-30f);
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        //Thread.sleep(10000);
        //LoadingSounds.BackgroundSound.stopBackgroundMusic();
    }


}
