package nz.ac.wgtn.swen225.lc.app.gui.screen;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.app.util.MyButton;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * The start screen of the game.
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class StartScreen extends JPanel {
    private AppController c;
    private BufferedImage bgImg;
    private JPanel rootPanel;
    int width = AppWindow.WINDOW_WIDTH/2;
    int height = AppWindow.WINDOW_HEIGHT/2;

    /**
     * Create a new start screen.
     * @param c The app controller.
     */
    public StartScreen(AppController c) {
        this.c = c;
        setLayout(null);
        rootPanel = new JPanel();
        setupLayoutPanel(rootPanel);
        setupContent();
        add(rootPanel);
        bgImg = LoadingImg.StartScreen.loadImage();
    }

    private void setupLayoutPanel(JPanel panel){
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.setOpaque(false);
        panel.setBounds(width,height,width,height);
    }

    private void setupContent(){
        rootPanel.add(Box.createVerticalGlue()); // Push content to center vertically
        setupTitle();
        setupButtons();
        rootPanel.add(Box.createVerticalGlue()); // Push content up
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
        JPanel leftPanel = new JPanel();
        setupLayoutPanel(leftPanel);

        JPanel rightPanel = new JPanel();
        setupLayoutPanel(rightPanel);

        setupSingleButton("Level 1", e -> c.handleInput(Input.LOAD_LEVEL_1), leftPanel);
        setupSingleButton("Level 2", e -> c.handleInput(Input.LOAD_LEVEL_2), rightPanel);
        setupSingleButton("Load Game", e -> c.handleInput(Input.RESUME), leftPanel);
        setupSingleButton("Help", e -> c.help(), rightPanel);

        JPanel mainPanel = new JPanel(new GridLayout(1,2));
        mainPanel.setOpaque(false);
        mainPanel.setPreferredSize(new Dimension(width, AppWindow.SQUARE_SIZE));
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        rootPanel.add(mainPanel);

        setupSingleButton("Exit", e -> c.handleInput(Input.EXIT), rootPanel);
    }

    private void setupSingleButton(String name, ActionListener listener, JPanel mainPanel){
        JButton button = MyButton.of(name, width, height, 30, null);
        button.setPreferredSize(new Dimension(width/2, AppWindow.SQUARE_SIZE));
        button.addActionListener(listener);
        button.setFocusable(false);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(width/2, AppWindow.SQUARE_SIZE));
        panel.setMaximumSize(new Dimension(width/2, AppWindow.SQUARE_SIZE));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(button);
        mainPanel.add(panel);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
    }

}
