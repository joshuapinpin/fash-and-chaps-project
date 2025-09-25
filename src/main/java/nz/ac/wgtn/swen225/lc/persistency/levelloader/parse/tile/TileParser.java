package nz.ac.wgtn.swen225.lc.persistency.levelloader.parse.tile;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;

/**
 * Represents the mapping from a human-readable String symbol which represents a
 * particular Tile type to instances of that Tile. Strategy for LevelMaker.
 * @author Thomas Ru - 300658840
 */
interface TileParser {
    String separator = ":";

    default void checkTileStartsWithSymbol(String tile) {
        if (!tile.startsWith(symbol())) {
            throw new IllegalArgumentException("Cannot parse, tile should begin with "+symbol());
        }
    }
    /**
     * Executes strategy for the LevelMaker context.
     * @param surroundings - the LevelMaker context.
     * @param tile - the String symbol.
     * @param position - the Position of the Tile.
     * @return - the concrete Tile instance.
     */
    Tile parse(LevelMaker surroundings, String tile, Position position);

    /**
     * Returns the general symbolic String representing a Tile.
     * @return - the String symbol.
     */
    String symbol();
    // if you want to unify string representations:
    // TODO: maybe refactor w/ Domain so that the Maze toString uses the LevelMaker (thus TileParser) symbols?
}
