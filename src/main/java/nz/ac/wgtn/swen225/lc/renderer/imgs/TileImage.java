package nz.ac.wgtn.swen225.lc.renderer.imgs;

import nz.ac.wgtn.swen225.lc.domain.*;

import java.awt.image.BufferedImage;

/**
 * Concrete Visitor Class to implement Visitor Pattern
 * Draws the specific image for the tile
 * @author Emily Ung (300663254)
 */
public class TileImage implements TileVisitor<BufferedImage> {

    /**
     * Draws Rock/wall image
     * @param wall - wall tile object
     * @return - a rock image
     */
    @Override
    public BufferedImage visitWall(Wall wall) { return LoadingImg.Rock.loadImage(); }

    /**
     * Draws Sand image OR an entity on top
     * @param free - free tile object
     * @return - a sand image OR entity image
     */
    @Override
    public BufferedImage visitFree(Free free) {
        if(free.getCollectable().isPresent()){ return free.getCollectable().get().accept(new EntityImage()); } // checks if the tile has an entity on it
        return LoadingImg.Sand.loadImage();
    }

    /**
     * Draws info tile image
     * @param info - info tile object
     * @return - info tile image
     */
    @Override
    public BufferedImage visitInfo(Info info) { return LoadingImg.Info.loadImage(); }

    /**
     * Draws water tile image
     * @param water - water tile object
     * @return - water tile image
     */
    @Override
    public BufferedImage visitWater(Water water) { return LoadingImg.Water.loadImage(); }

    /**
     * Draws exit tile image
     * @param exit - exit tile object
     * @return - exit tile image
     */
    @Override
    public BufferedImage visitExit(Exit exit) { return LoadingImg.Exit.loadImage(); }
}