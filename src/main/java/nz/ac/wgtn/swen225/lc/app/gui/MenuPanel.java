package nz.ac.wgtn.swen225.lc.app.gui;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    // Size fields
    public static final int PANEL_WIDTH = GameWindow.WINDOW_WIDTH;
    public static final int PANEL_HEIGHT = GameWindow.WINDOW_HEIGHT / 8;

    private GameWindow window;
    MenuPanel(GameWindow window){
        this.window = window;
        setBackground(Color.green);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
    }
}
