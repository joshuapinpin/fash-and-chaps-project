package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.controller.*;
import nz.ac.wgtn.swen225.lc.app.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.persistency.GameState;
import java.util.List;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 * Implements automatic replay of recorded game sessions for Level 1.
 * This class plays back recorded movements automatically with timing that matches
 * the original recording, scaled by a configurable speed factor.
 *
 * Implements the Singleton pattern to ensure only one autoplay instance exists.
 * Uses Swing Timer to schedule movements according to their recorded timing.</p>
 *
 * @author Arushi Bhatnagar Stewart
 * Student ID: 300664237
 */
public class AutoplayL1 implements Play{
    private static final AutoplayL1 playInstance = new AutoplayL1();
    private List<SaveL1.Moves> saveList;
    private final ObjectMapper mapper;
    private Timer autoplayTimer;
    private int pos;
    private static int speed;
    private int prevTimeLeft;
    SaveL1.FullGame fg;
    GameState initialState;
    /**
     * Private constructor to enforce Singleton pattern.
     * Initializes the replay state with default values.
     */
    private AutoplayL1(){
        saveList = new ArrayList<>();
        mapper = new ObjectMapper();
        pos = 0;
        speed = 1;
        prevTimeLeft = 0;
    }
    /**
     * Returns the singleton instance of AutoplayL1.
     *
     * @return the single AutoplayL1 instance
     */
    public static AutoplayL1 of() {
        return playInstance;
    }
    /**
     * Resets the autoplay state for a new replay session.
     * Clears all loaded recording data and stops any running autoplay timer.
     * Should be called when transitioning between levels or starting a new replay.
     */
    public void reset(){
        saveList = new ArrayList<>();
        speed = 1;
        prevTimeLeft = 0;
        fg = null;
        // Stop any running autoplay timer
        if (autoplayTimer != null) {
            autoplayTimer.stop();
            autoplayTimer = null;
        }
    }
    /**
     * Sets the playback speed multiplier for automatic replay.
     * Higher values result in faster playback.
     *
     * @param s the speed multiplier (must be positive)
     * @throws IllegalArgumentException if speed is negative
     */
    public void setSpeed(int s) {
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        if (s < 0) {
            throw new IllegalArgumentException("Speed must be between 1 and 6, got: " + s);
        }
        speed = s;
    }
    /**
     * Initiates automatic replay of a recorded game session.
     * Loads the recording from a file selected by the user, restores the initial
     * game state, and begins automatic playback.
     *
     * @param ac the application controller to apply movements to
     * @return true if replay started successfully, false if loading failed or was cancelled
     */
    public boolean play(AppController ac){
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        fg = getData(mapper);
        if(fg == null) return false; // stop play immediately
        saveList = fg.saveList();
        initialState = fg.state();
        if(saveList.isEmpty() || saveList == null || initialState == null) return false; // stop play immediately
        startState(initialState, ac);
        return autoPlay(ac);
    }
    /**
     * Begins automatic playback of the loaded recording.
     * Sets up the timer to replay movements according to their recorded timing.
     *
     * @param ac the application controller to apply movements to
     * @return true if autoplay started successfully, false if no moves to replay
     */
    private boolean autoPlay(AppController ac){
        if(saveList.isEmpty() || saveList == null) return false; // stop play immediately
        // Stop any existing autoplay
        if (autoplayTimer != null && autoplayTimer.isRunning()) {
            autoplayTimer.stop();
        }
        pos = 0;
        prevTimeLeft = 0;
        SaveL1.Moves iT = saveList.get(pos);
        int timeLeft = iT.timeLeftMilli();
        // Calculate delay for THIS move
        int timeDiff = Math.max(0, prevTimeLeft - timeLeft);
        int delay = (int) timeDiff/speed;
        autoplayTimer = new Timer(delay, e -> processNextMove(ac));
        autoplayTimer.setRepeats(false); // makes process iterative
        autoplayTimer.start();
        return true;
    }
    /**
     * Processes the next movement in the replay sequence.
     * Executes the current movement, updates timing information, and schedules
     * the next movement with appropriate delay.
     *
     * @param ac the application controller to apply the movement to
     */
    private void processNextMove(AppController ac) {
        if (pos >= saveList.size()) {
            return;
        }
        // Get and execute CURRENT move
        SaveL1.Moves iT = saveList.get(pos);
        Input dir = iT.direction();
        int timeLeft = iT.timeLeftMilli();
        // Execute THIS move
        ac.handleInput(dir);
        prevTimeLeft = timeLeft;
        pos++;
        // make sure pos is not out of bounds
        if (pos >= saveList.size()) {
            return;
        }
        // Calculate delay for NEXT move
        SaveL1.Moves nextMove = saveList.get(pos);
        int nextTimeLeft = nextMove.timeLeftMilli();
        int timeDiff = Math.max(0, prevTimeLeft - nextTimeLeft);
        int delay = (int) timeDiff/speed;
        // schedule delay for next move and restart the time
        autoplayTimer.setInitialDelay(delay);
        autoplayTimer.restart();
    }
}
