package nz.ac.wgtn.swen225.lc.app.gui;

import javax.swing.*;
import java.awt.*;

public class InventoryPanel extends JPanel {

    public static final int PANEL_WIDTH = (GameWindow.WINDOW_WIDTH / 4) ;
    public static final int PANEL_HEIGHT = (GameWindow.WINDOW_HEIGHT / 4) * 3;

    private GameWindow window;
    InventoryPanel(GameWindow window){
        this.window = window;
        setBackground(Color.yellow);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
    }
}
