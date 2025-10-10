package test.nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.persistency.GameState;
import nz.ac.wgtn.swen225.lc.persistency.LevelInfo;
import nz.ac.wgtn.swen225.lc.persistency.PlayerState;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

    public static final PlayerState playerState =
            new PlayerState(1, 1, 1, 2, "UP", List.of("GREEN"));

    private static GameState makeSampleGameState() {
        GameState state = new GameState(2, 4, 1, new LevelInfo(1, 4, 2), playerState);
        String[][] board = {
                {"F:Key-ORANGE", "F:Key-GREEN", "F:Key-PURPLE", "F:Key-PINK"},
                {"F:Treasure", "~", "F", "F:Treasure"}
        };
        state.setBoard(board);
        return state;
    }

    @Test
    public void testCounts() {
        GameState level = makeSampleGameState();
        assertEquals(2, level.maxTreasures());
        assertEquals(4, level.maxKeys());
    }

    @Test
    public void testEquals() {
        GameState a = makeSampleGameState();
        GameState b = makeSampleGameState();

        // reflexive
        assertEquals(a, a);

        // symmetric
        assertEquals(a, b);
        assertEquals(b, a);

        // transitive
        GameState c = makeSampleGameState();
        assertEquals(a, b);
        assertEquals(b, c);
        assertEquals(a, c);

        // consistent
        assertEquals(a, b);
        assertEquals(a, b);

        // null and type safety
        assertNotEquals(a, null);
        assertNotEquals(a, "some string");

        // different state should not be equal
        GameState different = new GameState(2, 4, 2, new LevelInfo(2, 4, 3), playerState);
        different.setBoard(new String[][]{
                {"F", "F", "F", "F"},
                {"F", "F", "F", "F"}
        });
        assertNotEquals(a, different);
    }

    @Test
    public void testHashCode() {
        GameState a = makeSampleGameState();
        GameState b = makeSampleGameState();

        // equal objects should have same hash code
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        // different objects should probably have different hash codes!
        GameState different = new GameState(2, 4, 2, new LevelInfo(2, 4, 3), playerState);
        different.setBoard(new String[][]{
                {"F", "F", "F", "F"},
                {"F", "F", "F", "F"}
        });
        assertNotEquals(a.hashCode(), different.hashCode());
    }

    @Test
    public void testToString() {
        GameState state = new GameState(
                5, // rows
                7, // cols
                120, // time
                new LevelInfo(2, 5, 3),
                playerState
        );

        String result = state.toString();

        assertNotNull(result, "toString() should not return null");
        assertFalse(result.isBlank(), "toString() should not be blank");

        assertTrue(result.contains("GameState"), "toString() should mention the class name");

        assertTrue(result.contains("rows") || result.contains("5"), "toString() should include rows info");
        assertTrue(result.contains("cols") || result.contains("7"), "toString() should include cols info");
        assertTrue(result.contains("time") || result.contains("120"), "toString() should include time info");
        assertTrue(result.contains("levelInfo"), "toString() should include LevelInfo");
        assertTrue(result.contains("player"), "toString() should include PlayerState");
    }

}
