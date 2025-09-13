package nz.ac.wgtn.swen225.lc.app.gui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    // Game Panel Fields
//    private static final int PANEL_WIDTH = (GameWindow.WINDOW_WIDTH / 4) * 3;
//    private static final int PANEL_HEIGHT = (GameWindow.WINDOW_HEIGHT / 4) * 3;;
//    private static final int PANEL_X = (GameWindow.WINDOW_WIDTH - PANEL_WIDTH) / 2;
//    private static final int PANEL_Y = (GameWindow.WINDOW_HEIGHT - PANEL_HEIGHT) / 2;

    private static final int PANEL_WIDTH = GameWindow.WINDOW_WIDTH / 2;
    private static final int PANEL_HEIGHT = GameWindow.WINDOW_HEIGHT / 2;
    private static final int PANEL_X = 0;
    private static final int PANEL_Y = 0;

    private GameWindow window;

    public GamePanel(GameWindow window) {
        this.window = window;
        setBackground(Color.blue);
        setBounds(PANEL_X,PANEL_Y,PANEL_WIDTH,PANEL_HEIGHT);
    }
}
