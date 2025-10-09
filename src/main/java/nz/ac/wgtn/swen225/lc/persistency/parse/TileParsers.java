package nz.ac.wgtn.swen225.lc.persistency.parse;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.tiles.*;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.game.GameState;

import java.util.*;

/**
 * Stores and provides access to concrete TileParsers for each Tile type.
 * These parsers convert String representations of each Tile into Tile instances.
 * @author Thomas Ru - 300658840
 */
public enum TileParsers {
    /**
     * Stores a parser for Exit Tiles.
     */
    ExitP(new TileParser<Exit>("E", Exit::of) {}),
    /**
     * Stores a parser for Wall Tiles.
     */
    WallP(new TileParser<Wall>("W", Wall::of) {}),
    /**
     * Stores a parser for Water Tiles.
     */
    WaterP(new TileParser<Water>("~", Water::of) {}),
    /**
     * Stores a parser for Info Tiles.
     */
    InfoP(new TileParser<Info>("I", Info::of) {}),
    /**
     * Stores a parser for Free Tiles.
     */
    FreeP(new FreeParser("F"));

    private final TileParser<?> parser;
    public static final String separator = ":";
    private static final Map<String, TileParser<?>> legend = new HashMap<>();
    public static final int MaxMonstersOnTile = 4;
    static {
        for (TileParsers tp : values()) {
            legend.put(tp.parser.symbol(), tp.parser);
        }
    }

    /**
     * Utility method to parse any given String into a Tile, if formatted correctly.
     * @param surroundings - the LevelMaker context for the TileParser strategies, has the whole game board.
     * @param symbol - the String supposedly representing a Tile.
     * @param position - the Position of the Tile on the game board.
     * @return - the Tile instance, otherwise an IllegalArgumentException is thrown.
     */
    public static Tile parseTile(GameState surroundings, String symbol, Position position) {
        TileParser<?> parser = legend.get(symbol.split(TileParsers.separator)[0]);
        if (parser == null) {
            throw new IllegalArgumentException("Unknown symbol: " + symbol);
        }
        return parser.parse(surroundings, symbol, position);
    }

    /**
     * Store a new TileParser.
     * @param parser - the TileParser to be stored.
     */
    TileParsers(TileParser<?> parser) {
        this.parser = parser;
    }
}
