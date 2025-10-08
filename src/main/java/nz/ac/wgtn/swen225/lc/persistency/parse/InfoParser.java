package nz.ac.wgtn.swen225.lc.persistency.parse;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.tiles.Info;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.GameState;

import java.util.Objects;

/**
 * Parses a String representation into an Info Tile, if possible.
 * @author Thomas Ru - 300658840
 */
class InfoParser extends TileParser<Info> {
    /**
     * Creates a new parser.
     * @param symbol - the String symbolic representation of an Info tile, e.g. 'I'.
     */
    public InfoParser(String symbol) {
        super(symbol);
    }

    /**
     * Parse some String into an Info Tile.
     * @param surroundings - the LevelMaker context for the TileParser strategy, has the whole gameboard.
     * @param tile - the String which is supposed to represent a Tile.
     * @param position - the Position of the Tile.
     * @return - the Info Tile if possible, otherwise an IllegalArgumentException is thrown.
     */
    @Override // override parse because Info Tile constructor isn't a Function<Position, Tile>
    public Info parse(GameState surroundings, String tile, Position position) {
        checkNonNull(surroundings, tile, position);
        String[] split = tile.split(TileParsers.separator);
        if (!split[0].equals(symbol())) {
            throw new IllegalArgumentException("Cannot parse into an Info tile: "+tile);
        }
        if (split.length != 2) {
            throw new IllegalArgumentException("Info Tile can't have message be empty or contain '"+TileParsers.separator+"'");
        }
        String message = split[1];
        return Info.of(message, Objects.requireNonNull(position, "Position cannot be null."));
    }
}
