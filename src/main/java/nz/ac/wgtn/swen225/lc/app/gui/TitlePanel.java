package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TitlePanel extends JPanel implements GamePanel{
    public static int PANEL_WIDTH = AppWindow.WINDOW_WIDTH;
    public static int PANEL_HEIGHT = AppWindow.HEADER_HEIGHT;

    private BufferedImage bgImg;
    private GameController controller;

    public TitlePanel(GameController controller){
        this.controller = controller;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.yellow);
        bgImg = LoadingImg.Water.loadImage();
        setupTitle();
    }

    private void setupTitle(){
        JLabel title = new JLabel("Fash and Chaps");
        title.setFont(MyFont.PIXEL.getFont(90));
        title.setForeground(Color.white);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int squareSize = AppWindow.SQUARE_SIZE;
        int x = 0, y = 0;
        while(x < AppWindow.WINDOW_WIDTH){
            while(y < AppWindow.HEADER_HEIGHT){
                g.drawImage(bgImg, x, y, squareSize, squareSize, this);
                y += squareSize;
            }
            y = 0;
            x += squareSize;
        }
    }

    @Override
    public void updatePanel() {

    }
}
