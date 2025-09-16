package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InventoryPanel extends JPanel {

    public static final int PANEL_WIDTH = (GameWindow.WINDOW_WIDTH / 4) ;
    public static final int PANEL_HEIGHT = (GameWindow.WINDOW_HEIGHT / 4) * 3;

    private GameController controller;
    InventoryPanel(GameController controller){
        this.controller = controller;
        setBackground(Color.yellow);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        JLabel label = new JLabel("Inventory");
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setForeground(Color.black);
        loadImage();
        add(label);
    }

    BufferedImage image;
    private void loadImage(){
        try {
            image = ImageIO.read(new File(
                    "src/main/java/nz/ac/wgtn/swen225/lc/renderer/imgs/sand.png"
            ));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int squareSize = PANEL_WIDTH / 2;
        int square = 100;
//        for(int row = PANEL_HEIGHT; row > 0; row -= squareSize){
//            for(int col = 0; col < PANEL_WIDTH; col += squareSize){
//                g.drawImage(image, col, row - squareSize, square, square, this);
//            }
//        }
        g.drawImage(image, 0, 40, square, square, this);
    }
}
