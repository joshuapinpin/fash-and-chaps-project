package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.controller.*;
import nz.ac.wgtn.swen225.lc.app.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class StepByStepL1 implements Play{
    private static final StepByStepL1 playInstance = new StepByStepL1();
    private static int pos; // count for step by step playing
    private List<SaveL1.inputTime> saveList;
    private final ObjectMapper mapper;
    private StepByStepL1(){
        saveList = new ArrayList<>();
        mapper = new ObjectMapper();
        pos = 0;
    }
    /**
     * Factory method to return singleton Save instance
     * @return
     */
    public static StepByStepL1 of() {
        return playInstance;
    }
    /**
     * Resets autoplay and step-by-step play.
     * Used for resetting for Level 2.
     */
    public void reset(){
        saveList = new ArrayList<>();
        pos = 0;
    }
    /** */
    public void setSpeed(int s){
        // dont need to set speed for step-by-step
    }
    /** main play method from interface */
    public boolean play(AppController ac){
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        // Get data only in the first iteration
        if(pos == 0) saveList = getData(mapper);
        if(saveList.isEmpty()) return false; // stop play immediately
        return stepByStep(ac);
    }
    /**
     * Very basic implementation of step by step. Reads one input
     * from the list, everytime method is called.
     */
    private boolean stepByStep(AppController ac) {
        // make the method return true while we still have positions to go
        if(pos == saveList.size()){pos = 0; return false;}
        if(saveList.isEmpty()) return false; // stop play immediately
        SaveL1.inputTime iT = saveList.get(pos);
        Input direction = iT.direction();
        // pass direction to app method
        ac.handleInput(direction);
        System.out.println("step-by-step position: " + pos);
        System.out.println("step-by-step direction: " + direction);
        pos++;
        return true;
    }
}
