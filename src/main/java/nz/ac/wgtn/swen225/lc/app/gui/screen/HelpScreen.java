package nz.ac.wgtn.swen225.lc.app.gui.screen;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.app.util.MyButton;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HelpScreen extends JPanel {
    private AppController c;

    private BufferedImage bgImg;
    private JPanel rootPanel;
    private JPanel controlsPanel;
    private JPanel helpPanel;
    private JPanel buttonPanel;

    public HelpScreen(AppController c) {
        this.c = c;
        setLayout(new BorderLayout());
        setupTitlePanel();
        setupRootPanel();
        setupHelpPanel();
        setupControlsPanel();
        setupButtonPanel();
        bgImg = LoadingImg.Background.loadImage();
    }

    private void setupTitlePanel(){
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.setPreferredSize(new Dimension(AppWindow.WINDOW_WIDTH, AppWindow.SQUARE_SIZE * 2));
        JLabel title = new JLabel("HOW TO PLAY", SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setFont(MyFont.PIXEL.getFont(90));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);
    }

    private void setupRootPanel(){
        rootPanel = new JPanel(new GridLayout(1,2));
        rootPanel.setOpaque(false);
        rootPanel.setBackground(new Color(255,0,0,80));

        helpPanel = new JPanel();
        controlsPanel = new JPanel();

        rootPanel.add(helpPanel);
        rootPanel.add(controlsPanel);
        add(rootPanel, BorderLayout.CENTER);
    }

    private void setupControlsPanel() {
        controlsPanel.setOpaque(false);
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        controlsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlsPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        controlsPanel.add(Box.createVerticalGlue());
        addText("ARROW KEYS: Move Player", controlsPanel);
        addText("SPACEBAR: Pause Game", controlsPanel);
        addText("ESCAPE: Resumes Current Game", controlsPanel);
        addText("CTRL 1: Start New Game (Level 1)", controlsPanel);
        addText("CTRL 2: Start New Game (Level 2)", controlsPanel);
        addText("CTRL X: Exit Game (Lose Current Game)", controlsPanel);
        addText("CTRL S: Exit Game (Save Current Game)", controlsPanel);
        addText("CTRL R: Resume Saved Game", controlsPanel);
        controlsPanel.add(Box.createVerticalGlue());
    }

    private void addText(String text, JPanel panel){
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(MyFont.PIXEL.getFont(AppWindow.FONT_SIZE_H2));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing between labels
        panel.add(label);
    }

    private void setupHelpPanel() {
    }


    private void setupButtonPanel(){
        buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension(AppWindow.WINDOW_WIDTH/2, AppWindow.SQUARE_SIZE*2));

        JButton button = MyButton.of("Back", AppWindow.WINDOW_WIDTH/2, AppWindow.SQUARE_SIZE, 30, null);
        button.addActionListener(e -> c.handleInput(Input.EXIT));

        int gapX = AppWindow.WINDOW_WIDTH / 4;
        int gapY = AppWindow.SQUARE_SIZE / 4;
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(gapY, gapX, gapY, gapX));
        buttonPanel.add(button);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int squareSize = AppWindow.SQUARE_SIZE;
        for(int col = 0; col < AppWindow.WINDOW_COLS; col++){
            for(int row = 0; row < AppWindow.WINDOW_ROWS; row++){
                g.drawImage(bgImg, col * squareSize, row * squareSize, squareSize, squareSize, this);
            }
        }
         //Darken the background
        g.setColor(new Color(0, 0, 0, 80));
        g.fillRect(0, 0, AppWindow.WINDOW_WIDTH, AppWindow.WINDOW_HEIGHT);
    }
}
