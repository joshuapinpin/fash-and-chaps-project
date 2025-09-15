package nz.ac.wgtn.swen225.lc.app.gui;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    // Size fields
    public static final int PANEL_WIDTH = (GameWindow.WINDOW_HEIGHT / 4) * 2;
    public static final int PANEL_HEIGHT = (GameWindow.WINDOW_HEIGHT / 4) * 3;;
    public static final int PANEL_X = 0;
    public static final int PANEL_Y = (GameWindow.WINDOW_HEIGHT - PANEL_HEIGHT) / 2;

    private GameWindow window;
    MenuPanel(GameWindow window){
        this.window = window;
        setBackground(Color.green);
        setBounds(PANEL_X+20,PANEL_Y,PANEL_WIDTH-20,PANEL_HEIGHT);
    }
}
