package nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.domain.Maze;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.function.Function;

/**
 * Level loading API, each level is a singleton.
 * @author Thomas Ru - 300658840
 */
public enum Levels {
    LevelOne(1),
    LevelTwo(2);

    private static final String defaultPath = "/levels/Level";
    private final int levelNumber;
    // add maxTime field here later for implementing timeout.

    static {
        // check no duplicate level numbers
        assert Arrays.stream(Levels.values()).distinct().count() == Levels.values().length;
    }

    /**
     * Given the level number, constructs a Level singleton.
     * @param i - the level number
     */
    Levels(int i) {
        assert i > 0 : "Level must be positive";
        levelNumber = i;
    }

    /**
     * Given a level number, loads the associated level from JSON file.
     * @return - the Maze instance loaded from file.
     */
    public Maze load() {
        return load(levelNumber, Levels::inputStreamToMaze);
    }

    /**
     * Utility method for testing.
     * Given a level number and way to map from InputStream to Maze,
     * loads the associated level from JSON file.
     * @param i - the level number.
     * @param mapper - the mapping function.
     * @return - the Maze instance loaded from file.
     */
    public static Maze load(int i, Function<InputStream, Maze> mapper) {
        assert mapper != null : "Mapper cannot be null";

        String location = defaultPath+i+".json";
        try (InputStream in = Levels.class.getResourceAsStream(location)) {
            if (in == null) {throw new IOException("Level "+i+" not found, "+location);}
            return mapper.apply(in);
        }
        catch (IOException e) {
            throw new Error("Level loading failed: Level "+i, e);
        }
    }

    /**
     * Takes a given InputStream from reading a JSON, returns the corresponding Maze instance.
     * @param in - the given InputStream.
     * @return - the associated Maze instance.
     */
    private static Maze inputStreamToMaze(InputStream in){
        try {
            LevelMaker level = new ObjectMapper().readValue(in, LevelMaker.class);
            return level.loadLevel();
        } catch (IOException e) {
            throw new Error("Deserialisation failed: "+e);
        }
    }
}
