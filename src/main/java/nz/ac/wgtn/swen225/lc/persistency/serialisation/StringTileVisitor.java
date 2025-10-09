package nz.ac.wgtn.swen225.lc.persistency.serialisation;

import nz.ac.wgtn.swen225.lc.domain.entities.Entity;
import nz.ac.wgtn.swen225.lc.domain.tiles.*;
import nz.ac.wgtn.swen225.lc.persistency.parse.TileParsers;

import java.util.Optional;

public class StringTileVisitor implements TileVisitor<String> {
    private final StringEntityVisitor entityToString = new StringEntityVisitor();

    @Override
    public String visitWall(Wall wall) {
        return "W";
    }

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

    @Override
    public String visitInfo(Info info) {
        return "I";
    }

    @Override
    public String visitWater(Water water) {
        return "~";
    }

    @Override
    public String visitExit(Exit exit) {
        return "E";
    }
}
