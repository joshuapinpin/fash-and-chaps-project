package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Maze;

/**
 * Represents a maze and its metadata that have been loaded from file, e.g.
 * to re-initialise the game domain.
 * @param maze - the game board Maze.
 * @param time - the time remaining to complete the level.
 * @param levelInfo - the level metadata.
 * @author Thomas Ru - 300658840
 */
public record LoadedMaze(Maze maze, int time, LevelInfo levelInfo) { }
