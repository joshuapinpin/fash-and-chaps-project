package nz.ac.wgtn.swen225.lc.app.gui.screen;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class StartScreen extends JPanel {
    private BufferedImage bgImg;

    public StartScreen(AppController controller) {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Fash and Chaps", SwingConstants.CENTER);
        title.setFont(MyFont.PIXEL.getFont(50));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> controller.startNewGame(1));
        add(startButton, BorderLayout.SOUTH);

        bgImg = LoadingImg.StartScreen.loadImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
    }

}
