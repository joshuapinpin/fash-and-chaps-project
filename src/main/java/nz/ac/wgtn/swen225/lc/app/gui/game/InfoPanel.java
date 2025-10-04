package nz.ac.wgtn.swen225.lc.app.gui.game;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    public InfoPanel(AppController controller) {
        setPreferredSize(new Dimension(AppWindow.MAZE_SIZE, AppWindow.MAZE_SIZE));
        setOpaque(true);
        setBackground(new Color(0, 0, 0, 150)); // Semi-transparent background
        
//        String desc = "<html><div style='text-align: center;'>"
//                + "Welcome to Fash and Chaps!<br><br>"
//                + "Pick up keys to open doors.<br>"
//                + "Collect coins to open chest.<br>"
//                + "Avoid crabs and puddles.<br>"
//                + "Reach the exit to complete the level."
//                + "</div></html>";
        String desc = "INFO\nYAY";
        JLabel label = new JLabel(desc);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(MyFont.PIXEL.getFont(30));
        label.setForeground(Color.WHITE);

        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);

        setVisible(false);
        setBounds(AppWindow.MAZE_SIZE/8, AppWindow.MAZE_SIZE/8,
                (AppWindow.MAZE_SIZE/4) * 3, (AppWindow.MAZE_SIZE/4) * 3);
    }
}
