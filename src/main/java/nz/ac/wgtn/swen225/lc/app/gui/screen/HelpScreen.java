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
    private JPanel buttonPanel;

    public HelpScreen(AppController c) {
        this.c = c;
        setLayout(new BorderLayout());
        setupTitlePanel();
        setupRootPanel();
        setupButtonPanel();
        bgImg = LoadingImg.Background.loadImage();
    }

    private void setupTitlePanel(){
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.setPreferredSize(new Dimension(AppWindow.WINDOW_WIDTH, AppWindow.SQUARE_SIZE * 3));
        JLabel title = new JLabel("HOW TO PLAY", SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setFont(MyFont.PIXEL.getFont(100));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(title);
//
//        JLabel bird = new JLabel(new ImageIcon(LoadingImg.PlayerDown.loadImage()));
//        titlePanel.add(bird, BorderLayout.WEST);
//
//        JPanel crab = new JPanel();
//        crab.add(new JLabel(new ImageIcon(LoadingImg.enemyCrab.loadImage())));
//        crab.setPreferredSize(new Dimension(AppWindow.SQUARE_SIZE * 3, AppWindow.SQUARE_SIZE * 3));
//        titlePanel.add(crab, BorderLayout.EAST);


        add(titlePanel, BorderLayout.NORTH);
    }

    private void setupRootPanel(){
        rootPanel = new JPanel(null);
        //rootPanel.setOpaque(false);
        rootPanel.setBackground(new Color(255,0,0,80));
        add(rootPanel, BorderLayout.CENTER);
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
        g.setColor(new Color(0, 0, 0, 40)); // alpha 80 for subtle darkness
        g.fillRect(0, 0, AppWindow.WINDOW_WIDTH, AppWindow.WINDOW_HEIGHT);
    }
}
