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
    public static int X_PANEL_WIDTH = 540;
    public static int Y_PANEL_HEIGHT = 540;




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
