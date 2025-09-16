package nz.ac.wgtn.swen225.lc.app.gui;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {
    private GameWindow window;
    public static int PANEL_WIDTH = GameWindow.WINDOW_WIDTH;
    public static int PANEL_HEIGHT = GameWindow.WINDOW_HEIGHT / 8;
    TitlePanel(GameWindow window){
        this.window = window;
//        setBackground(Color.white);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
    }
}
