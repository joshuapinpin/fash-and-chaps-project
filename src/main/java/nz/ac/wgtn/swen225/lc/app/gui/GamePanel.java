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
    public static final int PANEL_HEIGHT = (GameWindow.WINDOW_HEIGHT / 4) * 3;

    BufferedImage image;
    private GameWindow window;

    public GamePanel(GameWindow window) {
        this.window = window;
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setMaximumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        loadImage();
    }

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
        int num = 9;
        int size = PANEL_HEIGHT/num;
        for(int row = 0; row < num; row++){
            for(int col = 0; col < num; col++){
                g.drawImage(image, row * size,col * size,
                        size, size, this);
            }
        }
    }
}
