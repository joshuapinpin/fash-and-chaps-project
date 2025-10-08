package nz.ac.wgtn.swen225.lc.persistency.parse;

import nz.ac.wgtn.swen225.lc.domain.Monster;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.tiles.Free;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.GameState;

import java.util.Arrays;
import java.util.List;

import static nz.ac.wgtn.swen225.lc.persistency.parse.TileParsers.MaxMonstersOnTile;

/**
 * Parses a String representation into a Free Tile, if possible.
 * @author Thomas Ru - 300658840
 */
// TODO: DEFINITELY some major refactoring of multiple classes needed, the code below is just a temporary symptom but it works for now
class FreeParser extends TileParser<Free> {
    /**
     * Creates a new parser.
     * @param symbol - the String symbolic representation of a Free tile, e.g. 'F'
     */
    public FreeParser(String symbol) {
        super(symbol, Free::of);
    }

    /**
     * Parse some String into a Free Tile.
     * @param surroundings - the LevelMaker context for the TileParser strategy, has the whole gameboard.
     * @param tile - the String which is supposed to represent a Tile.
     * @param position - the Position of the Tile.
     * @return - the Free Tile if possible, otherwise an IllegalArgumentException is thrown.
     */
    @Override // override parse for additional parsing of Entities on Free tiles
    public Free parse(GameState surroundings, String tile, Position position) {
        checkNonNull(surroundings, tile, position);
        String[] split = tile.split(separator);
        if (split.length==0 || !split[0].equals(symbol())) {
            throw new IllegalArgumentException("Cannot parse into a Free tile: '"+tile+"'");
        }

        Free free = super.parse(surroundings, tile, position); // Free tile with no Entity on it

        // find entity, if present
        List<String> entities = Arrays.stream(split)
                .skip(1)
                .filter(EntityParsers::hasEntityName)
                .toList();

        // find monsters, if present (e.g. crab)
        List<String> monsters = Arrays.stream(split)
                .skip(1)
                .filter(s -> !EntityParsers.hasEntityName(s))
                .limit(MaxMonstersOnTile)
                .toList();

        if (entities.size() > 1) {
            throw new IllegalArgumentException("Tiles can only have one entity: '"+tile+"'");
        }

        // parse entity (if any)
        entities.stream()
                .findFirst()
                .map(s -> EntityParsers.parseEntity(surroundings, s))
                .ifPresent(free::setCollectable);

        // parse monsters (if any)
        monsters.forEach(s -> surroundings.setMonster(Monster.of(position))); // TODO: make this more robust!

        return free;
    }
}
