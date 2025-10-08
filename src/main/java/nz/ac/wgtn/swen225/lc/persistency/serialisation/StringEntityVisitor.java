package nz.ac.wgtn.swen225.lc.persistency.serialisation;

import nz.ac.wgtn.swen225.lc.domain.entities.*;
import nz.ac.wgtn.swen225.lc.persistency.parse.EntityParsers;

public class StringEntityVisitor implements EntityVisitor<String> {
    @Override
    public String visitKey(Key key) {
        return "K" + EntityParsers.separator + key.getColor().name();
    }

    @Override
    public String visitDoor(Door door) {
        return "D" + EntityParsers.separator + door.getColor().name();
    }

    @Override
    public String visitExitLock(ExitLock exitLock) {
        return "L";
    }

    @Override
    public String visitTreasure(Treasure treasure) {
        return "T";
    }
}
