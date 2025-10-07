package nz.ac.wgtn.swen225.lc.app.gui.screen;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.util.MyButton;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class StartScreen extends JPanel {
    private AppController c;
    private BufferedImage bgImg;
    private JPanel mainPanel;
    int width = AppWindow.WINDOW_WIDTH/2;
    int height = AppWindow.WINDOW_HEIGHT/2;

    public StartScreen(AppController c) {
        this.c = c;
        setLayout(null);
        setupRootPanel();
        setupTitle();
        setupButtons();
        bgImg = LoadingImg.StartScreen.loadImage();
    }

    private void setupRootPanel(){
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBounds(width,height,width,height);
        add(mainPanel);
    }

    private void setupTitle(){
        JLabel title = new JLabel("Fash and Chaps", SwingConstants.CENTER);
        title.setFont(MyFont.PIXEL.getFont(50));
        title.setForeground(Color.WHITE);
        mainPanel.add(title, BorderLayout.CENTER);
    }

    private void setupButtons(){
        JButton startButton = MyButton.of("Start", width, height, 30, null);
        //JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> c.startNewGame(1));
        mainPanel.add(startButton, BorderLayout.SOUTH);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
    }

}
