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
 *
 * @author Arushi Bhatnagar Stewart
 */
public class Save{
    /**
     *
     * @param direction
     * @param state
     */
    record MovementState(Input direction, GameState state){}
    private static final Save saveInstance = new Save();
    private Map<MovementState, Integer> saveMap;
    private final ObjectMapper mapper;
    /** Method to add the Input objects (the directions/movements)
     * of the character to the movements list.
     *
     */
    private Save(){
        saveMap = new HashMap<>();
        mapper = new ObjectMapper();
    }
    /**
     * Factory method to return singleton Save instance
     * @return
     */
    public static Save of() {
        return saveInstance;
    }
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
    private File chooseFile(){
        File fileChoice = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save JSON File");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JSON files", "json"));
        // parent is null, the dialog is displayed in a default position, centered on the screen
        // specifically for saving
        int userChoice = fileChooser.showSaveDialog(null);
        if (userChoice == JFileChooser.APPROVE_OPTION) {
            fileChoice = fileChooser.getSelectedFile();
            if (!fileChoice.getName().toLowerCase().endsWith(".json")) {
                fileChoice = new File(fileChoice.getParentFile(), fileChoice.getName() + ".json");
            }
        }
        assert fileChoice != null: "File not created";
        return fileChoice;
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
        Save s = Save.of();
        AppController ac = AppController.of();
        s.addMovement(Input.MOVE_UP, ac);
        s.addMovement(Input.MOVE_DOWN, ac);
        s.addMovement(Input.MOVE_LEFT, ac);
        s.addMovement(Input.MOVE_RIGHT, ac);
        s.saveToFile();
    }
}
