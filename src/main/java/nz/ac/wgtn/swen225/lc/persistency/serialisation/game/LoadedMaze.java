package nz.ac.wgtn.swen225.lc.persistency.serialisation.game;

import nz.ac.wgtn.swen225.lc.domain.Maze;

public record LoadedMaze(Maze maze, int time, LevelInfo levelInfo) { }
