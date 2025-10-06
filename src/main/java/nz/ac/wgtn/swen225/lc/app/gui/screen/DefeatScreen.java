package nz.ac.wgtn.swen225.lc.app.gui.screen;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DefeatScreen extends JPanel  {
    private BufferedImage bgImg;

    public DefeatScreen(AppController controller) {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Defeat :(((", SwingConstants.CENTER);
        title.setFont(MyFont.PIXEL.getFont(50));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.CENTER);

        JButton startButton = new JButton("Restart from Level");
        startButton.addActionListener(e -> controller.restartLevel());
        add(startButton, BorderLayout.SOUTH);

        bgImg = LoadingImg.LoseScreen.loadImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
    }
}
