package nz.ac.wgtn.swen225.lc.persistency.parse;

import nz.ac.wgtn.swen225.lc.domain.Monster;
import nz.ac.wgtn.swen225.lc.domain.Position;

/**
 * Utility class to convert from a string representation of a crab to a crab (Monster) object.
 */
public class MonsterParser {
    public static final String separator = EntityParsers.separator;

    /**
     * Create a crab at a given position, moving a given direction.
     * @param monster - the String representation of a crab in forat Crab-DIRECTION.
     * @param position - where the crab is.
     * @return - the crab (Monster).
     */
    static Monster parseMonster(String monster, Position position) {
        String[] split = monster.split(separator);
        if (split.length != 2) {
            throw new IllegalArgumentException("Monster must be in format Crab-DIRECTION");
        }
        if (!split[0].equals("Crab")) {
            throw new IllegalArgumentException("Monster type not supported: " + monster);
        }

        Monster result = Monster.of(position);
        // crabs move left by default, make it move right if needed
        if (!result.getDirection().name().equals(split[1])) {
            result.updateDirection();
        }
        return result;
    }
}
