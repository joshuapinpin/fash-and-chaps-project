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
public class LeftPanel extends JPanel {
    public static final int PANEL_WIDTH = AppWindow.SQUARE_SIZE * 6;
    public static final int PANEL_HEIGHT = AppWindow.MAZE_SIZE;

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
