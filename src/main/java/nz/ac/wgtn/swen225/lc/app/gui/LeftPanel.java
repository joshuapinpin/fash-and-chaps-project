package nz.ac.wgtn.swen225.lc.app.gui;


import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;

import javax.swing.*;
import java.awt.*;

/**
 * Displays time left (countdown), current level, keys collected, and treasures remaining.
 * Offers buttons for pausing, saving, quitting, and resuming a game.
 * Should also offer a help button that shows game rules.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class LeftPanel extends JPanel {
    // Size fields
    public static final int PANEL_WIDTH = (GameWindow.WINDOW_WIDTH / 4);
    public static final int PANEL_HEIGHT = (GameWindow.WINDOW_HEIGHT / 4) * 3;;

    private JLabel levelLabel;
    private JLabel timerLabel;
    private JButton helpButton;

    private GameController controller;

    public LeftPanel(GameController controller) {
        this.controller = controller;
//        setBackground(Color.blue);
        setLayout(new GridLayout(2, 1));

//        setOpaque(false);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        JLabel levelLabel = new JLabel("Level");
        levelLabel.setFont(new MyFont("pixel-font", 40).getFont());
        levelLabel.setForeground(Color.white);
        add(levelLabel);

        JLabel timerLabel = new JLabel("Timer");
        timerLabel.setFont(new MyFont("pixel-font", 40).getFont());
        timerLabel.setForeground(Color.white);
        add(timerLabel);
    }
}
