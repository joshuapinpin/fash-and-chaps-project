package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Monster;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.Free;

import java.util.Optional;

/**
 * Parses a String representation into a Free Tile, if possible.
 * @author Thomas Ru - 300658840
 */
class FreeParser extends TileParser<Free> {
    /**
     * Creates a new parser.
     * @param symbol - the String symbolic representation of a Free tile, e.g. 'F'
     */
    public FreeParser(String symbol) {
        super(symbol, Free::of);
    }
    String formatMessage = "Expected format F:Entity-COLOR:Monster-DIRECTION, where entity and monster are optional.";

    /**
     * Parse some String into a Free Tile.
     * @param surroundings - the LevelMaker context for the TileParser strategy, has the whole gameboard.
     * @param tile - the String which is supposed to represent a Tile.
     * @param position - the Position of the Tile.
     * @return - the ParsedTile<Free> data
     */
    @Override // override parse for additional parsing of Entities on Free tiles
    public ParsedTile<Free> parse(GameState surroundings, String tile, Position position) {
        checkNonNull(surroundings, tile, position);
        String[] split = tile.split(TileParsers.separator);

        // preconditions
        if (split.length == 0 || !split[0].equals(symbol())) {
            throw new IllegalArgumentException("Cannot parse into a Free tile: '" + tile + "'");
        }
        if (split.length > 3) {
            throw new IllegalArgumentException(formatMessage + " Received: " + tile);
        }

        // check parse if possible
        Free free = super.parse(surroundings, tile, position).tile();
        Optional<Monster> monster = Optional.empty();
        for (int i = 1; i < split.length; i++) {
            monster = handleToken(split[i], surroundings, free, position);
            if (monster.isPresent() && i != split.length - 1) {
                throw new IllegalArgumentException(formatMessage + " Received: " + tile);
            }
        }
        return new ParsedTile<>(free, monster);
    }

    /**
     * Given a free tile string's auxiliary token, set the Tile's Entity if it has one
     * and return Optional<Monster> depending on whether there's a crab on the tile
     * @param token - an auxiliary token (i.e. following 'F') of the free tile representation
     * @param surroundings - the GameState surroundings
     * @param free - the Free tile
     * @param position - the position of the tile
     * @return - Optional<Monster>, empty if there is no monster at the tile's position
     */
    private Optional<Monster> handleToken(String token, GameState surroundings, Free free, Position position) {
        if (!EntityParsers.hasEntityName(token)) {
            return Optional.of(MonsterParser.parseMonster(token, position));
        }
        free.setCollectable(EntityParsers.parseEntity(surroundings, token));
        return Optional.empty();
    }
}
