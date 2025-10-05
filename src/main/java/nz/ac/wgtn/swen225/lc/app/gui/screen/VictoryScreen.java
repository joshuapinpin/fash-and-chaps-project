package nz.ac.wgtn.swen225.lc.app.gui.screen;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.GamePanel;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class VictoryScreen extends JPanel implements GamePanel {
    private BufferedImage bgImg;

    public VictoryScreen(AppController controller) {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        JLabel title = new JLabel("VICTORY!!!!!", SwingConstants.CENTER);
        title.setFont(MyFont.PIXEL.getFont(50));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.CENTER);

        // Buttons if relevant
        // Maybe give options for level 1 or 2.
//        JButton startButton = new JButton("Start Game");
//        startButton.addActionListener(e -> controller.showScreen(PlayState.name()));
//        add(startButton, BorderLayout.SOUTH);

        bgImg = LoadingImg.WinScreen.loadImage();
    }

    @Override
    public void updatePanel() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
    }
}
