package nz.ac.wgtn.swen225.lc.persistency.parse;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.GameState;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * Represents the mapping from a human-readable String symbol which represents a
 * particular Tile type, to instances of that Tile. Strategy for LevelMaker.
 * All TileParsers should be registered under the TileParsers enum to ensure uniqueness.
 * @author Thomas Ru - 300658840
 */
abstract class TileParser<T extends Tile> {
    private static final Set<String> usedSymbols = new HashSet<>(); // enforce unique symbols
    private final Function<Position, T> defaultTileMaker;
    private final String symbol;

    /**
     * Creates a TileParser given only a symbol to parse - therefore,
     * parse(LevelMaker,String,Position) must be overridden for parsing to occur.
     * @param symbol - the String symbol.
     */
    public TileParser(String symbol) {
        this(
                symbol,
                p->{throw new Error("Must override: parse(LevelMaker,String,Position)");}
        );
    }

    /**
     * Creates a TileParser given a symbol to parse and a way to create a
     * Tile given some Position.
     * @param symbol - the String symbol.
     * @param defaultTileMaker - the Function<Position, Tile> to create Tiles with.
     */
    public TileParser(String symbol, Function<Position, T> defaultTileMaker) {
        this.symbol = verifySymbol(symbol);
        this.defaultTileMaker = defaultTileMaker;
    }

    /**
     * Utility method to check whether a String representation begins with the
     * required symbol, in order to be parsed into a Tile.
     * @param tile - the String representation.
     */
    protected void checkTileStartsWithSymbol(String tile) {
        if (!tile.startsWith(symbol)) {
            throw new IllegalArgumentException("Cannot parse, tile should begin with "+symbol);
        }
    }

    /**
     * Executes parsing strategy for the LevelMaker context.
     * Basic implementation which can be readily overridden -
     * simply checks parameters are valid then
     * @param surroundings - the LevelMaker context for the TileParser strategy, has the whole gameboard.
     * @param tile - the String which is supposed to represent a Tile.
     * @param position - the Position of the Tile.
     * @return - the concrete Tile instance.
     */
    public T parse(GameState surroundings, String tile, Position position) {
        checkNonNull(surroundings, tile, position);
        checkTileStartsWithSymbol(tile);
        T result = defaultTileMaker.apply(position);
        return Objects.requireNonNull(result, "Parsing function returned null.");
        // note LevelMaker context currently unused, but kept as parameter for future-proofing
    }

    /**
     * Utility method to check whether parse(LevelMaker,String,Position) has non-null arguments.
     * @param surroundings - the LevelMaker instance.
     * @param tile - the String instance.
     * @param position - the Position instance.
     */
    protected void checkNonNull(GameState surroundings, String tile, Position position) {
        Objects.requireNonNull(surroundings, "LevelMaker surroundings cannot be null.");
        Objects.requireNonNull(tile, "String cannot be null.");
        Objects.requireNonNull(position, "Position cannot be null.");
    }

    /**
     * Utility method to check whether some String representation of a Tile
     * is valid: non-null, non-empty, and unique (i.e. no other Tile type has the same
     * symbol). If valid, the given symbol is registered as being used.
     * Otherwise, an IllegalArgumentException is thrown.
     * @param symbol - the provided String representation.
     */
    private String verifySymbol(String symbol) throws IllegalArgumentException {
        boolean empty = Objects.requireNonNull(symbol, "Symbol cannot be null.").isEmpty();
        if (empty) {
            throw new IllegalArgumentException("Symbol cannot be empty.");
        }
        if (usedSymbols.contains(symbol)) {
            throw new IllegalArgumentException("Symbol '" + symbol + "' is already used.");
        }
        usedSymbols.add(symbol);
        assert usedSymbols.contains(symbol);
        return symbol;
    }

    /**
     * Returns the general String representing a Tile, e.g. 'F'.
     * @return - the String symbol.
     */
    public String symbol() {
        return symbol;
    }

    /**
     * String representation of a TileParser.
     * @return a representation in the format TileParser[symbol].
     */
    @Override
    public String toString() {
        return "TileParser["+symbol+"]";
    }

    /**
     * Compare a TileParser to some other object to find out if they're equivalent.
     * @param obj - the reference object with which to compare.
     * @return - true they have the same symbol and false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        TileParser<?> other = (TileParser<?>) obj;
        return this.symbol.equals(other.symbol); // only comparison needed due to enforcement of symbol uniqueness
    }

    /**
     * Gives the hash of a TileParser, where equality implies equal hashes.
     * @return - an integer hash.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(symbol);
    }

    // if you want to unify string representations:
    // TODO: maybe refactor w/ Domain so that the Maze toString uses the LevelMaker (thus TileParser) symbols?
}
