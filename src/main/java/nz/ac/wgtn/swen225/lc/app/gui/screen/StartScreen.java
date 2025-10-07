package nz.ac.wgtn.swen225.lc.app.gui.screen;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.util.MyButton;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class StartScreen extends JPanel {
    private AppController c;
    private BufferedImage bgImg;
    private JPanel rootPanel;
    int width = AppWindow.WINDOW_WIDTH/2;
    int height = AppWindow.WINDOW_HEIGHT/2;

    public StartScreen(AppController c) {
        this.c = c;
        setLayout(null);
        setupRootPanel();
        rootPanel.add(Box.createVerticalGlue()); // Push content to center vertically
        setupTitle();
        setupButtons();
        rootPanel.add(Box.createVerticalGlue()); // Push content up
        bgImg = LoadingImg.StartScreen.loadImage();
    }

    private void setupRootPanel(){
        rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        rootPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rootPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        rootPanel.setOpaque(false);
        rootPanel.setBounds(width,height,width,height);
        add(rootPanel);
    }

    private void setupTitle(){
        JLabel title = new JLabel("Fash and Chaps", SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setFont(MyFont.PIXEL.getFont(50));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        rootPanel.add(title);
    }

    private void setupButtons(){
        setupSingleButton("Start", e -> c.startNewGame(1));
        setupSingleButton("Help", e -> c.help());
        setupSingleButton("Exit", e -> c.exitGame());
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
        rootPanel.add(panel);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
    }

}
