package nz.ac.wgtn.swen225.lc.app.gui.logic;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.gui.layout.LeftPanel;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Panel to display the number of treasures collected.
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class TreasurePanel extends JPanel implements LogicPanel {
    AppController c;
    int treasuresCollected;
    int totalTreasures;

    public TreasurePanel(AppController c){
        this.c = c;
        updatePanel(1);
        setLayout(new GridLayout(4,1));
        setOpaque(false);
    }

    /**
     * Initialise the panel with the total number of treasures.
     * @param info Total number of treasures.
     */
    @Override
    public void initialisePanelInfo(int info){
        totalTreasures = info;
    }

    /**
     * Update the panel with the number of treasures collected.
     * @param info Number of treasures collected.
     */
    @Override
    public void updatePanel(int info) {
        treasuresCollected = info;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int offset = (LeftPanel.PANEL_WIDTH / 2) - ((totalTreasures * AppWindow.SQUARE_SIZE) / 2);
        for(int i = 0; i < totalTreasures; i++){
            BufferedImage img;
            if(i < treasuresCollected) img = LoadingImg.Treasure.loadImage();
            else img = LoadingImg.Sand.loadImage();
            g.drawImage(img, offset + i * AppWindow.SQUARE_SIZE, 0,
                    AppWindow.SQUARE_SIZE, AppWindow.SQUARE_SIZE, this);
        }
    }

}
