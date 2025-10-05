package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.controller.*;
import nz.ac.wgtn.swen225.lc.app.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private Map<Save.MovementState, Integer> saveMap;
    private final ObjectMapper mapper;
    private Play(){
        saveMap = new HashMap<>();
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
        saveMap = new HashMap<>();
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
    private Map<Save.MovementState, Integer> getData() {
        /*
        using new TypeReference<List<MyObject>>() {} to create
        an anonymous subclass of TypeReference,
        it carries the actual generic type (List<Input>)
        in its class signature. Can't do List.class.
         */
        File myFile = getFile();
        try {
            saveMap = mapper.readValue(myFile, new TypeReference<Map<Save.MovementState, Integer>>() {
            });
        } catch (IOException e) {
            // rethrows checked exception as error
            throw new Error(e);
        }
        return saveMap;
    }

    public void setSpeed(int s) {
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        // speed needs to be 1-6
        assert s > 0 : "Speed must me greater than zero";
        speed = s;
    }

    /**
     * Very basic implementation of step by step. Reads one input
     * from the list, everytime method is called.
     */
    public boolean stepByStep(AppController ac) {
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        // call in case data has changed
        getData();
        // make the method return true while we still have positions to go
        if(pos == saveMap.size()){pos = 0; return false;}
        if (saveMap.isEmpty()) throw new IllegalArgumentException("Character has not moved yet");
        List<Save.MovementState> movements = new ArrayList<>(saveMap.keySet());
        Input action = movements.get(pos).direction();
        // pass direction to app method
        ac.handleInput(action);
        System.out.println("step-by-step position: " + pos);
        System.out.println("step-by-step direction: " + action);
        pos++;
        return true;
    }

    /**
     * Very basic implementation. In one iteration, reads all the frames
     * Currently, doesn't implement speed.
     */
    public void autoPlay(AppController ac) {
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        getData();
        if (saveMap.isEmpty()) throw new IllegalArgumentException("Character has not moved yet");
        for (int frame = 0; frame < saveMap.size(); frame++) {
            System.out.println("autoplay position: " + frame);
            //Input frame1 = saveMap.get(frame);
            //ac.handleInput(frame1);
            //System.out.println("autoplay direction: " + frame1);
        }
    }
    public static void main(String[] args) {
        Play p = new Play();
        p.setSpeed(2);
        AppController x = AppController.of();
        p.stepByStep(x);
        p.stepByStep(x);
        p.stepByStep(x);
        p.stepByStep(x);
        p.autoPlay(x);
    }
}

