package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Direction;
import nz.ac.wgtn.swen225.lc.domain.Monster;
import nz.ac.wgtn.swen225.lc.domain.Position;

/**
 * Utility class to convert from a string representation of a crab to a crab (Monster) object.
 * @author Thomas Ru - 300658840
 */
public class MonsterParser {
    public static final String separator = EntityParsers.separator;
    public static final String name = "Crab";

    /**
     * Create a crab at a given position, moving a given direction.
     * @param monster - the String representation of a crab in forat Crab-DIRECTION.
     * @param position - where the crab is.
     * @return - the crab (Monster).
     */
    static Monster parseMonster(String monster, Position position) {
        String[] split = monster.split(separator);
        if (split.length != 2) {
            throw new IllegalArgumentException("Monster must be in format "+name+"-DIRECTION");
        }
        if (!split[0].equals(name)) {
            throw new IllegalArgumentException("Monster name not recognised: " + monster);
        }

        Monster monsterResult = Monster.of(position);
        // crabs move left by default, make it move right if needed
        Direction monsterDirection = Direction.valueOf(split[1]);
        if (!monsterResult.getDirection().equals(monsterDirection)) {
            monsterResult.updateDirection(); // reverse direction
        }
        return monsterResult;
    }
}
