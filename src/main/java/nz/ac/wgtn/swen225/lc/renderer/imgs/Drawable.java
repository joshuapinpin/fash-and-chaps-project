package nz.ac.wgtn.swen225.lc.renderer.imgs;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg.FreeTile;

/**
 * Creates a JPanel of all tiles needed to be loaded
 * Parses through List of tiles, checking which images to show
 */
public class Drawable extends JPanel{
    Map<Class<?>, LoadingImg> tileIdentities = Map.of(); // lookup table, see what tiles match to which image
    List<TileDummy> allTiles = new ArrayList<TileDummy>(); // list of all tiles in current screen


    /*
     * Finds the correct image to use
     */
    public void findTileImage(){

    }


    /*
     * Draws the image onto the JPanel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        for(TileDummy tile : allTiles){
            //check what thing the tile contains
            //
        }
        g.drawImage(LoadingImg.Wall.loadImage(), 50, 50, null);
    }




}
