package nz.ac.wgtn.swen225.lc.app.gui.logic;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * LevelPanel class for the GUI
 * Displays the current level the player is on
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class LevelPanel extends JPanel implements LogicPanel {
    private AppController c;
    private JLabel levelLabel;

    public LevelPanel(AppController c) {
        this.c = c;
        setOpaque(false);
        levelLabel = new JLabel(c.level() + "");
        AppWindow.formatLabel(levelLabel, AppWindow.FONT_SIZE_H1);
        add(levelLabel);
    }

    /**
     * Initialise the panel with the current level.
     * @param info Current level
     */
    @Override
    public void updatePanel(int info) {
        levelLabel.setText(info + "");
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img = LoadingImg.Sand.loadImage();
        for(int i = 0; i < 2; i++){
            g.drawImage(img,AppWindow.SQUARE_SIZE * 2 + i * AppWindow.SQUARE_SIZE, 0,
                    AppWindow.SQUARE_SIZE, AppWindow.SQUARE_SIZE, this);
        }
    }
}
