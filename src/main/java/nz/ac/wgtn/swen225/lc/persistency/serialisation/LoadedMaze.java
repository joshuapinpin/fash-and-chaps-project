package nz.ac.wgtn.swen225.lc.persistency.serialisation;

import nz.ac.wgtn.swen225.lc.domain.Maze;

public record LoadedMaze(Maze maze, int levelNumber, int maxTreasure, int time) {}
