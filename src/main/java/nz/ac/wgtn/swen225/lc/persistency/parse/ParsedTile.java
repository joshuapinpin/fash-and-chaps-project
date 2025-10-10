package nz.ac.wgtn.swen225.lc.persistency.parse;

import nz.ac.wgtn.swen225.lc.domain.Monster;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;

import java.util.Optional;

/**
 * Encapsulates information about a particular parsed tile.
 * @param tile - the Tile
 * @param monster - an Optional<Monster>, non-empty if there happens to be a Monster on this tile position
 * @param <T> - the Tile type
 * @author Thomas Ru - 300658840
 */
public record ParsedTile<T extends Tile>(T tile, Optional<Monster> monster){
    /**
     * Create a ParsedTile with no monster on it
     * @param tile - the Tile
     */
    public ParsedTile(T tile) {
        this(tile, Optional.empty());
    }
}
