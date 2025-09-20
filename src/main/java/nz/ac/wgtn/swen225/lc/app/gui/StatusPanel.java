package nz.ac.wgtn.swen225.lc.app.gui;


import nz.ac.wgtn.swen225.lc.app.controller.GameController;

import javax.swing.*;
import java.awt.*;

/**
 * Displays time left (countdown), current level, keys collected, and treasures remaining.
 * Offers buttons for pausing, saving, quitting, and resuming a game.
 * Should also offer a help button that shows game rules.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class StatusPanel extends JPanel {
    // Size fields
    public static final int PANEL_WIDTH = (GameWindow.WINDOW_WIDTH / 4);
    public static final int PANEL_HEIGHT = (GameWindow.WINDOW_HEIGHT / 4) * 3;;

    private JLabel levelLabel;
    private JLabel timerLabel;
    private JButton helpButton;

    private GameController controller;

    public StatusPanel(GameController controller) {
        this.controller = controller;
//        setBackground(Color.blue);

        setOpaque(false);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
    }
}
