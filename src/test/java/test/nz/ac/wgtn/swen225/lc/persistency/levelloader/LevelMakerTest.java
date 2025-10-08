package test.nz.ac.wgtn.swen225.lc.persistency.levelloader;

import nz.ac.wgtn.swen225.lc.persistency.serialisation.GameState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LevelMakerTest {
    @Test
    public void testCounts() {
        GameState level = new GameState(2, 4);
        String[][] board = {
                {"F:Key-ORANGE", "F:Key-GREEN", "F:Key-PURPLE", "F:Key-PINK"},
                {"F:Treasure", "~", "F", "F:Treasure"}
        };
        level.setBoard(board);
        assertEquals(2, level.treasureCount());
        assertEquals(4, level.keyCount());
    }
}
