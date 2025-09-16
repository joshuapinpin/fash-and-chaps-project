package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.util.Renderer;

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
        setBackground(Color.green);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        JLabel label = new JLabel("Inventory");
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setForeground(Color.black);
        add(label);
    }

    BufferedImage image = Renderer.image;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int square = 100;
        for(int col = 0; col < 2; col++){
            for(int row = 0; row < 4; row++){

            }
        }
    }
}
