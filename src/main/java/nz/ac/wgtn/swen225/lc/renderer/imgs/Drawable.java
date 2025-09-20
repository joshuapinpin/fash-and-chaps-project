package nz.ac.wgtn.swen225.lc.renderer.imgs;
import nz.ac.wgtn.swen225.lc.domain.Direction;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.entities.*;
import nz.ac.wgtn.swen225.lc.domain.tiles.*;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;

import nz.ac.wgtn.swen225.lc.renderer.dummyTest;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/**
 * Creates a JPanel of all tiles needed to be loaded
 * Parses through List of tiles, checking which images to show
 */
public class Drawable extends JPanel{

    Tile[][] allTiles;
    private final int ROWS = 9;
    private final int SIZE = getWidth()/ROWS;
    Player player;
    int centerX;
    int centerY;

    Map<Direction, LoadingImg> directionLookUpTable = Map.of(   //lookup table to see the direction of the player
            Direction.UP, LoadingImg.PlayerUp,
            Direction.DOWN, LoadingImg.PlayerDown,
            Direction.LEFT, LoadingImg.PlayerLeft,
            Direction.RIGHT, LoadingImg.PlayerRight
    );
    Map<Class<? extends Tile>, LoadingImg> tileLookUpTable = Map.of(    //lookup table to see type of tile
            Exit.class, LoadingImg.Exit,
            Free.class, LoadingImg.Sand,
            Info.class, LoadingImg.Info,
            Wall.class, LoadingImg.Rock,
            Water.class, LoadingImg.Water
    );
    Map<Class<? extends Entity>, LoadingImg> entityLookUpTable = Map.of(    //lookup table to see type of entity
            Treasure.class, LoadingImg.Treasure,
            ExitLock.class, LoadingImg.ExitLock
    );
    Map<String, LoadingImg> keyLookUpTable = Map.of(    //lookup table for key entities (to see colour)
            "purple", LoadingImg.PurpleKey,
            "orange", LoadingImg.OrangeKey,
            "blue", LoadingImg.BlueKey,
            "pink", LoadingImg.PinkKey,
            "green", LoadingImg.GreenKey
    );
    Map<String, LoadingImg> doorLookUpTable = Map.of(   //lookup table for door entities (to see colour)
            "purple", LoadingImg.PurpleDoor,
            "orange", LoadingImg.OrangeDoor,
            "blue", LoadingImg.BlueDoor,
            "pink", LoadingImg.PinkDoor,
            "green", LoadingImg.GreenDoor
    );


    /**
     * Gets all the tiles in the world and player
     * @param currentTiles - tiles of the world
     * @param player - the player
     */
    public void setAllTiles(Tile[][] currentTiles, Player player){
        allTiles = currentTiles;
        player = player;
        Position p = player.getPos();
        centerX = p.getX();
        centerY = p.getY();
    }


    /**
     * Sets the JPanel to correct size
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Renderer.X_PANEL_WIDTH, Renderer.Y_PANEL_HEIGHT);
    }

    /**
     * Draws the 9x9 world
     * @param g
     */
    public void drawCurrentImage(Graphics g){
        for (int dy = -4; dy <= 4; dy++) {
            for (int dx = -4; dx <= 4; dx++) {
                int worldX = centerX + dx;
                int worldY = centerY + dy;

                if (worldX < 0 || worldX >= allTiles[0].length || worldY < 0 || worldY >= allTiles.length) {
                    continue;
                }

                Tile tile = allTiles[worldY][worldX];
                BufferedImage image = findImage(tile);

                int screenX = (dx + 4) * SIZE;
                int screenY = (dy + 4) * SIZE;

                g.drawImage(image, screenX, screenY, SIZE, SIZE, null);
            }
        }
    }


    /**
     * Finds the corresponding image for type of tile
     * @param tile - type of tile
     * @return - returns the image of the current tile
     */
    public BufferedImage findImage(Tile tile){
        if (tile instanceof Wall) {
            return tileLookUpTable.get(Wall.class).loadImage();
        } else if (tile instanceof Free f) {
            if (f.getCollectable().isPresent()) {
                Entity entity = f.getCollectable().get();
                if (entity instanceof Key k) {
                    return keyLookUpTable.get(k.getColor()).loadImage();
                } else if (entity instanceof Door d) {
                    return doorLookUpTable.get(d.getColor()).loadImage();
                } else if (entity instanceof Treasure) {
                    return entityLookUpTable.get(Treasure.class).loadImage();
                } else if (entity instanceof ExitLock) {
                    return entityLookUpTable.get(ExitLock.class).loadImage();
                }
            }
        } else if (tile instanceof Exit) {
            return tileLookUpTable.get(Exit.class).loadImage();
        } else if (tile instanceof Water) {
            return tileLookUpTable.get(Water.class).loadImage();
        } else if (tile instanceof Info){
            return tileLookUpTable.get(Info.class).loadImage();
        }
        return tileLookUpTable.get(Free.class).loadImage();
    }











    /**
     * Draws tiles onto the JPanel
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //dummyTest.drawTiles(g, this);
        drawCurrentImage(g);
        g.drawImage(directionLookUpTable.get(player.getDirection()).loadImage(),centerX, centerY, SIZE, SIZE, null);
    }
}
