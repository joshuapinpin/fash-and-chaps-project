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
import java.util.List;

public class HelpScreen extends JPanel {
    private AppController c;

    private BufferedImage bgImg;
    private JPanel rootPanel;
    private JPanel controlsPanel;
    private JPanel managementPanel;
    private JPanel helpPanel;
    private JPanel buttonPanel;

    int fontSize= AppWindow.FONT_SIZE_H3;
    int headerSize = AppWindow.FONT_SIZE_H2;
    int helpSize = 25;

    public HelpScreen(AppController c) {
        this.c = c;
        setLayout(new BorderLayout());
        setupTitlePanel();
        setupRootPanel();

        setupControlsPanel();
        setupManagementPanel();

        setupHelpPanel();
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

        helpPanel = new JPanel();
        controlsPanel = new JPanel();
        managementPanel = new JPanel();

        JPanel rightPanel = new JPanel(new GridLayout(2,1));
        rightPanel.setOpaque(false);
        rightPanel.add(controlsPanel);
        rightPanel.add(managementPanel);

        rootPanel.add(helpPanel);
        rootPanel.add(rightPanel);
        add(rootPanel, BorderLayout.CENTER);
    }

    private void setupControlsPanel(){
        controlsPanel.setOpaque(false);
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        controlsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlsPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        controlsPanel.add(Box.createVerticalGlue());
        addText("Game Controls", controlsPanel, headerSize);
        addText("ARROW KEYS: Move Player", controlsPanel,fontSize);
        addText("SPACEBAR: Pause Game", controlsPanel,fontSize);
        addText("ESCAPE: Resumes Current Game", controlsPanel,fontSize);
        controlsPanel.add(Box.createVerticalGlue());

    }

    private void setupManagementPanel() {
        managementPanel.setOpaque(false);
        managementPanel.setLayout(new BoxLayout(managementPanel, BoxLayout.Y_AXIS));
        managementPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        managementPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        managementPanel.add(Box.createVerticalGlue());


        addText("Game Management", managementPanel, headerSize);
        addText("CTRL 1: Start New Game (Level 1)", managementPanel,fontSize);
        addText("CTRL 2: Start New Game (Level 2)", managementPanel,fontSize);
        addText("CTRL X: Exit Game (Lose Current Game)", managementPanel,fontSize);
        addText("CTRL S: Exit Game (Save Current Game)", managementPanel,fontSize);
        addText("CTRL R: Resume Saved Game", managementPanel,fontSize);
        managementPanel.add(Box.createVerticalGlue());
    }

    private void setupHelpPanel() {
        helpPanel.setOpaque(false);
        helpPanel.setLayout(new GridLayout(8,1));

        addTextWithPanel("Pick up keys to open doors.", helpPanel, helpSize);
        addImageToPanel(List.of(LoadingImg.OrangeKey.loadImage(),LoadingImg.OrangeDoor.loadImage()), helpPanel);
        addTextWithPanel("Collect coins to open chest.", helpPanel, helpSize);
        addImageToPanel(List.of(LoadingImg.Treasure.loadImage(),LoadingImg.ExitLock.loadImage()), helpPanel);
        addTextWithPanel("Avoid crabs and puddles.", helpPanel, helpSize);
        addImageToPanel(List.of(LoadingImg.enemyCrab.loadImage(),LoadingImg.Water.loadImage()), helpPanel);
        addTextWithPanel("Reach sand castle to win!", helpPanel, helpSize);
        addImageToPanel(List.of(LoadingImg.Exit.loadImage()), helpPanel);
    }

    private void addImageToPanel(List<BufferedImage> imgs, JPanel mainPanel){
        int numImgs = imgs.size();
        int offset = (AppWindow.WINDOW_WIDTH/4) - (numImgs * AppWindow.SQUARE_SIZE / 2);
        int squareSize = AppWindow.SQUARE_SIZE;
        JPanel panel = new JPanel(null){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for(int i = 0; i < numImgs; i++){
                    g.drawImage(imgs.get(i), offset + i * squareSize, 0, squareSize, squareSize, this);
                }
            }
        };
        panel.setOpaque(false);
        mainPanel.add(panel);
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

    private void addTextWithPanel(String text, JPanel mainPanel, int fontSize){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        addText(text, panel, fontSize);
        mainPanel.add(panel);
    }

    private void addText(String text, JPanel panel, int fontSize){
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(MyFont.PIXEL.getFont(fontSize));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing between labels
        panel.add(label);
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
        g.setColor(new Color(0, 0, 0, 60));
        g.fillRect(0, 0, AppWindow.WINDOW_WIDTH, AppWindow.WINDOW_HEIGHT);
    }
}
