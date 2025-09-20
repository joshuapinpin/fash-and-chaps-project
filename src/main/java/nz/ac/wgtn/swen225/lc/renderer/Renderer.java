package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.renderer.imgs.Drawable;
import nz.ac.wgtn.swen225.lc.renderer.imgs.TileDummy;

import javax.swing.*;
import java.util.List;

/**
 * Helps to control what to call etc
 */
public class Renderer {
    //call Josh's methods
    public static int X_PANEL_WIDTH;
    public static int Y_PANEL_HEIGHT;
    static Drawable drawable;

    public static Renderer of() {
        return new Renderer();
    }

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
    public Renderer(){
        drawable = new Drawable();
    }

    /**
     * Returns the panel
     * @return - returns the JPanel
     */
    public Drawable getPanel(){
        return drawable;
    }


    public static void main(String[] args) {
        Renderer renderer = new Renderer();
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
