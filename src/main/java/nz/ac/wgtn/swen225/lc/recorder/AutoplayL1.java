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
        pos = 0;
        prevTimeLeft = 0;
        // Stop any existing autoplay
        if (autoplayTimer != null && autoplayTimer.isRunning()) {
            autoplayTimer.stop();
        }
        autoplayTimer = new Timer(0, e -> processNextMove(ac));
        autoplayTimer.setRepeats(true); // makes process iterative
        autoplayTimer.start();
        return true;
    }
    /** */
    private void processNextMove(AppController ac) {
        if (pos >= saveList.size()) {
            autoplayTimer.stop();
            return;
        }
        SaveL1.inputTime iT = saveList.get(pos);
        Input dir = iT.direction();
        int timeLeft = iT.timeLeft();
        ac.handleInput(dir);
        int timeDiff = Math.max(0, prevTimeLeft - timeLeft);
        int delay = (timeDiff/speed) * 1000;
        prevTimeLeft = timeLeft;
        pos++;
        // Update delay for next iteration
        autoplayTimer.setDelay(delay);
    }

    /*public static void main(String[] args) {
        Play p = new Play();
        p.setSpeed(2);
        AppController x = AppController.of();
        p.stepByStep(x);
        p.stepByStep(x);
        p.stepByStep(x);
        p.stepByStep(x);
        p.autoPlay(x);
    } */
}
