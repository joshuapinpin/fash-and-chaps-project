package nz.ac.wgtn.swen225.lc.app.gui.game;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    public static final int PANEL_X = 10;
    public static final int PANEL_Y = 10;
    public static final int PANEL_WIDTH = AppWindow.MAZE_SIZE - (PANEL_X*2);
    public static final int PANEL_HEIGHT = (AppWindow.MAZE_SIZE - AppWindow.SQUARE_SIZE) / 2 - (PANEL_Y*2);

    public InfoPanel(AppController controller) {
        setPreferredSize(new Dimension(AppWindow.MAZE_SIZE, AppWindow.MAZE_SIZE));
        setOpaque(true);
        setBackground(new Color(0, 0, 0, 150)); // Semi-transparent background

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setAlignmentY(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue()); // Push content to center vertically
        addText("Welcome to Fash and Chaps!");
        addText("Pick up keys to open doors.");
        addText("Collect coins to open chest.");
        addText("Avoid crabs and puddles.");
        addText("Reach exit to complete level.");
        add(Box.createVerticalGlue()); // Push content up

        setVisible(false);
        setBounds(PANEL_X, PANEL_Y, PANEL_WIDTH, PANEL_HEIGHT);
    }

    private void addText(String text){
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(MyFont.PIXEL.getFont(20));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 5))); // Spacing between labels
        add(label);
    }
}
