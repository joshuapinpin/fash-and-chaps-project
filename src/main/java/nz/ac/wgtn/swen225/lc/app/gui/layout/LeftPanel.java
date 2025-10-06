package nz.ac.wgtn.swen225.lc.app.gui.layout;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.gui.logic.*;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

/**
 * Displays time left (countdown), current level, keys collected, and treasures remaining.
 * Offers buttons for pausing, saving, quitting, and resuming a game.
 * Should also offer a help button that shows game rules.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class LeftPanel extends JPanel {
    public static final int PANEL_WIDTH = AppWindow.SQUARE_SIZE * 6;
    public static final int PANEL_HEIGHT = AppWindow.MAZE_SIZE;


    // Game Logic Panels
    private KeysPanel keysPanel;
    private LevelPanel levelPanel;
    private TimerPanel timerPanel;
    private TreasurePanel treasurePanel;
    private List<JPanel> logicPanels;

    private AppController c;
    private BufferedImage bgImg;

    /**
     * Constructor for LeftPanel.
     * @param controller AppController
     */
    public LeftPanel(AppController controller){
        this.c = controller;
        setLayout(new GridLayout(9, 1));
        setOpaque(false);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        bgImg = LoadingImg.Water.loadImage();
        logicPanels = new ArrayList<>();
        setupLogicPanels();
    }

    private void setupLogicPanels() {
        setupSingleLogicPanel("Level", levelPanel = new LevelPanel(c));
        setupSingleLogicPanel("Timer", timerPanel = new TimerPanel(c));
        setupSingleLogicPanel("Keys", keysPanel = new KeysPanel(c));
        setupSingleLogicPanel("Treasure", treasurePanel = new TreasurePanel(c));
    }

    private void setupSingleLogicPanel(String name, JPanel panel){
        JLabel nameLabel = new JLabel(name);
        AppWindow.formatLabel(nameLabel, AppWindow.FONT_SIZE_H1);
        panel.add(nameLabel);
        add(nameLabel);
        add(panel);
        logicPanels.add(panel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        int squareSize = AppWindow.SQUARE_SIZE;
//        int x = 0, y = 0;
//        while(x < PANEL_WIDTH){
//            while(y < PANEL_HEIGHT){
//                g.drawImage(bgImg, x, y, squareSize, squareSize, this);
//                y += squareSize;
//            }
//            y = 0;
//            x += squareSize;
//        }
    }

    // ====== GETTERS ======
    public List<JPanel> logicPanels(){return Collections.unmodifiableList(logicPanels);}
    public KeysPanel keysPanel(){return keysPanel;}
    public LevelPanel levelPanel(){return levelPanel;}
    public TimerPanel timerPanel(){return timerPanel;}
    public TreasurePanel treasurePanel(){return treasurePanel;}
}
