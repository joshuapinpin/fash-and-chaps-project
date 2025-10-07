package nz.ac.wgtn.swen225.lc.app.gui.screen;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.util.MyButton;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class DefeatScreen extends JPanel  {
    private AppController c;
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private BufferedImage bgImg;
    int width = AppWindow.WINDOW_WIDTH/2;
    int height = AppWindow.WINDOW_HEIGHT/2;
    int buttonGap = 20;

    public DefeatScreen(AppController c) {
        this.c = c;
        setLayout(new BorderLayout());
        setupTitlePanel();
        setupButtonPanel();

        bgImg = LoadingImg.LoseScreen.loadImage();
    }

    private void setupTitlePanel(){
        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.setPreferredSize(new Dimension(AppWindow.WINDOW_WIDTH, AppWindow.SQUARE_SIZE * 4));
        add(titlePanel, BorderLayout.NORTH);
        setupTitle();
    }

    private void setupTitle(){
        JLabel title = new JLabel("DEFEAT", SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setFont(MyFont.PIXEL.getFont(100));
        title.setForeground(Color.red);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(title);
    }

    private void setupButtonPanel(){
        buttonPanel = new JPanel(new GridLayout(1,3, buttonGap, buttonGap));
        buttonPanel.setPreferredSize(new Dimension(AppWindow.WINDOW_WIDTH/2, AppWindow.SQUARE_SIZE * 2));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(buttonGap, buttonGap, buttonGap,buttonGap));
        add(buttonPanel, BorderLayout.SOUTH);
        setupButtons();
    }

    private void setupButtons(){
        setupSingleButton("Level 1", e -> c.startNewGame(1));
        setupSingleButton("Level 2", e -> c.startNewGame(2));
        setupSingleButton("Home", e -> c.exitGame());
    }

    private void setupSingleButton(String name, ActionListener listener ){
        JButton button =MyButton.of(name, width, height, 30, null);
        button.addActionListener(listener);
        button.setFocusable(false);
        buttonPanel.add(button);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
    }
}
