package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Maze;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * Used within this package to allow a user to save/load game objects
 * from JSON files via a Swing GUI.
 * @author Thomas Ru - 300658840
 */
public class GamePersistManager implements PersistManager<LoadedMaze> {
    private final Message message;
    private final FileDialog fileDialog;
    private final FileIO<GameState> fileIO;
    private final Mapper<LoadedMaze, GameState> mapper;
    private static final String extension = "json";

    /**
     * Creates a GamePersistManager given a way to convert between Maze, GameState,
     * and go to/from file-written representation of GameState.
     * @param message - popup that displays success/failure.
     * @param fileDialog - popup that allows user to choose files to save/load.
     * @param fileIO - reads and writes GameState objects to file.
     * @param mapper - converts from Maze to GameState, or vice versa.
     */
    public GamePersistManager(
            Message message,
            FileDialog fileDialog,
            FileIO<GameState> fileIO,
            Mapper<LoadedMaze, GameState> mapper
    ) {
        this.message = message;
        this.fileDialog = fileDialog;
        this.fileIO = fileIO;
        this.mapper = mapper;
    }

    /**
     * Allow user to save a game to JSON via GUI, with a default file name based on the time and date.
     * @param data - the Maze object to save.
     * @param levelNumber - the current level (e.g. 1, 2)
     * @param maxTreasures - the maximum number of treasures on the current level.
     * @param maxKeys - the maximum number of keys on the current level.
     * @param time - the time left for this play-through.
     * @param parent - the parent JFrame/window.
     */
    public boolean save(Maze data, int levelNumber, int maxTreasures, int maxKeys, int time, JFrame parent) {
        Objects.requireNonNull(data, "Cannot save null game board.");
        String defaultName = timestampName();
        Optional<File> fileOpt = fileDialog.showSaveDialog(parent, defaultName, extension);

        if(fileOpt.isEmpty()) return false;

        fileOpt.ifPresent(file -> {
            try {
                LevelInfo levelInfo = new LevelInfo(levelNumber, maxKeys, maxTreasures);
                fileIO.save(mapper.toState(new LoadedMaze(data, time, levelInfo)), file);
                message.showMessage(
                        "File saved: " + file.getAbsolutePath(),
                        "Success", JOptionPane.INFORMATION_MESSAGE
                );
            } catch (IOException e) {
                message.showMessage(
                        "Error saving: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE
                );
            }
        });
        return true;
    }

    /**
     * Present for completeness/implementing the interface.
     * Saves a LoadedMaze to file.
     * @param data - the LoadedMaze
     * @param parent - the parent window for the GUI file choosing popup.
     */
    @Override
    public boolean save(LoadedMaze data, JFrame parent) {
        LevelInfo meta = data.levelInfo();
        return save(data.maze(), meta.levelNumber(), meta.maxTreasures(), meta.maxKeys(), data.time(), parent);
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
            message.showMessage(
                    "Error loading: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE
            );
            return Optional.empty();
        }
    }

    /**
     * Helper to generate a name for a game save file based on the current time/date.
     * @return - the file name as a String.
     */
    private String timestampName() {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        return "chaps_save_" + timestamp + "." + extension;
    }
}
