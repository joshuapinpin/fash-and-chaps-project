package nz.ac.wgtn.swen225.lc.renderer.imgs;

import nz.ac.wgtn.swen225.lc.domain.entities.*;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Concrete Visitor Class to implement Visitor Pattern
 * Draws the specific entity image
 * @author Emily Ung (300663254)
 */
public class EntityImage implements EntityVisitor<BufferedImage>{
    Map<EntityColor, LoadingImg> keyLookUpTable = Map.of(    //lookup table for key entities (to see colour)
            EntityColor.PURPLE, LoadingImg.PurpleKey,
            EntityColor.ORANGE, LoadingImg.OrangeKey,
            EntityColor.PINK, LoadingImg.PinkKey,
            EntityColor.GREEN, LoadingImg.GreenKey
    );
    Map<EntityColor, LoadingImg> doorLookUpTable = Map.of(   //lookup table for door entities (to see colour)
            EntityColor.PURPLE, LoadingImg.PurpleDoor,
            EntityColor.ORANGE, LoadingImg.OrangeDoor,
            EntityColor.PINK, LoadingImg.PinkDoor,
            EntityColor.GREEN, LoadingImg.GreenDoor
    );

    /**
     * Draws key collectable image
     * @param key - current key tile object
     * @return - key tile image
     */
    @Override
    public BufferedImage visitKey(Key key){ return keyLookUpTable.get(key.getColor()).loadImage(); }

    /**
     * Draws door image
     * @param door - current door tile object
     * @return - door tile image
     */
    @Override
    public BufferedImage visitDoor(Door door){ return doorLookUpTable.get(door.getColor()).loadImage();}

    /**
     * Draws exit lock image
     * @param exitLock - current exit lock tile object
     * @return - exit lock tile image
     */
    @Override
    public BufferedImage visitExitLock(ExitLock exitLock){ return LoadingImg.ExitLock.loadImage(); }

    /**
     * Draws coin image
     * @param treasure - current treasure tile object
     * @return - coin tile image
     */
    @Override
    public BufferedImage visitTreasure(Treasure treasure){ return LoadingImg.Treasure.loadImage(); }
}