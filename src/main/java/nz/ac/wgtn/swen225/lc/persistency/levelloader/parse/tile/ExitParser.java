package nz.ac.wgtn.swen225.lc.persistency.levelloader.parse.tile;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.tiles.Exit;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;

import java.util.Objects;

/**
 * Converts a String symbolic representation to an Exit Tile instance.
 * @author Thomas Ru - 300658840
 * @param symbol - the String representation.
 */
record ExitParser(String symbol) implements TileParser {
    /**
     * Construct an Exit Tile given a symbolic String representation, if possible.
     * @param surroundings - the LevelMaker context, currently unused.
     * @param tile - the String symbol.
     * @param position - the Position of the tile.
     * @return - the resulting Exit tile.
     */
    @Override
    public Tile parse(LevelMaker surroundings, String tile, Position position) {
        checkTileStartsWithSymbol(tile);
        return Exit.of(Objects.requireNonNull(position, "Position cannot be null."));
    }
}

