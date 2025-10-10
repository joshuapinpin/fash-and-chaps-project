package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.controller.*;
import nz.ac.wgtn.swen225.lc.app.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.game.GameState;

import java.util.List;
import java.util.ArrayList;
import javax.swing.Timer;

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
    private AutoplayL1(){
        saveList = new ArrayList<>();
        mapper = new ObjectMapper();
        pos = 0;
        speed = 1;
        prevTimeLeft = 0;
    }
    /**
     * Factory method to return singleton Save instance
     * @return
     */
    public static AutoplayL1 of() {
        return playInstance;
    }
    /**
     * Resets autoplay and step-by-step play.
     * Used for resetting for Level 2.
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
    /** */
    public void setSpeed(int s) {
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        if (s < 0) {
            throw new IllegalArgumentException("Speed must be between 1 and 6, got: " + s);
        }
        speed = s;
    }

    /** main play method from interface */
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
    /** */
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
