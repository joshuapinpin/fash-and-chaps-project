package nz.ac.wgtn.swen225.lc.persistency.save;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.persistency.save.util.FileDialog;
import nz.ac.wgtn.swen225.lc.persistency.save.util.SwingFileDialog;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * Used within this package to allow a user to save/load game objects from JSON files via a Swing GUI.
 * @param <T> - a type of Maze (i.e. game board).
 */
class GamePersistManager<T extends Maze> implements PersistManager<T> {
    private final FileDialog fileDialog;
    private final ObjectMapper mapper; // used for jackson serialisation+deserialisation
    private final Class<T> type; // used for Jackson deserialisation
    private static final String extension = "json";

    /**
     * Constructor requiring a class for Jackson deserialisation.
     * @param - the class of the generic type.
     */
    public GamePersistManager(Class<T> type) {
        this.fileDialog = new SwingFileDialog();
        this.mapper = new ObjectMapper();
        this.type = type;
    }

    /**
     * Allow user to save a game to JSON via GUI, with a default file name based on the time and date.
     * @param data - the Maze object to save.
     * @param parent - the parent JFrame/window.
     */
    @Override
    public void save(T data, JFrame parent) {
        String defaultName = timestampName();
        fileDialog.showSaveDialog(parent, defaultName, extension)
                .ifPresent(file -> {
                    try {
                        mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
                        JOptionPane.showMessageDialog(parent,
                                "File saved: " + file.getAbsolutePath(),
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(parent,
                                "Error saving: " + e.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
    }

    /**
     * Allow user to load a game from JSON via GUI.
     * @param parent - the parent JFrame/window.
     * @return - an Optional of the Maze, which will be empty if nothing could be loaded.
     */
    @Override
    public Optional<T> load(JFrame parent) {
        Optional<File> selected = fileDialog.showOpenDialog(parent, extension);
        if (selected.isEmpty()) {
            return Optional.empty();
        }

        // deserialise the json to a Maze if possible
        try {
            return Optional.of(mapper.readValue(selected.get(), type));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent,
                    "Error loading: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return Optional.empty();
        }
    }

    /**
     * Helper to generate a name for a game save file based on the current time/date.
     * @return - the file name as a String.
     */
    private String timestampName() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "chaps_save_" + timestamp + "." + extension;
    }
}
