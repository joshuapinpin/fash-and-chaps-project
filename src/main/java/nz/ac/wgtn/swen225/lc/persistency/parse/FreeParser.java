package nz.ac.wgtn.swen225.lc.persistency.parse;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.tiles.Free;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.game.GameState;

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
    String formatMessage = "Expected format F:Entity-COLOR:Monster-DIRECTION, where entity and monster are optional.";

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
        String[] split = tile.split(TileParsers.separator);
        if (split.length==0 || !split[0].equals(symbol())) {
            throw new IllegalArgumentException("Cannot parse into a Free tile: '"+tile+"'");
        }
        if (split.length > 3) {
            throw new IllegalArgumentException(formatMessage+" Received: "+tile);
        }

        Free free = super.parse(surroundings, tile, position); // Free tile with no Entity on it

        return switch(split.length) {
            case 1 -> free;
            case 2 -> {
                if(EntityParsers.hasEntityName(split[1])) {
                    free.setCollectable(EntityParsers.parseEntity(surroundings, split[1]));
                }
                else {
                    surroundings.setMonster(MonsterParser.parseMonster(split[1], position));
                }
                yield free;
            }
            case 3 -> {
                free.setCollectable(EntityParsers.parseEntity(surroundings, split[1]));
                surroundings.setMonster(MonsterParser.parseMonster(split[2], position));
                yield free;
            }
            default -> {
                throw new  IllegalArgumentException(formatMessage+" Received: "+tile);
            }
        };
    }
}
