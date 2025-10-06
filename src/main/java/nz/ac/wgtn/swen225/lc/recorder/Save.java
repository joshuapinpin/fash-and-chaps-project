package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.util.*;
import nz.ac.wgtn.swen225.lc.app.controller.*;
import nz.ac.wgtn.swen225.lc.app.controller.logic.*;
import nz.ac.wgtn.swen225.lc.app.state.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import javax.swing.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Class for saving each movement of the Character
 * It coordinates with the App package, which calls
 * the addMovement method, passes an Input object
 * every time the character moves.
 *
 * @author Arushi Bhatnagar Stewart
 */
public class Save{
    public record inputTime(Input direction, Integer timeLeft){}
    private static final Save saveInstance = new Save();
    private List<inputTime> saveList;
    private final ObjectMapper mapper;
    /** Method to add the Input objects (the directions/movements)
     * of the character to the movements list.
     *
     */
    private Save(){
        saveList = new ArrayList<>();
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
        saveList = new ArrayList<>();
    }
    /** */
    public void addMovement(Input direction, AppController ac) {
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        int timeLeft = ac.timerController().getTimeLeft();
        saveList.add(new inputTime(direction, timeLeft));
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
            mapper.writerWithDefaultPrettyPrinter().writeValue(playerMovements, saveList);
        } catch(IOException e){
            throw new Error(e);
        }
    }

    /*public static void main(String[] args) {
        Save s = Save.of();
        AppController ac = AppController.of();
        s.addMovement(Input.MOVE_UP, ac);
        s.addMovement(Input.MOVE_DOWN, ac);
        s.addMovement(Input.MOVE_LEFT, ac);
        s.addMovement(Input.MOVE_RIGHT, ac);
        s.saveToFile();
    } */
}
