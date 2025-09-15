package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;

import javax.swing.JPanel;
import java.awt.*;

/**
 * Displays time left (countdown), current level, keys collected, and treasures remaining.
 * Offers buttons for pausing, saving, quitting, and resuming a game.
 * Should also offer a help button that shows game rules.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class StatusPanel extends JPanel {
    // TODO: Add fields and methods to display game status

    // Size fields
    public static final int PANEL_WIDTH = (GameWindow.WINDOW_HEIGHT / 8) * 3;
    public static final int PANEL_HEIGHT = (GameWindow.WINDOW_HEIGHT / 4) * 3;;

    private GameWindow window;

    public StatusPanel(GameWindow window) {
        this.window = window;
        setBackground(Color.blue);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
    }
}
