package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.util.Input;

import javax.swing.*;
import java.io.File;

/**
 * Interface defining the contract for game recording functionality.
 * Implementations of this interface handle recording player movements
 * and game state for later replay.
 * Student ID: 300664237
 */
public interface Save{
    /**
     * Resets the recorder state, clearing any previously recorded data.
     */
    public void reset();
    /**
     * Initializes the recorder with the current game state at the start of recording.
     *
     * @param ac the application controller containing the current game state
     */
    public void  startRecorder(AppController ac);
    /**
     * Updates the recording with a new movement or triggers saving to file.
     *
     * @param direction the input direction for the movement
     * @param ac the application controller
     * @param toSave if true, saves the recording to file; if false, adds the movement
     */
    public void updateMovement(Input direction, AppController ac, boolean toSave);
    /**
     * Prompts the user to select a file location for saving.
     * Opens a file chooser dialog filtered for JSON files and automatically
     * appends the .json extension if not provided.
     *
     * @return the selected File object, or null if the user cancels
     */
    public default File chooseFile(){
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
        else {
            // user canceled or closed dialog
            JOptionPane.showMessageDialog(null, "File selection canceled.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        return fileChoice;
    }
}