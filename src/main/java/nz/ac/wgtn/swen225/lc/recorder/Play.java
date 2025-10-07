package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.controller.*;
import nz.ac.wgtn.swen225.lc.app.util.*;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.io.*;
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
    public default void startPlay(){
        // sets the game to position at start of recording
    }
    public default void stopPlay(){
        // sets the game to position at the end of recording
    }
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
        assert fileChoice != null: "File not chosen";
        return fileChoice;
    }
}

