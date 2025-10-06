package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.app.controller.local.*;
import nz.ac.wgtn.swen225.lc.app.controller.module.*;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.state.*;
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
    private WindowController windowController;

    // MODULE CONTROLLERS
    private RecorderController recorderController;
    private PersistencyController persistencyController;
    private DomainController domainController;
    private RendererController rendererController;

    // GAME MANAGEMENT Components
    private GameState state;
    private static final AppController APP = new AppController();

    /**
     * Factory method to return singleton AppController instance
     * @return The single instance of AppController
     */
    public static AppController of() {
        return APP;
    }

    /**
     * Constructor initializes the game components and starts a new game.
     */
    private AppController() {
        initialiseControllers();
        setState(new StartState());
    }

    private void initialiseControllers(){
        inputController = new InputController(this);
        timerController = new TimerController(this);
        persistencyController = new PersistencyController(this);
        domainController = new DomainController(this);
        rendererController = new RendererController(this, domainController);
        recorderController = new RecorderController(this, timerController);
        windowController = new WindowController(this);
    }

    // ========== Game Controller Implementation ==========

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
            //TODO: also pass the exact time the input was made
            recorderController.addMovement(input);
        }
        catch(UnsupportedOperationException e){
            System.out.println(
                    "Input " + input + " not valid in current state: "
                            + state.getClass().getSimpleName()
            );
        }

        // Update Maze and window after handling input
        rendererController.updateMaze(domainController);
        windowController.updateWindow();
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
        timerController.startTimer(level);
        setState(new PlayState(this));
        domainController.initialiseDomain(level);
        recorderController.stopRecording();
        windowController.displayInfo(false);

        rendererController.updateMaze(domainController);
        windowController.initialiseWindow();

        System.out.println("Starting New Game at Level " + level);
    }

    /**
     * Restarts the current level.
     */
    public void restartLevel(){
        System.out.println("Restarting Level");
        rendererController.stopMusic();
        startNewGame(persistencyController.level());
    }

    /**
     * Continues the game from a paused state.
     */
    public void continueGame() {
        setState(new PlayState(this));
        System.out.println("Continuing Game");
    }

    /**
     * Pauses the game.
     */
    public void pauseGame() {
        setState(new PausedState(this));
        System.out.println("Game Paused");
    }

    /**
     * Resumes a saved game
     * Load game from file selector.
     * Get from persistency
     */
    public void resumeGame() {
        // TODO: Implement resume logic
        System.out.println("Game Resumed");
    }

    /**
     * Saves the current game state.
     * Get from persistency
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
        setState(new HelpState(this));
        System.out.println("Displaying Help/Rules...");
    }

    /**
     * Handles the event when player achieves victory
     * By collecting all treasures and reaching exit
     */
    public void victory(){
        setState(new VictoryState(this));
        System.out.println("You Win! Congratulations!");
    }

    /**
     * Handles the event when player is defeated
     * Either by time running out or monster hitting player
     */
    public void defeat() {
        setState(new DefeatState(this));
        System.out.println("Time's Up! Game Over.");
    }

    // ========== Getters and Setters ==========
    // State
    public void setState(GameState state) {this.state = state;}

    // App Components
    public int level() {return persistencyController.level();}
    public Maze domain() {return domainController.domain();}
    public GameState state() {return state;}
    public Renderer renderer() {return rendererController.renderer();}

    // Controllers
    public InputController inputController() {return inputController; }
    public TimerController timerController() {return timerController; }

    public PersistencyController persistencyController() {return persistencyController; }
    public WindowController windowController() {return windowController; }
    public RecorderController recorderController() {return recorderController; }
    public DomainController domainController() {return domainController;}
    public RendererController rendererController() {return rendererController;}
}
