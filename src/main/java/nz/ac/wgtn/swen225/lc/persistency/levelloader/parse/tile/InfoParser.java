package nz.ac.wgtn.swen225.lc.persistency.levelloader.parse.tile;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;
import nz.ac.wgtn.swen225.lc.domain.tiles.Info;

import java.util.Objects;

/**
 * Converts a String symbolic representation to an Info Tile instance.
 * @author Thomas Ru - 300658840
 * @param symbol - the String representation.
 */
record InfoParser(String symbol) implements TileParser {
    /**
     * Construct an Info Tile given a symbolic String representation, if possible.
     * @param surroundings - the LevelMaker context, currently unused.
     * @param tile - the String symbol.
     * @param position - the Position of the tile.
     * @return - the resulting Info tile.
     */
    @Override
    public Tile parse(LevelMaker surroundings, String tile, Position position) {
        String[] split = tile.split(separator);
        assert split[0].equals(symbol);
        String message = split[1];
        return Info.of(message, Objects.requireNonNull(position, "Position cannot be null."));
    }
}

