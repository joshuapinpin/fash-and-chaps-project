package nz.ac.wgtn.swen225.lc.recorder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.app.controller.*;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.game.GameMapper;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.game.GameState;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.game.LoadedMaze;

import java.io.File;
import javax.swing.*;
import java.io.*;


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
    public default void startState(GameState gs, AppController c){
        // sets the game to position at start of recording
        LoadedMaze maze = new GameMapper().fromState(gs);
        c.persistencyController().loadDomain(maze);
    }
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
     * This methods reads full game object from the json file
     * and returns it
     */
    default SaveL1.FullGame getData(ObjectMapper mapper) {
        /*
        using new TypeReference<List<MyObject>>() {} to create
        an anonymous subclass of TypeReference,
        it carries the actual generic type (List<Input>)
        in its class signature. Can't do List.class.
         */
        File myFile = getFile();
        SaveL1.FullGame fg = null;
        if (myFile == null) {
            // return empty list, which is handled by autoplay and step-by-step methods
            return fg;
        }
        try {
            fg = mapper.readValue(myFile, new TypeReference<SaveL1.FullGame>() {});
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to get recording: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return fg;
    }
}

