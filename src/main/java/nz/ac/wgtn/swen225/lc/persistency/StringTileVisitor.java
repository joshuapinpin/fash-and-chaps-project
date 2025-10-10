package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.*;

import java.util.Optional;

/**
 * Visitor to get the JSON-writeable string representations of each tile type.
 * @author Thomas Ru - 300658840
 */
public class StringTileVisitor implements TileVisitor<String> {
    // for appending entity strings
    private final StringEntityVisitor entityToString = new StringEntityVisitor();

    /**
     * Get the string representation of a wall.
     * @param wall the wall tile to visit
     * @return - the String representation
     */
    @Override
    public String visitWall(Wall wall) {
        return "W";
    }

    /**
     * Get the string representation of a Free tile:
     * includes entity, but monster is independent so has to be
     * added separately if desired
     * @param free the free tile to visit
     * @return - the free tile + entity (if present) string
     */
    @Override
    public String visitFree(Free free) {
        StringBuilder result = new StringBuilder().append('F');
        Optional<Entity> entity = free.getCollectable();
        if (entity.isPresent()) {
            result.append(TileParsers.separator);
            result.append(entity.get().accept(entityToString));
        }
        return result.toString();
    }

    /**
     * Get the string representation of an info tile.
     * @param info the info tile to visit
     * @return - the string
     */
    @Override
    public String visitInfo(Info info) {
        return "I";
    }

    /**
     * Get the string representation of a water tile
     * @param water the water tile to visit
     * @return - the string
     */
    @Override
    public String visitWater(Water water) {
        return "~";
    }

    /**
     * Get the string representation of a water tile
     * @param exit the exit tile to visit
     * @return - the string
     */
    @Override
    public String visitExit(Exit exit) {
        return "E";
    }
}
