package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.util.MyImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MazePanel extends JPanel {
    // Game Panel Fields
    public static final int PANEL_WIDTH = (GameWindow.WINDOW_HEIGHT / 4) * 3;
    public static final int PANEL_HEIGHT = (GameWindow.WINDOW_HEIGHT / 4) * 3;

    private GameController controller;
    private BufferedImage bgImg;

    public MazePanel(GameController controller) {
        this.controller = controller;

        setBackground(Color.orange);
//        setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        bgImg = new MyImage("sand").getImage();
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 0, y = 0;
        while(x < GameWindow.WINDOW_WIDTH){
            while(y < GameWindow.WINDOW_HEIGHT){
                g.drawImage(bgImg, x, y, 60, 60, this);
                y += 60;
            }
            y = 0;
            x += 60;
        }

    }
}
