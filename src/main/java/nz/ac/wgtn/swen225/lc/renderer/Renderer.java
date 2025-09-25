package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;
import nz.ac.wgtn.swen225.lc.renderer.imgs.Drawable;

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

    /*
    * Testing main to see if drawing are displayed.
     */
    public static void main(String[] args) {
        Renderer renderer = new Renderer(null, null);
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Image Display");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(renderer.getPanel());
            frame.pack(); // Use pack() to respect preferred size
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
