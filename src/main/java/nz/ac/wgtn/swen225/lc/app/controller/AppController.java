package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.app.controller.logic.InputController;
import nz.ac.wgtn.swen225.lc.app.controller.logic.TimerController;
import nz.ac.wgtn.swen225.lc.app.controller.module.DomainController;
import nz.ac.wgtn.swen225.lc.app.controller.module.PersistencyController;
import nz.ac.wgtn.swen225.lc.app.controller.module.RecorderController;
import nz.ac.wgtn.swen225.lc.app.controller.module.RendererController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.state.DefeatState;
import nz.ac.wgtn.swen225.lc.app.state.GameState;
import nz.ac.wgtn.swen225.lc.app.state.PausedState;
import nz.ac.wgtn.swen225.lc.app.state.PlayState;
import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.domain.Direction;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;


/**
 * Central controller for game logic and flow.
 * Handles user input, updates the domain model, and triggers view updates.
 * Coordinates between Model (Domain), View (Renderer), and other controllers.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class AppController {
    // APP CONTROLLERS
    private InputController inputController;
    private TimerController timerController;
    private AppWindow window;

    // MODULE CONTROLLERS
    private RecorderController recorderController;
    private PersistencyController persistencyController;
    private DomainController domainController;
    private RendererController rendererController;

    // GAME MANAGEMENT Components
    private GameState state;
    private int level;

    /**
     * Constructor initializes the game components and starts a new game.
     */
    private AppController() {
        initialiseControllers();
//        setState(new StartState());
        startNewGame(1);
    }

    private void initialiseControllers(){
        inputController = new InputController(this);
        timerController = new TimerController(this);

        domainController = new DomainController(this);
        rendererController = new RendererController(this, domainController);
        recorderController = new RecorderController(this, timerController);

        window = new AppWindow(this, inputController, timerController, recorderController);
    }


    // ========== Game Controller Implementation ==========

    /**
     * Factory method to create a new AppController instance.
     * @return A new AppController instance
     */
    public static AppController of() {
        return new AppController();
    }

    /**
     * Handles a user input (e.g., move, pause, save, etc.).
     * Delegates to the current game state for processing.
     * Updates the GUI after processing input.
     * App -> Domain -> App -> Renderer
     * @param input The user input to handle
     */
    public void handleInput(Input input) {
        System.out.println("*DEBUG* Inside of the App Package Now");
        if(state == null) throw new RuntimeException("Game state is null.");

        // Handle Input, send input to recorder if valid
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

        // Update Maze after handling input
        rendererController.updateMaze(domainController);

        // Update Supporting GUI Elements
        window.updateWindow();
    }

    /**
     * Moves the player in the specified direction.
     * @param dir Direction to move the player
     */
    public void movePlayer(Direction dir){
        domainController.movePlayer(dir);
    }

    /**
     * Starts a new game at the given level.
     * @param level The level to start the new game at
     */
    public void startNewGame(int level) {
        setState(new PlayState(timerController));
        timerController.startTimer(level);
        domainController.initialiseDomain(level);
        recorderController.stopRecording();
        this.level = level;
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
     * Resumes a saved game
     * Load game from file selector.
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

    /**
     * Displays help or game rules.
     */
    public void help() {
        System.out.println("Displaying Help/Rules...");
    }

    /**
     * Handles the event when time is up.
     */
    public void timeUp() {
        setState(new DefeatState(timerController));
        System.out.println("Time's Up! Game Over.");
    }

    // ========== Getters and Setters ==========
    public void setState(GameState state) {this.state = state;}

    public int level() {return level;}
    public GameState state() {return state;}
    public Maze domain() {return domainController.domain();}
    public Renderer renderer() {return rendererController.renderer();}
    public AppWindow gameWindow() {return window;}
}
