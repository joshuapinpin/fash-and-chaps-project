package nz.ac.wgtn.swen225.lc.persistency.saver.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Optional;

/**
 * Concrete file saving/loading dialogs, implemented using Swing.
 * @author Thomas Ru - 300658840
 */
public class SwingFileDialog implements FileDialog {

    /**
     * Show a dialog for loading a file.
     * @param parent - the parent JFrame/window.
     * @param extension - the file extension, as a String.
     * @return - an Optional<File>, which will be empty if no file was selected.
     */
    @Override
    public Optional<File> showOpenDialog(JFrame parent, String extension) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter(
                extension.toUpperCase() + " files", extension
        ));

        int result = chooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            return Optional.of(chooser.getSelectedFile());
        }
        return Optional.empty();
    }

    /**
     * Show a save dialog with the given default file name.
     * The defaultName can be null or empty, in which case the chooser starts empty.
     * @param parent - the parent JFrame/window.
     * @param defaultName - the default name of the file to save, as a String.
     * @param extension - the file extension, as a String.
     */
    @Override
    public Optional<File> showSaveDialog(JFrame parent, String defaultName, String extension) {
        // get user to select a file to save to
        JFileChooser chooser = new JFileChooser();
        if (defaultName != null && !defaultName.isBlank()) {
            chooser.setSelectedFile(new File(defaultName));
        }
        chooser.setFileFilter(new FileNameExtensionFilter(
                extension.toUpperCase() + " files", extension
        ));
        int result = chooser.showSaveDialog(parent);
        if (result != JFileChooser.APPROVE_OPTION) {
            return Optional.empty();
        }

        // tidy up the chosen file
        File file = chooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith("." + extension.toLowerCase())) {
            file = new File(file.getAbsolutePath() + "." + extension);
        }
        if (file.exists() &&  confirmOverwrite(parent)) {
            return Optional.empty();
        }

        return Optional.of(file);
    }

    /**
     * Confirms whether a user wants to overwrite a file.
     * @param parent - the parent JFrame/window.
     * @return - true if they do, otherwise false.
     */
    private boolean confirmOverwrite(JFrame parent) {
        int overwrite = JOptionPane.showConfirmDialog(parent,
                "File already exists. Overwrite?",
                "Confirm Save",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        return overwrite == JOptionPane.YES_OPTION;
    }
}