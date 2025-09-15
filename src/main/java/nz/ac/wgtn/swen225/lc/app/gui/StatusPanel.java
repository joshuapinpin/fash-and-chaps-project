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
    public static final int PANEL_WIDTH = (GameWindow.WINDOW_HEIGHT / 8) * 7;
    public static final int PANEL_HEIGHT = (GameWindow.WINDOW_HEIGHT / 8) * 7;;
    public static final int PANEL_X = GameWindow.WINDOW_HEIGHT - (GameWindow.WINDOW_HEIGHT - PANEL_HEIGHT) / 2;
    public static final int PANEL_Y = GameWindow.WINDOW_HEIGHT - (GameWindow.WINDOW_HEIGHT - PANEL_HEIGHT) / 2;

    private GameWindow window;

    public StatusPanel(GameWindow window) {
        this.window = window;
        setBackground(Color.red);
//        setBounds(PANEL_X,PANEL_Y,PANEL_WIDTH,PANEL_HEIGHT);
    }
}
