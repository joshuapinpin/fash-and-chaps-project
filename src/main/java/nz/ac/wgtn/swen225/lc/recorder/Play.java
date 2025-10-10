package nz.ac.wgtn.swen225.lc.recorder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.app.controller.*;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.GameMapper;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.GameState;
import java.io.File;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Initial outline for class to replay the actions of the character.
 *
 * @author Arushi Bhatnagar Stewart
 */

// use game controller handle input?
public interface Play {
    /**
     *
     */
    public boolean play(AppController ac);
    /** */
    public void reset();
    /** */
    public void setSpeed(int s);
    /** */
    public default File getFile(){
        File fileChoice = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open JSON File");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JSON files", "json"));
        // specifically for opening file
        int userChoice = fileChooser.showOpenDialog(null);
        if (userChoice == JFileChooser.APPROVE_OPTION) {
            fileChoice = fileChooser.getSelectedFile();
        }
        else {
            // user canceled or closed dialog
            JOptionPane.showMessageDialog(null, "File selection canceled.", "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return fileChoice;
    }
    /**
     * This methods reads the list of saveMap from the json file
     * and assigns it to our movement arraylist field.
     */
    default List<SaveL1.Moves> getData(ObjectMapper mapper) {
        /*
        using new TypeReference<List<MyObject>>() {} to create
        an anonymous subclass of TypeReference,
        it carries the actual generic type (List<Input>)
        in its class signature. Can't do List.class.
         */
        File myFile = getFile();
        List<SaveL1.Moves> saveList = new ArrayList<>();
        if (myFile == null) {
            // return empty list, which is handled by autoplay and step-by-step methods
            return saveList;
        }
        try {
            saveList = mapper.readValue(myFile, new TypeReference<List<SaveL1.Moves>>() {
            });
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to get recording: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return saveList;
    }
}

