package nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.domain.Maze;

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
    LevelOne(1, 60, new GameMapper()),
    LevelTwo(2, 60, new GameMapper());

    private static final String defaultPath = "/levels/Level";
    private static final String extension = ".json";

    private final GameMapper mapper;
    private final int levelNumber;
    private final int maxTime;
    private final GameState gameState;
    private final LevelInfo levelInfo;


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
     * @param mapper - used to map from GameState to Maze.
     */
    Levels(int levelNumber, int maxTime, GameMapper mapper) {
        if (levelNumber <= 0) { throw new  IllegalArgumentException("Level number must be positive."); }
        if (maxTime <= 0) { throw new  IllegalArgumentException("Level timeout must be positive."); }
        this.levelNumber = levelNumber;
        this.maxTime = maxTime;
        this.mapper = mapper;

        gameState = load(levelNumber, this::loadState);
        levelInfo = gameState.getLevelInfo();
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
        return mapper.fromState(gameState).maze();
    }

    /**
     * Utility method, useful for testing.
     * Given a level number and way to map from an InputStream to a GameState,
     * loads the associated level from JSON file.
     * @param i - the level number.
     * @param mapper - the mapping function.
     * @return - the GameState instance loaded from file.
     */
    public static GameState load(int i, Function<InputStream, GameState> mapper) {
        if (mapper == null) { throw new IllegalArgumentException("Mapper cannot be null."); }

        String location = defaultPath+i+extension;
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
     * Takes a given InputStream from reading a JSON, returns the corresponding GameState instance.
     * @param in - the given InputStream.
     * @return - the GameState read from file.
     */
    private GameState loadState(InputStream in){
        try {
            return new ObjectMapper().readValue(in, GameState.class);
        } catch (IOException e) {
            throw new Error("Deserialisation failed: "+e);
        }
    }

    /**
     * Getter for the level number
     * @return - the level number
     */
    public int levelNumber() { return levelNumber; }

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
        return levelInfo.maxKeys();
    }

    /**
     * Getter for the number of treasures on the map.
     * @return the integer no. of treasures.
     */
    public int maxTreasures() {
        return levelInfo.maxTreasures();
    }
}
