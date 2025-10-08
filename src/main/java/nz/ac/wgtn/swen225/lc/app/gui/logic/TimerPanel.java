package nz.ac.wgtn.swen225.lc.app.gui.logic;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TimerPanel extends JPanel implements LogicPanel {
    private AppController c;
    private JLabel timerLabel;

    public TimerPanel(AppController c){
        this.c = c;
        setOpaque(false);
        timerLabel = new JLabel(0 + "");
        AppWindow.formatLabel(timerLabel, AppWindow.FONT_SIZE_H1);
        add(timerLabel);
    }

    @Override
    public void initialisePanelInfo(int info){
        if(info > 10) timerLabel.setForeground(Color.WHITE);
        timerLabel.setText(info + "");
    }

    @Override
    public void updatePanel(int info) {
        if(info < 0 ) {timerLabel.setText("XX"); return;}
        if(info <= 10) timerLabel.setForeground(new Color(255, 69, 69));
        timerLabel.setText(info + "");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img = LoadingImg.Sand.loadImage();
        for(int i = 0; i < 2; i++){
            g.drawImage(img, AppWindow.SQUARE_SIZE * 2 + i * AppWindow.SQUARE_SIZE, 0,
                    AppWindow.SQUARE_SIZE, AppWindow.SQUARE_SIZE, this);
        }
    }


}
