package nz.ac.wgtn.swen225.lc.persistency.serialisation;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.persistency.parse.EntityParsers;

public class StringEntityVisitor implements EntityVisitor<String> {
    @Override
    public String visitKey(Key key) {
        return "Key" + EntityParsers.separator + key.getColor().name();
    }

    @Override
    public String visitDoor(Door door) {
        return "Door" + EntityParsers.separator + door.getColor().name();
    }

    @Override
    public String visitExitLock(ExitLock exitLock) {
        return "ExitLock";
    }

    @Override
    public String visitTreasure(Treasure treasure) {
        return "Treasure";
    }
}
