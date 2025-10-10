package test.nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.Free;
import nz.ac.wgtn.swen225.lc.persistency.GameState;
import nz.ac.wgtn.swen225.lc.persistency.LevelInfo;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.persistency.Levels;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

record TestData(int number){}

public class LevelsTest {
    private static GameState mapper(InputStream in){
        try {
            TestData level = new ObjectMapper().readValue(in, TestData.class);
            int n = level.number();
            return new GameState(n, n, n,
                    new LevelInfo(1, 1, 1),
                    GameStateTest.playerState
            );
        } catch (IOException e) {
            throw new Error("Deserialisation failed: "+e);
        }
    }

    @Test
    public void testPath() {
        assertEquals("/levels/Level", Levels.defaultPath());
    }

    @Test
    public void testValid() {
        GameState state = Levels.load(67, LevelsTest::mapper);
        Maze result = new Maze(state.getRows(), state.getCols());
        for (int y=0; y<result.getRows(); y++) {
            for (int x=0; x<result.getCols(); x++) {
                result.setTileAt(Free.of(new Position(x, y)));
            }
        }
        String expected =
                        "F F F F F F \n"+
                        "F F F F F F \n"+
                        "F F F F F F \n"+
                        "F F F P F F \n"+
                        "F F F F F F \n"+
                        "F F F F F F \n";
        assertEquals(expected, result.toString());
    }

    @Test
    public void testInvalidFile() {
        Error err = assertThrows(Error.class, ()->{
            Levels.load(999, LevelsTest::mapper);
        });
        assertEquals("Level loading failed: Level 999",  err.getMessage());
    }

    @Test
    public void testMapperReturnsNull() {
        Error err = assertThrows(Error.class, ()->{
            Levels.load(67, in->null);
        });
        assertEquals("Level deserialised to null: Level 67",  err.getMessage());
    }

    @Test
    public void testMapperFailure() {
        Error err = assertThrows(Error.class, ()->{
            Levels.load(67, in->{
                throw new Error("Map failure");
            });
        });
        assertEquals("Map failure", err.getMessage());
    }

    @Test
    public void testMapperNull() {
        Exception e = assertThrows(IllegalArgumentException.class, ()->{
            Levels.load(67, null);
        });
        assertEquals("Mapper cannot be null.", e.getMessage());
    }

    @Test
    public void testMaxTime() {
        assertEquals(60, Levels.LevelOne.maxTime());
        assertEquals(60, Levels.LevelTwo.maxTime());
    }

    @Test
    public void testMaxKeys() {
        assertEquals(2, Levels.LevelOne.maxKeys());
        // TODO: add LevelTwo check
    }

    @Test
    public void textMaxTreasures() {
        assertEquals(4, Levels.LevelOne.maxTreasures());
        // TODO: add LevelTwo check
    }
}
