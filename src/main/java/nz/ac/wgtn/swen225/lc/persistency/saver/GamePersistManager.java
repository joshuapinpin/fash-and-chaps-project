package nz.ac.wgtn.swen225.lc.persistency.saver;

import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.FileIO;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.GameState;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.Mapper;
import nz.ac.wgtn.swen225.lc.persistency.saver.gui.FileDialog;
import nz.ac.wgtn.swen225.lc.persistency.saver.gui.SwingFileDialog;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * Used within this package to allow a user to save/load game objects from JSON files via a Swing GUI.
 * @param <M> - a type of Maze (i.e. game board).
 * @param <S> - a type of GameState, i.e. reduced representation of Maze suitable for serialisation.
 * @author Thomas Ru - 300658840
 */
class GamePersistManager<M extends Maze, S extends GameState> implements PersistManager<M> {
    private final FileDialog fileDialog = new SwingFileDialog();
    private final FileIO<S> fileIO;
    private final Mapper<M, S> mapper;
    private static final String extension = "json";

    /**
     * Creates a GamePersistManager given a way to convert between Maze, GameState, and file-written
     * representation of GameState.
     * @param fileIO - reads and writes GameState objects to file.
     * @param mapper - converts from Maze to GameState, or vice versa.
     */
    public GamePersistManager(FileIO<S> fileIO, Mapper<M, S> mapper) {
        this.fileIO = fileIO;
        this.mapper = mapper;
    }

    /**
     * Allow user to save a game to JSON via GUI, with a default file name based on the time and date.
     * @param data - the Maze object to save.
     * @param parent - the parent JFrame/window.
     */
    @Override
    public void save(M data, JFrame parent) {
        String defaultName = timestampName();
        fileDialog.showSaveDialog(parent, defaultName, extension)
                .ifPresent(file -> {
                    try {
                        fileIO.save(mapper.toGameState(data), file);
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
    public Optional<M> load(JFrame parent) {
        Optional<File> selected = fileDialog.showOpenDialog(parent, extension);
        if (selected.isEmpty()) {
            return Optional.empty();
        }

        // deserialise to a Maze if possible
        try {
            S gameState = fileIO.load(selected.get());
            return Optional.of(mapper.fromGameState(gameState));
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
