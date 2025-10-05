package nz.ac.wgtn.swen225.lc.renderer.imgs;

import nz.ac.wgtn.swen225.lc.domain.entities.*;

import java.awt.image.BufferedImage;
import java.util.Map;

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

    @Override
    public BufferedImage visitKey(Key key) { return keyLookUpTable.get(key.getColor()).loadImage(); }

    @Override
    public BufferedImage visitDoor(Door door) { return doorLookUpTable.get(door.getColor()).loadImage();}

    @Override
    public BufferedImage visitExitLock(ExitLock exitLock) { return LoadingImg.ExitLock.loadImage(); }

    @Override
    public BufferedImage visitTreasure(Treasure treasure) { return LoadingImg.Treasure.loadImage(); }
}

