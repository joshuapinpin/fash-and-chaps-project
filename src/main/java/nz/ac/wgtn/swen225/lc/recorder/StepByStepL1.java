package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.controller.*;
import nz.ac.wgtn.swen225.lc.app.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.game.GameState;

import java.util.List;
import java.util.ArrayList;
/**
 * Implements step-by-step replay of recorded game sessions for Level 1.
 * This class allows manual advancement through recorded movements one step at a time,
 * useful for detailed analysis or debugging of gameplay.
 *
 * <p>Implements the Singleton pattern to ensure only one step-by-step instance exists.
 * Each call to play() advances the replay by one movement.</p>
 *
 * @author Arushi Bhatnagar Stewart
 * Student ID: 300664237
 */
public class StepByStepL1 implements Play{
    private static final StepByStepL1 playInstance = new StepByStepL1();
    private static int pos; // count for step by step playing
    private List<SaveL1.Moves> saveList;
    private final ObjectMapper mapper;
    SaveL1.FullGame fg;
    GameState initialState;
    /**
     * Private constructor to enforce Singleton pattern.
     * Initializes the replay state with default values.
     */
    private StepByStepL1(){
        saveList = new ArrayList<>();
        mapper = new ObjectMapper();
        pos = 0;
    }
    /**
     * Returns the singleton instance of StepByStepL1.
     *
     * @return the single StepByStepL1 instance
     */
    public static StepByStepL1 of() {
        return playInstance;
    }
    /**
     * Resets the step-by-step replay state for a new replay session.
     * Clears all loaded recording data and resets the position counter.
     * Should be called when transitioning between levels or starting a new replay.
     */
    public void reset(){
        saveList = new ArrayList<>();
        pos = 0;
    }
    /**
     * Sets the playback speed for replay.
     * This implementation does nothing as step-by-step replay is manually controlled.
     *
     * @param s the speed parameter (ignored)
     */
    public void setSpeed(int s){
        // dont need to set speed for step-by-step
    }
    /**
     * Advances the replay by one movement or initializes the replay on first call.
     * On the first call, loads the recording and restores the initial game state.
     * Subsequent calls advance through the movements one at a time.
     *
     * @param ac the application controller to apply the movement to
     * @return true if a movement was executed successfully, false if replay is complete or failed
     */
    public boolean play(AppController ac){
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        // Get data only in the first iteration
        if(pos == 0){
            fg = getData(mapper);
            saveList = fg.saveList();
            initialState = fg.state();
            startState(initialState, ac);
        }
        if(saveList == null || saveList.isEmpty() || initialState == null) return false; // stop play immediately
        return stepByStep(ac);
    }
    /**
     * Executes a single step in the replay sequence.
     * Retrieves and applies the next movement from the recording, then advances
     * the position counter. Resets when the end of the recording is reached.
     *
     * @param ac the application controller to apply the movement to
     * @return true if a movement was executed, false if the recording is complete or empty
     */
    private boolean stepByStep(AppController ac) {
        // make the method return true while we still have positions to go
        if(pos == saveList.size()){pos = 0; return false;}
        if(saveList.isEmpty()) return false; // stop play immediately
        SaveL1.Moves iT = saveList.get(pos);
        Input direction = iT.direction();
        // pass direction to app method
        ac.handleInput(direction);
        System.out.println("step-by-step position: " + pos);
        System.out.println("step-by-step direction: " + direction);
        pos++;
        return true;
    }
}
