package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
    private List<Input> movements = new ArrayList<>();
    final ObjectMapper mapper = new ObjectMapper();;
    /** Method to add the Input objects (the directions/movements)
     * of the character to the movements list.
     *
     */
    public Save(){}
    /** */
    public void reset(){
        movements = new ArrayList<>();
    }
    /** */
    public void addMovement(Input direction) {
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        movements.add(direction);
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
            mapper.writerWithDefaultPrettyPrinter().writeValue(playerMovements, movements);
        } catch(IOException e){
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        Save s = new Save();
        s.addMovement(Input.MOVE_UP);
        s.addMovement(Input.MOVE_DOWN);
        s.addMovement(Input.MOVE_LEFT);
        s.addMovement(Input.MOVE_RIGHT);
        s.saveToFile();
    }
}
