package nz.ac.wgtn.swen225.lc.renderer;


import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Drawable extends JPanel{
    Map<TileDummy, LoadingImg> tileIdentities = Map.of();
    List allTiles = new List();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(LoadingImg.FreeTile.loadImage(), 50, 50, null);
    }
}


