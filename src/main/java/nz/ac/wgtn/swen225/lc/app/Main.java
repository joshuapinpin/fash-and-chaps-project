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
        // Set the look and feel to the system default (optional)
//        try {
//            javax.swing.UIManager.setLookAndFeel(
//                    javax.swing.UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception e) {
//            // Ignore and use default
//        }

        // Start the UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            GameController controller = new GameController();
            GameWindow window = new GameWindow(controller);
            window.setVisible(true);
        });
    }
}
