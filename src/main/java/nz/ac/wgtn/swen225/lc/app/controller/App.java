package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.app.state.*;
import nz.ac.wgtn.swen225.lc.app.gui.*;
import nz.ac.wgtn.swen225.lc.app.util.*;
import nz.ac.wgtn.swen225.lc.domain.Direction;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;
import nz.ac.wgtn.swen225.lc.persistency.levelloader.*;

/**
 * Central controller for game logic and flow.
 * Handles user input, updates the domain model, and triggers view updates.
 * Coordinates between Model (Domain), View (Renderer), and other controllers.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class App implements GameController {
    private Maze domain; // Reference to the domain model
    private Renderer renderer;// Reference to the renderer/view

    // CONTROLLER Components
    private InputController inputController;
    private TimerController timerController;
    private RecorderController recorderController;
    // Reference to persistence

    // GAME MANAGEMENT Components
    private AppWindow window; // Reference to the main application window
    private GameState state;
    private int level;

    public App() {
        initialiseControllerComponents();
        startNewGame(1);
    }


    private void initialiseControllerComponents() {
        // Initialize domain model, renderer, and controllers
        domain = Levels.LevelOne.load();

        renderer = new Renderer(domain.getTileGrid(), domain.getPlayer());
        int size = AppWindow.MAZE_SIZE;
        renderer.setDimensions(size, size);

        inputController = new InputController(this);
        timerController = new TimerController(this);
        recorderController = new RecorderController(this, timerController);
        window = new AppWindow(this, inputController,
                timerController, recorderController);
    }


    // ========== Game Controller Implementation ==========

    /**
     * Handles a user input (e.g., move, pause, save, etc).
     * Delegates to the current game state for processing.
     * @param input The user input to handle
     */
    public void handleInput(Input input) {
        System.out.println("*DEBUG* Inside of the App Package Now");
        if(state == null) throw new RuntimeException("Game state is null.");

        try {
            state.handleInput(this, input);
            recorderController.addMovement(input);
        }
        catch(UnsupportedOperationException e){
            System.out.println(
                    "Input " + input + " not valid in current state: "
                    + state.getClass().getSimpleName()
            );
        }
        window.updateWindow();

        // Update the renderer with the latest domain state
        if(domain == null)
            throw new RuntimeException("Cannot update renderer: Domain is null.");
        if(renderer == null)
            throw new RuntimeException("Cannot update renderer: Renderer is null.");
        renderer.getPanel().setAllTiles(domain.getTileGrid(), domain.getPlayer());
        renderer.getPanel().repaint();
    }

    /**
     * Moves the player in the specified direction.
     * @param dir Direction to move the player
     */
    public void movePlayer(Direction dir){
        if(domain == null) throw new RuntimeException("Cannot move player: Domain is null.");
        domain.movePlayer(dir);
    }

    /**
     * Starts a new game at the given level.
     * @param level The level to start the new game at
     */
    public void startNewGame(int level) {
        timerController.startTimer(TimerController.getTimeLimitForLevel(level));
        setState(new PlayState(timerController));

        if(level == 1) domain = Levels.LevelOne.load();
//        else if(level == 2) domain = Levels.LevelTwo.load();
        else throw new IllegalArgumentException("Invalid level: " + level);

        this.level = level;
        recorderController.stopRecording();
        System.out.println("Starting New Game at Level " + level);
    }

    /**
     * Pauses the game.
     */
    public void pauseGame() {
        // TODO: Implement pause logic
        setState(new PausedState(timerController));
        System.out.println("Game Paused");
    }

    /**
     * Resumes the game.
     */
    public void resumeGame() {
        // TODO: Implement resume logic
        System.out.println("Game Resumed");
    }

    /**
     * Continues the game from a paused state.
     */
    public void continueGame() {
        setState(new PlayState(timerController));
        System.out.println("Continuing Game");
    }

    /**
     * Saves the current game state.
     */
    public void saveGame(){
        if(domain == null) throw new RuntimeException("Cannot save game: Domain is null.");
        //TODO: get Persistence to create a "save current game" method
        System.out.println("Game Saved!");
    }

    /**
     * Loads a saved game state.
     * Opens a file chooser to select the saved game file.
     * Uses Persistence to retrieve the saved state and update the domain model.
     */
    public void loadGame(){
        // TODO: get Persistence to create a "load saved game" method, which returns a Domain object
        System.out.println("Game Loaded!");
    }

    /**
     * Exits the current game.
     */
    public void exitGame() {
        System.out.println("Exiting Game...");
        System.exit(0);
    }

    @Override
    public void help() {
        System.out.println("Displaying Help/Rules...");
    }

    public void timeUp() {
        setState(new DeadState(timerController));
        System.out.println("Time's Up! Game Over.");
    }

    public void setState(GameState state) {this.state = state;}
    public AppWindow getGameWindow() {return window;}
    public GameState getState() {return state;}
    public Maze getDomain() {return domain;}
    public Renderer getRenderer() {return renderer;}
    public int getLevel() {return level;}
}
