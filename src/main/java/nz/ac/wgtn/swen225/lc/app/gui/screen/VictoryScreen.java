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

public class VictoryScreen extends JPanel {
    private BufferedImage bgImg;
    private AppController c;
    private JPanel titlePanel, buttonPanel;
    int width = AppWindow.WINDOW_WIDTH/2;
    int height = AppWindow.WINDOW_HEIGHT/2;

    public VictoryScreen(AppController c) {
        this.c = c;
        setLayout(null);
        setupTitlePanel();
        setupButtonPanel();
//
//        JLabel title = new JLabel("VICTORY !!!", SwingConstants.CENTER);
//        title.setFont(MyFont.PIXEL.getFont(50));
//        title.setForeground(new Color(122, 255, 122));
//        add(title, BorderLayout.CENTER);
//
//        // Buttons if relevant
//        // Maybe give options for level 1 or 2.
//        JButton startButton = new JButton("Start Game");
//        startButton.addActionListener(e -> c.restartLevel());
//        add(startButton, BorderLayout.SOUTH);

        bgImg = LoadingImg.WinScreen.loadImage();
    }

    private void setupTitlePanel(){
        titlePanel = new JPanel(new BorderLayout());
//        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
//        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        titlePanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        titlePanel.setOpaque(false);
        titlePanel.setBounds(0,0,width,height);
        add(titlePanel);

        setupTitle();
    }

    private void setupTitle(){
        JLabel title = new JLabel("VICTORY", SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setFont(MyFont.PIXEL.getFont(80));
        title.setForeground(new Color(122, 255, 122));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(title);
    }

    private void setupButtonPanel(){
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(width,height, width,height);
        add(buttonPanel);

        setupButtons();
    }

    private void setupButtons(){
        buttonPanel.add(Box.createVerticalGlue()); // Push content to center vertically
        setupSingleButton("Level 1", e -> c.startNewGame(1));
        setupSingleButton("Level 2", e -> c.startNewGame(2));
        setupSingleButton("Exit", e -> c.exitGame());
        buttonPanel.add(Box.createVerticalGlue()); // Push content up
    }


    private void setupSingleButton(String name, ActionListener event){
        JButton button = MyButton.of(name, width, height, 30, null);
        button.setPreferredSize(new Dimension(width/2, AppWindow.SQUARE_SIZE));
        button.addActionListener(event);
        button.setFocusable(false);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(width/2, AppWindow.SQUARE_SIZE));
        panel.setMaximumSize(new Dimension(width/2, AppWindow.SQUARE_SIZE));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(button);
        buttonPanel.add(panel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
    }
}
