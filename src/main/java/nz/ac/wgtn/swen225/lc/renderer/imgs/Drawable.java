package nz.ac.wgtn.swen225.lc.renderer.imgs;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.domain.Direction;
import nz.ac.wgtn.swen225.lc.domain.Monster;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.entities.*;
import nz.ac.wgtn.swen225.lc.domain.tiles.*;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.List;

/**
 * Creates a JPanel of all tiles needed to be loaded
 * Parses through List of tiles, checking which images to show
 * @author Emily Ung (300663254)
 */
public class Drawable extends JPanel{
    Tile[][] allTiles; //array of all the tiles in the world
    private final int ROWS = AppWindow.MAZE_SIZE/AppWindow.SQUARE_SIZE; //amount of rows drawn
    private final int SIZE = AppWindow.SQUARE_SIZE;//size of each tile
    Player player; //the player
    int centerX;// x pos of player
    int centerY;// y pos of plater
    List<Monster> crabs; // all the enemies

    Map<Direction, LoadingImg> directionLookUpTable = Map.of(  //lookup table to see the direction of the player
            Direction.UP, LoadingImg.PlayerUp,
            Direction.DOWN, LoadingImg.PlayerDown,
            Direction.LEFT, LoadingImg.PlayerLeft,
            Direction.RIGHT, LoadingImg.PlayerRight
    );

    /**
     * Constructor to set all the tiles and player within the world
     * @param currentTiles - all the tiles within the current world
     * @param player - current player
     * @param enemies - list of all crabs
     */
    public Drawable(Tile[][] currentTiles, Player player, List<Monster> enemies){
        setAllTiles(currentTiles, player, enemies);
    }

    /**
     * Gets all the tiles in the world and player
     * @param currentTiles - tiles of the world
     * @param player - the player
     * @param enemies - list of the crabs
     */
    public void setAllTiles(Tile[][] currentTiles, Player player, List<Monster> enemies){
        System.out.println("*DEBUG* Inside of the Renderer Package Now");
        allTiles = currentTiles;
        this.player = player;
        Position p = player.getPos();
        centerX = p.getX();
        centerY = p.getY();
        this.crabs =  enemies;
    }

    /**
     * Sets the JPanel to correct size
     */
    @Override
    public Dimension getPreferredSize() { return new Dimension(Renderer.X_PANEL_WIDTH, Renderer.Y_PANEL_HEIGHT); }

    /**
     * Draws the 9x9 world
     * @param g - the current graphic to be drawn
     */
    public void drawCurrentImage(Graphics g){
        for (int dy = -4; dy <= 4; dy++) {
            for (int dx = -4; dx <= 4; dx++) {
                int worldX = centerX + dx; // x position of the screen
                int worldY = centerY + dy; //

                //ensures positions are within bounds
                if (worldX < 0 || worldX >= allTiles[0].length || worldY < 0 || worldY >= allTiles.length) {continue;}

                Tile tile = allTiles[worldY][worldX];//gets the current tile
                BufferedImage image = tile.accept(new TileImage());//gets the image for that tile

                //calculates the pos of this tile in screen view
                int screenX = (dx + 4) * SIZE;
                int screenY = (dy + 4) * SIZE;

                g.drawImage(image, screenX, screenY, SIZE, SIZE, null); //draws the tile

                // draws the crabs
                for(Monster c : crabs){
                    if(c.getPos().getX() == worldX && c.getPos().getY() == worldY){
                        g.drawImage(LoadingImg.enemyCrab.loadImage(), screenX, screenY, SIZE, SIZE, null);
                    }
                }

            }
        }
        g.drawImage(directionLookUpTable.get(player.getDirection()).loadImage(),4*SIZE, 4*SIZE, SIZE, SIZE, null);// draws the player
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
    }
}
