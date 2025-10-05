package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.util.*;
import nz.ac.wgtn.swen225.lc.app.controller.*;
import nz.ac.wgtn.swen225.lc.app.controller.logic.*;
import nz.ac.wgtn.swen225.lc.app.state.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
/**
 * Class for saving each movement of the Character
 * It coordinates with the App package, which calls
 * the addMovement method, passes an Input object
 * every time the character moves.
 * Need to change the algorithm and use the observer pattern.
 *
 * @author Arushi Bhatnagar Stewart
 */
public class Save{
    private record MovementState(Input direction, GameState state){}
    private Map<MovementState, Integer> saveMap = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();;
    /** Method to add the Input objects (the directions/movements)
     * of the character to the movements list.
     *
     */
    public Save(){}
    /** */
    public void reset(){
        saveMap = new HashMap<>();
    }
    /** */
    public void addMovement(Input direction, AppController ac) {
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        MovementState ms = new MovementState(direction, ac.state());
        int timeLeft = ac.timerController().getTimeLeft();
        saveMap.put(ms, timeLeft);
    }
    /** */
    public File chooseFile(){
        File myFile = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save JSON File");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JSON files", "json"));
        // parent is null, the dialog is displayed in a default position, centered on the screen
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            myFile = fileChooser.getSelectedFile();
            if (!myFile.getName().toLowerCase().endsWith(".json")) {
                myFile = new File(myFile.getParentFile(), myFile.getName() + ".json");
            }
        }
        assert myFile != null: "File not chosen";
        return myFile;
    }
    /** This is the core method of this class.
     * In this method, we create a file and write
     * elements in the movement list to the file
     */
    public void saveToFile(){
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        File playerMovements = chooseFile();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(playerMovements, saveMap);
        } catch(IOException e){
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        Save s = new Save();
        AppController ac = AppController.of();
        s.addMovement(Input.MOVE_UP, ac);
        s.addMovement(Input.MOVE_DOWN, ac);
        s.addMovement(Input.MOVE_LEFT, ac);
        s.addMovement(Input.MOVE_RIGHT, ac);
        s.saveToFile();
    }
}
