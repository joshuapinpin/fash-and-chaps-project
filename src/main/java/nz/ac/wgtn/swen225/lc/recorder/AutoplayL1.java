package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.controller.*;
import nz.ac.wgtn.swen225.lc.app.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.Timer;

public class AutoplayL1 implements Play{
    private static final AutoplayL1 playInstance = new AutoplayL1();
    private static int speed;
    private List<SaveL1.inputTime> saveList;
    private final ObjectMapper mapper;
    private Timer autoplayTimer;
    private int pos;
    private int prevTimeLeft;
    private AutoplayL1(){
        saveList = new ArrayList<>();
        mapper = new ObjectMapper();
        speed = 1;
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
    }
    /** */
    public void setSpeed(int s) {
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        // speed needs to be 1-6
        assert s > 0 : "Speed must me greater than zero";
        speed = s;
    }
    /**
     * This methods reads the list of saveMap from the json file
     * and assigns it to our movement arraylist field.
     */
    private List<SaveL1.inputTime> getData() {
        /*
        using new TypeReference<List<MyObject>>() {} to create
        an anonymous subclass of TypeReference,
        it carries the actual generic type (List<Input>)
        in its class signature. Can't do List.class.
         */
        File myFile = getFile();
        try {
            saveList = mapper.readValue(myFile, new TypeReference<List<SaveL1.inputTime>>() {
            });
        } catch (IOException e) {
            // rethrows checked exception as error
            throw new Error(e);
        }
        return saveList;
    }
    /** main play method from interface */
    public boolean play(AppController ac){
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        getData();
        return autoPlay(ac);
    }
    /**
     */
    public boolean autoPlay(AppController ac){
        if (saveList.isEmpty()) throw new IllegalArgumentException("Character has not moved yet");
        // Stop any existing autoplay
        if (autoplayTimer != null && autoplayTimer.isRunning()) {
            autoplayTimer.stop();
        }
        pos = 0;
        prevTimeLeft = 0;
        SaveL1.inputTime iT = saveList.get(pos);
        Input dir = iT.direction();
        int timeLeft = iT.timeLeft();
        // Calculate delay for THIS move
        int timeDiff = Math.max(0, prevTimeLeft - timeLeft);
        int delay = (timeDiff * 1000) / speed;
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
        SaveL1.inputTime iT = saveList.get(pos);
        Input dir = iT.direction();
        int timeLeft = iT.timeLeft();
        // Execute THIS move
        ac.handleInput(dir);
        prevTimeLeft = timeLeft;
        pos++;
        // Calculate delay for NEXT move
        SaveL1.inputTime nextMove = saveList.get(pos);
        int nextTimeLeft = nextMove.timeLeft();
        int timeDiff = Math.max(1, prevTimeLeft - nextTimeLeft);
        int delay = (timeDiff * 1000) / speed;
        // schedule next move
        autoplayTimer.setInitialDelay(delay);
        autoplayTimer.restart();
    }
}
