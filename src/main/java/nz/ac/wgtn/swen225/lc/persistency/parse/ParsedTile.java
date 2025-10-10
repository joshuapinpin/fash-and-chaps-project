package nz.ac.wgtn.swen225.lc.persistency.parse;

import nz.ac.wgtn.swen225.lc.domain.Monster;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;

import java.util.Optional;

public record ParsedTile<T extends Tile>(T tile, Optional<Monster> monster){
    public ParsedTile(T tile) {
        this(tile, Optional.empty());
    }
}
