package test.nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.persistency.LevelMaker;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.persistency.Levels;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertEquals(new Maze(67, 67), result); // RELIES ON MAZE EQUALS TO PASS
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

record TestData(int number){}
