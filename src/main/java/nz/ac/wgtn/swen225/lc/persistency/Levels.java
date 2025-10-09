package nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.GameMapper;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.GameState;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Level loading API, each level is a singleton.
 * @author Thomas Ru - 300658840
 */
public enum Levels {
    LevelOne(1, 60),
    LevelTwo(2, 60);

    private static final String defaultPath = "/levels/Level";
    private static final GameMapper mapper = new GameMapper();

    private boolean loaded = false;
    private final int levelNumber;
    private final int maxTime;
    private int maxKeys;
    private int maxTreasures;


    static {
        // check no duplicate level numbers
        boolean unique = Arrays.stream(Levels.values())
                .map(level->level.levelNumber)
                .distinct()
                .count()
                == Levels.values().length;
        if (!unique) {
            String levelNumbers = Arrays.stream(Levels.values())
                    .map(level->String.valueOf(level.levelNumber))
                    .collect(Collectors.joining(", ", "[", "]"));
            throw new AssertionError("Level numbers must be unique: "+levelNumbers);
        }
    }

    /**
     * Given the level number, constructs a Level singleton.
     * @param levelNumber - the level number.
     * @param maxTime - the maximum level duration in pings.
     */
    Levels(int levelNumber, int maxTime) {
        if (levelNumber <= 0) { throw new  IllegalArgumentException("Level number must be positive."); }
        if (maxTime <= 0) { throw new  IllegalArgumentException("Level timeout must be positive."); }
        this.levelNumber = levelNumber;
        this.maxTime = maxTime;
    }

    /**
     * Gets the default path where levels are stored.
     * @return - the default path as a String.
     */
    public static String defaultPath() {
        return defaultPath;
    }

    /**
     * Given a level number, loads the associated level from JSON file.
     * @return - the Maze instance loaded from file.
     */
    public Maze load() {
        System.out.println("*DEBUG* Inside of the Persistency Package Now");
        // note: level loading sets maxKeys and maxTreasures
        Maze level = load(levelNumber, this::createMaze);
        loaded = true;
        return level;
    }

    /**
     * Utility method, useful for testing.
     * Given a level number and way to map from an InputStream to a LevelMaker,
     * loads the associated level from JSON file.
     * @param i - the level number.
     * @param mapper - the mapping function.
     * @return - the Maze instance loaded from file.
     */
    public static Maze load(int i, Function<InputStream, Maze> mapper) {
        if (mapper == null) { throw new IllegalArgumentException("Mapper cannot be null."); }

        String location = defaultPath+i+".json";
        try (InputStream in = Levels.class.getResourceAsStream(location)) {
            if (in == null) {throw new IOException("Level "+i+" not found, "+location);}
            return Objects.requireNonNull(mapper.apply(in), "Level JSON deserialised to null.");
        }
        catch (IOException e) {
            throw new Error("Level loading failed: Level "+i, e);
        }
        catch (NullPointerException e) {
            throw new Error("Level deserialised to null: Level "+i, e);
        }
    }

    /**
     * Takes a given InputStream from reading a JSON, returns the corresponding Maze instance.
     * Also sets the maxKeys and maxTreasures fields using the information from the Maze.
     * @param in - the given InputStream.
     * @return - the associated Maze instance.
     */
    private Maze createMaze(InputStream in){
        try {
            GameState gameState = new ObjectMapper().readValue(in, GameState.class);
            Maze maze = mapper.fromState(gameState).maze();
            maxKeys = gameState.keyCount();
            maxTreasures = gameState.treasureCount();
            return maze;
        } catch (IOException e) {
            throw new Error("Deserialisation failed: "+e);
        }
    }

    /**
     * Getter for the maximum level duration.
     * @return - max. level duration in pings.
     */
    public int maxTime() { return maxTime; }

    /**
     * Getter for the number of keys which on the map.
     * @return - the integer no. of keys.
     */
    public int maxKeys() {
        if (!loaded) { load(); }
        return maxKeys;
    }

    /**
     * Getter for the number of treasures on the map.
     * @return the integer no. of treasures.
     */
    public int maxTreasures() {
        if (!loaded) { load(); }
        return maxTreasures;
    }

    public int levelNumber() { return levelNumber; }
}
