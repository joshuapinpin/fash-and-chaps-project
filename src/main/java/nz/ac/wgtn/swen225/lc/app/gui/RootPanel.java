package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.util.MyImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RootPanel extends JPanel {

    private GameController controller;
    private BufferedImage bgImg;

    public RootPanel(GameController controller) {
        this.controller = controller;
        setLayout(new FlowLayout(FlowLayout.CENTER, GameWindow.SQUARE_SIZE,0));
        setBackground(Color.BLACK);
//        setBorder(BorderFactory.createLineBorder(Color.RED, 10));
        bgImg = new MyImage("water").getImage();
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
