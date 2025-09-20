package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.util.MyImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TitlePanel extends JPanel {
    public static int PANEL_WIDTH = GameWindow.WINDOW_WIDTH;
    public static int PANEL_HEIGHT = GameWindow.HEADER_HEIGHT;

    private BufferedImage bgImg;

    private GameController controller;

    public TitlePanel(GameController controller){
        this.controller = controller;
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.yellow);
        bgImg = new MyImage("wall").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int squareSize = 60;
        int x = 0, y = 0;
        while(x < GameWindow.WINDOW_WIDTH){
            while(y < GameWindow.WINDOW_HEIGHT){
                g.drawImage(bgImg, x, y, squareSize, squareSize, this);
                y += squareSize;
            }
            y = 0;
            x += squareSize;
        }
    }
}
