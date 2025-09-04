package nz.ac.wgtn.swen225.lc.app;

import javax.swing.SwingUtilities;

/**
 * Entry point for SWEN225's ChapsChallenge application.
 * Starts the application
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
