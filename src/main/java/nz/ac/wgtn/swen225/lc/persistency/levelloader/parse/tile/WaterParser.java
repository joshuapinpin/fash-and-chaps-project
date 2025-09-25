package nz.ac.wgtn.swen225.lc.persistency.levelloader.parse.tile;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;
import nz.ac.wgtn.swen225.lc.domain.tiles.Water;

import java.util.Objects;

/**
 * Converts a String symbolic representation to a Water Tile instance.
 * @author Thomas Ru - 300658840
 * @param symbol - the String representation.
 */
record WaterParser(String symbol) implements TileParser {
    /**
     * Construct a Water Tile given a symbolic String representation, if possible.
     * @param surroundings - the LevelMaker context, currently unused.
     * @param tile - the String symbol.
     * @param position - the Position of the tile.
     * @return - the resulting Water tile.
     */
    @Override
    public Tile parse(LevelMaker surroundings, String tile, Position position) {
        checkTileStartsWithSymbol(tile);
        return Water.of(Objects.requireNonNull(position, "Position cannot be null."));
    }
}

