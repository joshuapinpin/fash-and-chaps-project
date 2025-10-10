package nz.ac.wgtn.swen225.lc.app.gui.layout;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;

import javax.swing.*;
import java.awt.*;

/**
 * A panel that displays a "PAUSED" message when the game is paused.
 * It overlays the current game view with a semi-transparent background.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class PausePanel extends JPanel {
    private AppController c;

    /**
     * Constructor for PausePanel.
     * @param c AppController
     */
    public PausePanel(AppController c){
        this.c = c;
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(new Color(0, 0, 0, 150)); // Semi-transparent background
        JLabel label = new JLabel("PAUSED");
        label.setFont(MyFont.PIXEL.getFont(AppWindow.FONT_SIZE_H1));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        add(label);
    }
}
