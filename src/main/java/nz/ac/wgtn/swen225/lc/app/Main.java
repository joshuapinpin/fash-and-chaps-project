package nz.ac.wgtn.swen225.lc.app;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;

import javax.swing.*;

/**
 * Entry point for SWEN225's ChapsChallenge application.
 * Starts the application
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class Main {
    public static void main(String[] args) {
        // Set the look and feel to the system default (optional)
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (Exception e) {e.printStackTrace();}

        // Start the UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(AppController::of);
    }
}
