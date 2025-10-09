package test.nz.ac.wgtn.swen225.lc.persistency.levelloader;

import nz.ac.wgtn.swen225.lc.persistency.serialisation.game.GameState;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.player.PlayerState;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameStateTest {
    public static PlayerState playerState = new PlayerState(1,1,1, 2, "UP", List.of("GREEN"));
    @Test
    public void testCounts() {
        GameState level = new GameState(2, 4, 1, 2, 60, playerState);
        String[][] board = {
                {"F:Key-ORANGE", "F:Key-GREEN", "F:Key-PURPLE", "F:Key-PINK"},
                {"F:Treasure", "~", "F", "F:Treasure"}
        };
        level.setBoard(board);
        assertEquals(2, level.maxTreasures());
        assertEquals(4, level.maxKeys());
    }
}
