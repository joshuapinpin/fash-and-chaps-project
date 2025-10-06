package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.controller.*;
import nz.ac.wgtn.swen225.lc.app.state.AutoReplayState;
import nz.ac.wgtn.swen225.lc.app.state.PausedState;
import nz.ac.wgtn.swen225.lc.app.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Initial outline for class to replay the actions of the character.
 *
 * @author Arushi Bhatnagar Stewart
 */

// use game controller handle input?
public class Play {
    private static final Play playInstance = new Play();
    private static int speed;
    private static int pos; // count for step by step playing
    private List<Save.inputTime> saveList;
    private final ObjectMapper mapper;
    private Play(){
        saveList = new ArrayList<>();
        mapper = new ObjectMapper();
        pos = 0;
        speed = 1;
    }
    /**
     * Factory method to return singleton Save instance
     * @return
     */
    public static Play of() {
        return playInstance;
    }
    /**
     * Resets autoplay and step-by-step play.
     * Used for resetting for Level 2.
     */
    public void reset(){
        saveList = new ArrayList<>();
        pos = 0;
        speed = 1;
    }
    /**
     *
     */
    private File getFile(){
        File fileChoice = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open JSON File");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JSON files", "json"));
        // specifically for opening file
        int userChoice = fileChooser.showOpenDialog(null);
        if (userChoice == JFileChooser.APPROVE_OPTION) {
            fileChoice = fileChooser.getSelectedFile();
        }
        assert fileChoice != null: "File not chosen";
        return fileChoice;
    }
    /**
     * This methods reads the list of saveMap from the json file
     * and assigns it to our movement arraylist field.
     */
    private List<Save.inputTime> getData() {
        /*
        using new TypeReference<List<MyObject>>() {} to create
        an anonymous subclass of TypeReference,
        it carries the actual generic type (List<Input>)
        in its class signature. Can't do List.class.
         */
        File myFile = getFile();
        try {
            saveList = mapper.readValue(myFile, new TypeReference<List<Save.inputTime>>() {
            });
        } catch (IOException e) {
            // rethrows checked exception as error
            throw new Error(e);
        }
        return saveList;
    }

    public void setSpeed(int s) {
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        // speed needs to be 1-6
        assert s > 0 : "Speed must me greater than zero";
        speed = s;
    }

    private void startPlay(){
        getData();
        // sets the game to position at start of recording
    }
    private void stopPlay(){
        // sets the game to position at the end of recording
    }

    /**
     * Very basic implementation of step by step. Reads one input
     * from the list, everytime method is called.
     */
    public boolean stepByStep(AppController ac) {
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        // Get data only in the first iteration
        if(pos == 0) getData();
        // make the method return true while we still have positions to go
        if(pos == saveList.size()){pos = 0; return false;}
        if(saveList.isEmpty()) throw new IllegalArgumentException("Character has not moved yet");
        Save.inputTime iT = saveList.get(pos);
        Input direction = iT.direction();
        // pass direction to app method
        ac.handleInput(direction);
        System.out.println("step-by-step position: " + pos);
        System.out.println("step-by-step direction: " + direction);
        pos++;
        return true;
    }

    /**
     * Very basic implementation. In one iteration, reads all the frames
     * Currently, doesn't implement speed.
     */
    public void autoPlay(int pos, int prevTimeLeft, AppController ac) {
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        if (saveList.isEmpty()) throw new IllegalArgumentException("Character has not moved yet");
        Save.inputTime iT = saveList.get(pos);
        final Input direction = iT.direction();
        final int timeLeft = iT.timeLeft();
        int timeDiff = prevTimeLeft - timeLeft;
        if (timeDiff < 0) timeDiff = 0;
        // Adjust playback speed
        int delay = Math.max(1, timeDiff/speed);
        System.out.println("Waiting for " + delay + " seconds...");
        try {
            TimeUnit.SECONDS.sleep(delay);
        } catch (InterruptedException e) {
            System.err.println("Autoplay interrupted");
        }
        // Schedule the move on the Swing thread
        ac.handleInput(direction);
        saveList.remove(pos);
        autoPlay(pos++, timeLeft, ac);
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

