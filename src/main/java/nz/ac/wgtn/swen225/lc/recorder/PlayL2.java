package nz.ac.wgtn.swen225.lc.recorder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.app.controller.*;
import nz.ac.wgtn.swen225.lc.persistency.GameMapper;
import nz.ac.wgtn.swen225.lc.persistency.GameState;
import nz.ac.wgtn.swen225.lc.persistency.LoadedMaze;
import java.io.File;
import javax.swing.*;
import java.io.*;

/**
 * Interface defining the contract for game replay functionality.
 * Implementations of this interface handle replaying recorded game sessions
 * either automatically or step-by-step.
 *
 * @author Arushi Bhatnagar Stewart
 * Student ID: 300664237
 */
public interface PlayL2{
    /**
     * Initiates replay of a recorded game session.
     *
     * @param ac the application controller to apply movements to
     * @return true if replay started successfully, false otherwise
     */
    public boolean play(AppController ac);
    /**
     * Resets the replay state, clearing any loaded recording data.
     */
    public void reset();
    /**
     * Sets the playback speed for automatic replay.
     *
     * @param s the speed multiplier for replay
     */
    public void setSpeed(int s);
    /**
     * Loads and initializes the game state from a recording.
     * Sets the game to the exact state it was in at the start of the recording.
     *
     * @param gs the game state to load
     * @param c the application controller to apply the state to
     */
    public default void setState(GameState gs, AppController c){
        // sets the game to position at start of recording
        LoadedMaze maze = new GameMapper().fromState(gs);
        c.persistencyController().loadDomain(maze);
    }
    /**
     * Prompts the user to select a recording file to play.
     * Opens a file chooser dialog filtered for JSON files.
     *
     * @return the selected File object, or null if the user cancels
     */
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
     * Reads a complete game recording from a JSON file.
     * Deserializes the file into a FullGame object containing all moves and initial state.
     *
     * @param mapper the ObjectMapper for JSON deserialization
     * @return the FullGame object containing the recording, or null if loading fails
     */
    public default SaveL2.FullGame getData(ObjectMapper mapper) {
        /*
        using new TypeReference<List<MyObject>>() {} to create
        an anonymous subclass of TypeReference,
        it carries the actual generic type (List<Input>)
        in its class signature. Can't do List.class.
         */
        File myFile = getFile();
        SaveL2.FullGame fg = null;
        if (myFile == null) {
            // return empty list, which is handled by autoplay and step-by-step methods
            return fg;
        }
        try {
            fg = mapper.readValue(myFile, new TypeReference<SaveL2.FullGame>() {});
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to get recording: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return fg;
    }

    /** This method handles completion of autoplay */
    public default boolean isPlayComplete(Timer autoplayTimer) {
        if (autoplayTimer != null) {
            autoplayTimer.stop();
        }
        System.out.println("Playback completed");
        return true;
    }
}

