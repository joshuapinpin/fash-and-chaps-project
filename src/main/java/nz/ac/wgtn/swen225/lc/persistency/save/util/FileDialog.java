package nz.ac.wgtn.swen225.lc.persistency.save.util;

import java.io.File;
import java.util.Optional;
import javax.swing.JFrame;

/**
 * API to select files to save/load via a Swing dialog.
 * @author Thomas Ru - 300658840
 */
public interface FileDialog {

    /**
     * Select a file for loading.
     * @param parent - the parent JFrame/window.
     * @param extension - the file extension, as a String.
     * @return - an Optional<File>, which will be empty if no file was selected.
     */
    Optional<File> showOpenDialog(JFrame parent, String extension);

    /**
     * Select a file for saving.
     * @param parent - the parent JFrame/window.
     * @param defaultName - the default name of the file, as a String.
     * @param extension - the file extension, as a String.
     * @return - an Optional<File>, which will be empty if no file was saved.
     */
    Optional<File> showSaveDialog(JFrame parent, String defaultName, String extension);
}
