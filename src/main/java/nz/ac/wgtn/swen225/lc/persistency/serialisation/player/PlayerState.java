package nz.ac.wgtn.swen225.lc.persistency.serialisation.player;

import java.util.List;

/**
 * Represents a JSON serialisable version of the player, with all the required context.
 * @param x - the x position
 * @param y - the y position
 * @param treasures - the treasures the player collected
 * @param maxTreasures - the max treasures in the level
 * @param direction - the direction the player is facing
 * @param keyColors - the color of all the keys the player collected
 */
public record PlayerState(
        int x,
        int y,
        int treasures,
        int maxTreasures,
        String direction,
        List<String> keyColors
) { }
