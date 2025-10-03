package test.nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.tiles.Free;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.persistency.levelloader.Levels;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

record TestData(int number){}

// TODO: move Level67.json to test/resources
public class LevelsTest {
    private static Maze mapper(InputStream in){
        try {
            TestData level = new ObjectMapper().readValue(in, TestData.class);
            return new Maze(level.number(), level.number());
        } catch (IOException e) {
            throw new Error("Deserialisation failed: "+e);
        }
    }

    @Test
    public void testValid() {
        Maze result = Levels.load(67, LevelsTest::mapper);
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
        Error err = assertThrows(AssertionError.class, ()->{
            Levels.load(67, null);
        });
    }
}
