package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {
    public static int PANEL_WIDTH = GameWindow.WINDOW_WIDTH;
    public static int PANEL_HEIGHT = GameWindow.WINDOW_HEIGHT / 8;

    private GameController controller;

    TitlePanel(GameController controller){
        this.controller = controller;
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
    }
}
