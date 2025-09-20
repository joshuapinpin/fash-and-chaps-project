package nz.ac.wgtn.swen225.lc.renderer.imgs;
import nz.ac.wgtn.swen225.lc.domain.Direction;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;

import nz.ac.wgtn.swen225.lc.renderer.dummyTest;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Creates a JPanel of all tiles needed to be loaded
 * Parses through List of tiles, checking which images to show
 */
public class Drawable extends JPanel{

    //Map<Direction, LoadingImg> map = new Map.of();

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
    

    List<TileDummy> allTiles = dummyTest.getTiles(); // list of all tiles in current screen

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
        dummyTest.drawTiles(g, this);
    }
}
