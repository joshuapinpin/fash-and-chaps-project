package nz.ac.wgtn.swen225.lc.app.gui.logic;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.gui.GamePanel;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TimerPanel extends JPanel implements GamePanel, LogicPanel {
    private AppController c;
    private JLabel label;
    private JPanel panel;

    public TimerPanel(AppController c){
        this.c = c;
    }

    @Override
    public void updateLogic(String info) {

    }

    @Override
    public void updatePanel() {
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img = LoadingImg.Sand.loadImage();
        for(int i = 0; i < 4; i++){
            g.drawImage(img, AppWindow.SQUARE_SIZE + i * AppWindow.SQUARE_SIZE, 0,
                    AppWindow.SQUARE_SIZE, AppWindow.SQUARE_SIZE, this);
        }
    }


}
