package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;
import nz.ac.wgtn.swen225.lc.domain.*;

import java.util.Map;
import java.util.function.Supplier;

public class LevelMaker {
    private static Map<String, Supplier<Tile>> legend;
    static {
        legend = Map.of(); //TODO, e.g. {" ", Free::new, "?", Info::new ...}
    }
    private final String[][] board;

    /**
     * Create LevelMaker object from ASCII art type array representing the board.
     * Useful for testing, but in-game LevelMaker instances will be constructed
     * directly from JSON.
     * @param board - the 2D String array representing each tile with symbol.
     */
    public LevelMaker(String[][] board) {
        this.board = board;
    }

    /**
     * Alter the mappings between String symbols and Tiles.
     * @param legend - map representing the legend.
     */
    public static void map(Map<String, Supplier<Tile>> legend) {
        LevelMaker.legend = legend;
    }

    /**
     * Gives the Maze object corresponding to the tiles and entities listed
     * in the board.
     * @return - the Maze object.
     */
    public Maze loadLevel() {
        throw new RuntimeException("To do");
    }
}
