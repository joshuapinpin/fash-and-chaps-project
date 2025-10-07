package nz.ac.wgtn.swen225.lc.app.gui.logic;

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

    JPanel helperPanel;

    public InfoPanel(AppController controller) {
        setOpaque(false);
        setLayout(null);

        helperPanel = new JPanel();
        helperPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        helperPanel.setOpaque(true);
        helperPanel.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent background
        helperPanel.setLayout(new BoxLayout(helperPanel, BoxLayout.Y_AXIS));
        helperPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        helperPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        helperPanel.add(Box.createVerticalGlue()); // Push content to center vertically
        addText("Welcome to Fash and Chaps!");
        addText("Pick up keys to open doors.");
        addText("Collect coins to open chest.");
        addText("Avoid crabs and puddles.");
        addText("Reach sand castle to win!");
        helperPanel.add(Box.createVerticalGlue()); // Push content up

        helperPanel.setVisible(true);
        helperPanel.setBounds(PANEL_X, PANEL_Y, PANEL_WIDTH, PANEL_HEIGHT);

        add(helperPanel);
    }

    private void addText(String text){
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(MyFont.PIXEL.getFont(20));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        helperPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Spacing between labels
        helperPanel.add(label);
    }

    public static String name(){return "InfoPanel";}
}
