package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.renderer.imgs.Drawable;

import javax.swing.*;

/**
 * Helps to control what to call etc
 */
public class Renderer {
    //call Josh's methods
    static double X_PANEL_WIDTH = 1000;
    static double Y_PANEL_HEIGHT = 900;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Image Display");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Drawable drawable = new Drawable();
            System.out.println("Tile loaded successfully");
            frame.add(drawable);
            frame.pack(); // Use pack() to respect preferred size
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
