package nz.ac.wgtn.swen225.lc.app.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel {
    // Game Panel Fields
    public static final int PANEL_WIDTH = (GameWindow.WINDOW_HEIGHT / 4) * 3;
    public static final int PANEL_HEIGHT = (GameWindow.WINDOW_HEIGHT / 4) * 3;;
    public static final int PANEL_X = (GameWindow.WINDOW_WIDTH - PANEL_WIDTH) / 2;
    public static final int PANEL_Y = (GameWindow.WINDOW_HEIGHT - PANEL_HEIGHT) / 2;

    BufferedImage image;
    private GameWindow window;

    public GamePanel(GameWindow window) {
        this.window = window;
        setBackground(Color.blue);
        setBounds(PANEL_X, PANEL_Y, PANEL_WIDTH, PANEL_HEIGHT);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
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
        int num = 15;
        int size = PANEL_HEIGHT/num;
        for(int row = 0; row < num; row++){
            for(int col = 0; col < num; col++){
                g.drawImage(image, row * size,col * size,
                        size, size, this);
            }
        }
    }
}
