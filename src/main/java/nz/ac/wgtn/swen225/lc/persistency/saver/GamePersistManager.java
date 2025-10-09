package nz.ac.wgtn.swen225.lc.persistency.saver;

import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.FileIO;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.GameState;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.LoadedMaze;
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
class GamePersistManager implements PersistManager<LoadedMaze> {
    private final FileDialog fileDialog = new SwingFileDialog();
    private final FileIO<GameState> fileIO;
    private final Mapper<LoadedMaze, GameState> mapper;
    private static final String extension = "json";

    /**
     * Creates a GamePersistManager given a way to convert between Maze, GameState, and file-written
     * representation of GameState.
     * @param fileIO - reads and writes GameState objects to file.
     * @param mapper - converts from Maze to GameState, or vice versa.
     */
    public GamePersistManager(FileIO<GameState> fileIO, Mapper<LoadedMaze, GameState> mapper) {
        this.fileIO = fileIO;
        this.mapper = mapper;
    }

    /**
     * Allow user to save a game to JSON via GUI, with a default file name based on the time and date.
     * @param data - the Maze object to save.
     * @param parent - the parent JFrame/window.
     */
    public void save(Maze data, int levelNumber, int maxTreasures, int time, JFrame parent) {
        String defaultName = timestampName();
        fileDialog.showSaveDialog(parent, defaultName, extension)
                .ifPresent(file -> {
                    try {
                        fileIO.save(mapper.toState(new LoadedMaze(data, levelNumber, maxTreasures, time)), file);
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

    @Override
    public void save(LoadedMaze data, JFrame parent) {
        save(data.maze(), data.levelNumber(), data.maxTreasure(), data.time(), parent);
    }
    /**
     * Allow user to load a game from JSON via GUI.
     * @param parent - the parent JFrame/window.
     * @return - an Optional of the Maze, which will be empty if nothing could be loaded.
     */
    @Override
    public Optional<LoadedMaze> load(JFrame parent) {
        Optional<File> selected = fileDialog.showOpenDialog(parent, extension);
        if (selected.isEmpty()) {
            return Optional.empty();
        }

        // deserialise to a Maze if possible
        try {
            GameState gameState = fileIO.load(selected.get());
            return Optional.of(mapper.fromState(gameState));
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
