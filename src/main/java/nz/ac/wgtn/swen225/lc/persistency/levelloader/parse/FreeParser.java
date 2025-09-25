package nz.ac.wgtn.swen225.lc.persistency.levelloader.parse;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.entities.Entity;
import nz.ac.wgtn.swen225.lc.domain.tiles.Free;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;

import java.util.Objects;

/**
 * Converts a String symbolic representation to a Free Tile instance.
 * @author Thomas Ru - 300658840
 * @param symbol - the String representation.
 */
record FreeParser(String symbol) implements TileParser {
    /**
     * Construct a Free Tile given a symbolic String representation, if possible.
     * @param surroundings - the LevelMaker context, currently unused.
     * @param tile - the String symbol.
     * @param position - the Position of the tile.
     * @return - the resulting Free tile.
     */
    @Override
    public Tile parse(LevelMaker surroundings, String tile, Position position) {
        String[] split = tile.split(separator);
        assert split.length > 0;
        assert split[0].equals(symbol);
        Free free = Free.of(Objects.requireNonNull(position, "Position cannot be null."));

        if (split.length > 2) {
            throw new IllegalArgumentException("Expected 'F' or 'F:Entity-PROPERTY' but got"+tile);
        }

        if (split.length == 2) {
            Entity entity = EntityParser.parseEntity(split[1]);
            free.setCollectable(entity);
        }
        return free;
    }
}
