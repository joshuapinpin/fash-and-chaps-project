package nz.ac.wgtn.swen225.lc.persistency.levelloader.parse;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.tiles.Free;
import nz.ac.wgtn.swen225.lc.persistency.levelloader.LevelMaker;

/**
 * Parses a String representation into a Free Tile, if possible.
 * @author Thomas Ru - 300658840
 */
class FreeParser extends TileParser<Free> {
    /**
     * Creates a new parser.
     * @param symbol - the String symbolic representation of a Free tile, e.g. 'F'
     */
    public FreeParser(String symbol) {
        super(symbol, Free::of);
    }

    /**
     * Parse some String into a Free Tile.
     * @param surroundings - the LevelMaker context for the TileParser strategy, has the whole gameboard.
     * @param tile - the String which is supposed to represent a Tile.
     * @param position - the Position of the Tile.
     * @return - the Free Tile if possible, otherwise an IllegalArgumentException is thrown.
     */
    @Override // override parse for additional parsing of Entities on Free tiles
    public Free parse(LevelMaker surroundings, String tile, Position position) {
        checkNonNull(surroundings, tile, position);
        String[] split = tile.split(separator);
        if (split.length!=1 && split.length!=2) {
            throw new IllegalArgumentException("Expected 'F' or 'F:Entity-PROPERTY' but got "+tile);
        }
        if (!split[0].equals(symbol())) {
            throw new IllegalArgumentException("Cannot parse into a Free tile: "+tile);
        }

        Free free = super.parse(surroundings, tile, position); // Tile with no Entity on it
        if (split.length == 2) {
            free.setCollectable(EntityParsers.parseEntity(surroundings, split[1]));
        }
        return free;
    }
}
