package nz.ac.wgtn.swen225.lc.renderer.imgs;

import nz.ac.wgtn.swen225.lc.domain.tiles.*;

import java.awt.image.BufferedImage;

public class TileImage implements TileVisitor<BufferedImage>{

    @Override
    public BufferedImage visitWall(Wall wall) { return LoadingImg.Rock.loadImage(); }

    @Override
    public BufferedImage visitFree(Free free) {
        if(free.getCollectable().isPresent()){ return free.getCollectable().get().accept(new EntityImage()); }
        return LoadingImg.Sand.loadImage();
    }

    @Override
    public BufferedImage visitInfo(Info info) { return LoadingImg.Info.loadImage(); }

    @Override
    public BufferedImage visitWater(Water water) { return LoadingImg.Water.loadImage(); }

    @Override
    public BufferedImage visitExit(Exit exit) { return LoadingImg.Exit.loadImage(); }
}
