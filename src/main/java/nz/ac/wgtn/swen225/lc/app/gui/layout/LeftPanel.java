package nz.ac.wgtn.swen225.lc.app.gui.layout;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.controller.local.TimerController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.gui.GamePanel;
import nz.ac.wgtn.swen225.lc.app.gui.logic.KeysPanel;
import nz.ac.wgtn.swen225.lc.app.gui.logic.LevelPanel;
import nz.ac.wgtn.swen225.lc.app.gui.logic.TimerPanel;
import nz.ac.wgtn.swen225.lc.app.gui.logic.TreasurePanel;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

/**
 * Displays time left (countdown), current level, keys collected, and treasures remaining.
 * Offers buttons for pausing, saving, quitting, and resuming a game.
 * Should also offer a help button that shows game rules.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class LeftPanel extends JPanel implements GamePanel {
    public static final int PANEL_WIDTH = AppWindow.SQUARE_SIZE * 6;
    public static final int PANEL_HEIGHT = AppWindow.MAZE_SIZE;
    public static final int FONT_SIZE = 40;

    private JPanel levelPanel, timerPanel, keysPanel, treasurePanel;
    private JLabel timerLabel, levelLabel;
    private BufferedImage bgImg;
    private MyFont font;

    private AppController c;
    private TimerController timerController;

    private List<JComponent> allComponents;

    /**
     * Constructor for LeftPanel.
     * @param controller AppController
     */
    public LeftPanel(AppController controller){
        this.c = controller;
        this.timerController = controller.timerController();
        this.font = MyFont.PIXEL;
        setLayout(new GridLayout(8, 1));
        setOpaque(false);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        //setupComponents();
        bgImg = LoadingImg.Water.loadImage();
    }

    private void setupComponents(){
        allComponents = new ArrayList<>();
        setupLabelWithPanel("Level", levelPanel = new LevelPanel(c));
        setupLabelWithPanel("Timer",  timerPanel = new TimerPanel(c));
        setupLabelWithPanel("Keys",  keysPanel = new KeysPanel(c));
        setupLabelWithPanel("Treasure", treasurePanel = new TreasurePanel(c));
    }

    private void setupLabelWithPanel(String name, JPanel panel){
        JLabel label = new JLabel(name);
        setupLabel(label);
        panel.setOpaque(false);

        if(panel == timerPanel){
            timerLabel = new JLabel("0");
            setupLabel(timerLabel);
            panel.add(timerLabel);
        } else if(panel == levelPanel){
            levelLabel = new JLabel(c.level() + "");
            setupLabel(levelLabel);
            panel.add(levelLabel);
        }

        add(label);
        add(panel);
        allComponents.add(label);
        allComponents.add(panel);
    }

    private void setupLabel(JLabel label){
        label.setFont(font.getFont(FONT_SIZE));
        label.setForeground(Color.white);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
    }

    /**
     * Update the panel's components.
     */
    @Override
    public void updatePanel() {
//        int timeLeft = timerController.getTimeLeft();
//        timerLabel.setText(timeLeft + "");
//        int level = c.level();
//        levelLabel.setText(level + "");
//        allComponents.forEach(JComponent::repaint);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int squareSize = AppWindow.SQUARE_SIZE;
        int x = 0, y = 0;
        while(x < PANEL_WIDTH){
            while(y < PANEL_HEIGHT){
                g.drawImage(bgImg, x, y, squareSize, squareSize, this);
                y += squareSize;
            }
            y = 0;
            x += squareSize;
        }
    }
}
