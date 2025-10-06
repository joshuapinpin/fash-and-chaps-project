package nz.ac.wgtn.swen225.lc.app.gui.logic;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.gui.layout.LeftPanel;
import nz.ac.wgtn.swen225.lc.domain.entities.EntityColor;
import nz.ac.wgtn.swen225.lc.domain.entities.Key;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;


public class KeysPanel extends JPanel implements LogicPanel {
    AppController c;
    List<Key> keys;
    int keysLeft;
    int maxKeys;

    private Map<EntityColor, BufferedImage> imageKeyMap;


    public KeysPanel(AppController c) {
        this.c = c;
        imageKeyMap = Map.of(
                EntityColor.PINK, LoadingImg.PinkKey.loadImage(),
                EntityColor.ORANGE, LoadingImg.OrangeKey.loadImage(),
                EntityColor.GREEN, LoadingImg.GreenKey.loadImage(),
                EntityColor.PURPLE, LoadingImg.PurpleKey.loadImage()
        );


        setOpaque(false);
    }

    @Override
    public void initialisePanelInfo(int info){
        maxKeys = info;
    }


    @Override
    public void updatePanel(int info) {
        keys = c.domain().getPlayer().getKeys();
        keysLeft = keys.size();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage img;
        for(int i = 0; i < maxKeys; i++){
            if(i < keysLeft){
                EntityColor keyColor = keys.get(i).getColor();
                img = imageKeyMap.get(keyColor);
            }
            else img = LoadingImg.Sand.loadImage();
            int offset = (LeftPanel.PANEL_WIDTH / 2) - ((maxKeys * AppWindow.SQUARE_SIZE) / 2);
            g.drawImage(img, offset +  i * AppWindow.SQUARE_SIZE, 0,
                    AppWindow.SQUARE_SIZE, AppWindow.SQUARE_SIZE, this);
        }
    }

}
