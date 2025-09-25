package nz.ac.wgtn.swen225.lc.persistency.levelloader.parse.tile;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * Regulate construction of TileParsers, ensuring all symbols are unique.
 * @author Thomas Ru - 300658840
 */
class TileParserFactory {
    private final Set<String> usedSymbols = new HashSet<>();

    /**
     * Check whether a given String symbol for a Tile type is non-null, non-empty, and unique.
     * @param symbol - the String symbol.
     * @return - the String symbol.
     */
    private String verifySymbol(String symbol) {
        boolean empty = Objects.requireNonNull(symbol, "Symbol cannot be null.").isEmpty();
        if (empty) {
            throw new IllegalArgumentException("Symbol cannot be empty.");
        }
        if (usedSymbols.contains(symbol)) {
            throw new IllegalArgumentException("Symbol '" + symbol + "' is already used.");
        }
        usedSymbols.add(symbol);
        return symbol;
    }

    /**
     * Creates a TileParser given the symbol for the given Tile and the constructor
     * for a specific TileParser.
     * @param symbol - the String symbol.
     * @param makeParser - the TileParser constructor, as a Function.
     * @return - the TileParser instance.
     */
    public TileParser createParser(String symbol, Function<String, TileParser> makeParser) {
        return makeParser.apply(verifySymbol(symbol));
    }
}
