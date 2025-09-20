package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Maze;

import java.nio.file.Path;

/**
 * Level loading API, each level is a singleton
 */
public enum Levels {
    LevelOne(1),
    LevelTwo(2);

    private final Maze gameBoard;

    Levels(int i) {
        gameBoard = loadLevel(i);
    }

    static Path startPath() {
        throw new RuntimeException("To do");
    }

    private static Maze loadLevel(int i) {
        throw new  RuntimeException("To do");
    }

    public Maze game() {
        // note: clone gameBoard before returning, Maze is mutable!
        throw new  RuntimeException("To do");
    }
}
