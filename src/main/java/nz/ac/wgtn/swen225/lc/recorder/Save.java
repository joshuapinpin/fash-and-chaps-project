package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.game.GameMapper;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.game.GameState;

import javax.swing.*;
import java.io.File;

public interface Save{
    /**
     */
    public void reset();
    /**
     */
    public void  startRecording(AppController ac);
    public void updateMovement(Input direction, AppController ac, boolean toSave);
    /** */
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