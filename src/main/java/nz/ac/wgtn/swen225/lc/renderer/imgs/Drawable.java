package nz.ac.wgtn.swen225.lc.renderer.imgs;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;

import nz.ac.wgtn.swen225.lc.domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * Creates a JPanel of all tiles needed to be loaded
 * Parses through List of tiles, checking which images to show
 */
public class Drawable extends JPanel{

//    Map<Class<?>, LoadingImg> tileIdentities = Map.of( // lookup table, see what tiles match to which image
//            Door.class, LoadingImg.Door,
//            Exit.class, LoadingImg.Exit,
//            ExitLock.class, LoadingImg.ExitLock,
//            Free.class, LoadingImg.Sand,
//            Info.class, LoadingImg.Info,
//            Key.class, LoadingImg.Key,
//            Wall.class, LoadingImg.Rock
//            Player.class.Direction??, LoadingImg.playerLeft,
//            Player.class.Direction??, LoadingImg.playerRight,
//            Player.class.Direction??, LoadingImg.playerForward,
//            Player.class.Direction??, LoadingImg.playerBackward
//

//    );
    
    Map<String, LoadingImg> tileIdentities = Map.ofEntries(
            Map.entry("Sand", LoadingImg.Sand),
            Map.entry("Rock", LoadingImg.Rock),
            Map.entry("Water", LoadingImg.Water),
            Map.entry("PlayerL", LoadingImg.PlayerLeft),
            Map.entry("PlayerR", LoadingImg.PlayerRight),
            Map.entry("PlayerF", LoadingImg.PlayerForward),
            Map.entry("PlayerB", LoadingImg.PlayerBackward),
            Map.entry("orangeKey", LoadingImg.OrangeKey),
            Map.entry("orangeDoor", LoadingImg.OrangeDoor),
            Map.entry("treasure", LoadingImg.Treasure),
            Map.entry("exitLock", LoadingImg.ExitLock),
            Map.entry("exit", LoadingImg.Exit),
            Map.entry("enemyCrab", LoadingImg.enemyCrab)
    );


    /*
     * Testing world map
     */
    public List<TileDummy> getAllTiles(){
        ArrayList<TileDummy> tiles = new ArrayList<>();

        for(int i = 0; i <= 8; i++) {
            for (int j = 0; j <= 8; j++) {
                tiles.add(new TileDummy("Rock", i, j));
            }
        }

        for(int i = 1; i <= 7; i++) {
            for (int j = 1; j <= 7; j++) {
                tiles.add(new TileDummy("Sand", i, j));
            }
        }

        tiles.add(new TileDummy("Water", 6, 7));
        tiles.add(new TileDummy("Water", 7, 7));

        tiles.add(new TileDummy("Water", 4, 5));
        tiles.add(new TileDummy("Water", 4, 4));

        tiles.add(new TileDummy("PlayerB", 4, 4));
        tiles.add(new TileDummy("PlayerF", 2, 4));
        tiles.add(new TileDummy("PlayerR", 6, 4));
        tiles.add(new TileDummy("PlayerL", 7, 4));

        tiles.add(new TileDummy("enemyCrab", 3, 2));

        tiles.add(new TileDummy("exitLock", 4, 7));
        tiles.add(new TileDummy("exit", 4, 8));


        tiles.add(new TileDummy("orangeKey", 6, 2));
        tiles.add(new TileDummy("orangeDoor", 2, 2));

        tiles.add(new TileDummy("treasure", 6, 6));


        return tiles;
    }


    List<TileDummy> allTiles = getAllTiles(); // list of all tiles in current screen


    /*
     * Testing to get correct JFRAME/JPANEL SIZE
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Renderer.X_PANEL_WIDTH, Renderer.Y_PANEL_HEIGHT);
    }


    /*
     * Draws the image onto the JPanel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int COLS = 9;
        int ROWS = 9;
        int SIZE = getWidth() / ROWS;

        for (TileDummy tile : allTiles) {
            int x = tile.x();
            int y = tile.y();
            g.drawImage(tileIdentities.get(tile.name()).loadImage(), x * SIZE, y * SIZE, SIZE, SIZE, null);
        }
    }
}
