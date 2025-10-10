package nz.ac.wgtn.swen225.lc.persistency.domainutil;

import nz.ac.wgtn.swen225.lc.domain.entities.*;
import nz.ac.wgtn.swen225.lc.persistency.parse.EntityParsers;

/**
 * Visitor to get JSON-writeable name of Entity type.
 * @author Thomas Ru - 300658840
 */
public class StringEntityVisitor implements EntityVisitor<String> {
    /**
     * Given a key, get its string representation.
     * @param key the key entity to visit.
     * @return - the String representation.
     */
    @Override
    public String visitKey(Key key) {
        return "Key" + EntityParsers.separator + key.getColor().name();
    }

    /**
     * Given a door, get its string representation
     * @param door the door entity to visit
     * @return - the String representation.
     */
    @Override
    public String visitDoor(Door door) {
        return "Door" + EntityParsers.separator + door.getColor().name();
    }

    /**
     * Given an exitlock, get its string representation
     * @param exitLock -  the exitlock entity to visit
     * @return - the String representation.
     */
    @Override
    public String visitExitLock(ExitLock exitLock) {
        return "ExitLock";
    }

    /**
     * Given a treasure, get its string representation.
     * @param treasure the treasure entity to visit.
     * @return - the String representation.
     */
    @Override
    public String visitTreasure(Treasure treasure) {
        return "Treasure";
    }
}
