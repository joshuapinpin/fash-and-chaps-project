package nz.ac.wgtn.swen225.lc.persistency;

/**
 * Represents metadata about a level of the game.
 * @param levelNumber - the level number.
 * @param maxKeys - the maximum number of keys.
 * @param maxTreasures - the maximum number of treasures.
 * @author Thomas Ru - 300658840
 */
public record LevelInfo(int levelNumber, int maxKeys, int maxTreasures) { }
