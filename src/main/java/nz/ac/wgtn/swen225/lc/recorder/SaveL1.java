package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.util.*;
import nz.ac.wgtn.swen225.lc.app.controller.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.game.GameMapper;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.game.GameState;
import java.io.File;
import java.io.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Manages the recording of player movements and game state for Level 1.
 * This class coordinates with the App package to capture each character movement
 * and saves the complete game session to a JSON file.
 *
 * Implements the Singleton pattern to ensure only one recorder instance exists.
 * The recorder captures both the movement inputs and timing information for replay.</p>
 *
 * @author Arushi Bhatnagar Stewart
 * Student ID: 300664237
 */
public class SaveL1 implements Save{
    /**
     * Represents a single movement with its direction and timestamp.
     *
     * @param direction the input direction for this move
     * @param timeLeftMilli the time remaining in milliseconds when this move was made
     */
    public record Moves(Input direction, Integer timeLeftMilli){}
    /**
     * Encapsulates a complete game recording including all moves and initial game state.
     *
     * @param saveList the list of all recorded moves
     * @param state the initial game state at the start of recording
     */
    public record FullGame(List<Moves> saveList, GameState state){};
    private static final SaveL1 saveInstance = new SaveL1();
    private List<Moves> saveList;
    private final ObjectMapper mapper;
    private GameState gs;
    /**
     * Private constructor to enforce Singleton pattern.
     * Initializes the save list and JSON mapper.
     */
    private SaveL1(){
        saveList = new ArrayList<>();
        mapper = new ObjectMapper();
    }
    /**
     * Returns the singleton instance of SaveL1.
     *
     * @return the single SaveL1 instance
     */
    public static SaveL1 of() {
        return saveInstance;
    }
    /**
     * Resets the recorder by clearing all recorded movements.
     * This should be called when switching between levels.
     */
    public void reset(){
        saveList = new ArrayList<>();
    }
    /**
     * Initializes the recorder with the current game state.
     * Captures the initial maze configuration, level number, treasures, keys, and time
     * to enable accurate replay from the beginning.
     *
     * @param ac the application controller containing the current game state
     */
    public void startRecorder(AppController ac){
        Maze maze = ac.domain();
        int levelNumber = ac.level();
        int maxTreasures = ac.persistencyController().maxTreasures();
        int maxKeys = ac.persistencyController().maxKeys();
        int timeLeft = ac.timerController().getTimeLeft();
        gs = new GameMapper().toState(maze, levelNumber, maxTreasures, maxKeys, timeLeft);
    }
    /**
     * Updates the recording with a new movement or saves the recording to file.
     *
     * @param direction the input direction for the movement
     * @param ac the application controller
     * @param toSave if true, saves the recording to file; if false, adds the movement to the recording
     */
    public void updateMovement(Input direction, AppController ac, boolean toSave){
        if(toSave){
            saveToFile(ac);
            return;
        }
        addMovement(direction, ac);
    }
    /**
     * Adds a movement to the recording with its timestamp.
     *
     * @param direction the input direction for the movement
     * @param ac the application controller for retrieving timing information
     */
    private void addMovement(Input direction, AppController ac) {
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        int timeLeft = ac.timerController().getPreciseTimeMillis();
        saveList.add(new Moves(direction, timeLeft));
    }
    /**
     * Saves the complete recording to a JSON file.
     * Prompts the user to choose a save location and writes the recording
     * including all movements and initial game state.
     *
     * @param ac the application controller
     */
    private void saveToFile(AppController ac){
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        File playerMovements = chooseFile();
        if (playerMovements == null) {
            return;
        }
        FullGame fg = new FullGame(saveList, gs);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(playerMovements, fg);
        } catch(IOException e){
            JOptionPane.showMessageDialog(null, "Failed to save file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
