package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.app.state.*;
import nz.ac.wgtn.swen225.lc.app.gui.*;
import nz.ac.wgtn.swen225.lc.app.util.*;
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
public class App implements GameController {
    // MODEL  (Domain module)
    private Maze domain; // Reference to the domain model

    // VIEW (Renderer module)
    private Renderer renderer;// Reference to the renderer/view

    // CONTROLLER Components
    private AppWindow window; // Reference to the main application window
    private GameState state;
    private InputController inputController;
    private TimerController timerController;

    // GAME MANAGEMENT Components
    // Reference to persistence
    // Reference to recorder

    private int level;

    // Constructor with Singleton Pattern
    private static App INSTANCE;
    private App() {
        initialiseControllerComponents();
        startNewGame(1);
    }
    public static App getInstance() {
        if(INSTANCE == null) INSTANCE = new App();
        return INSTANCE;
    }


    private void initialiseControllerComponents() {
        // Initialize domain model, renderer, and controllers
        domain = new Maze(10,9);
        domain.addTiles();

        renderer = new Renderer(domain.getTileGrid(), domain.getPlayer());
        int size = AppWindow.MAZE_SIZE;
        renderer.setDimensions(size, size);

        inputController = new InputController(this);
        timerController = new TimerController(this);
        window = new AppWindow(this, inputController, timerController);
    }


    // ========== Game Controller Implementation ==========

    /**
     * Handles a user input (e.g., move, pause, save, etc).
     * Delegates to the current game state for processing.
     * @param input The user input to handle
     */
    public void handleInput(Input input) {
        if(state == null) throw new RuntimeException("Game state is null.");

        try {state.handleInput(this, input);}
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
        setState(new PlayState());
        this.level = level;
        timerController.startTimer(TimerController.getTimeLimitForLevel(level));
        System.out.println("Starting New Game at Level " + level);
    }

    /**
     * Pauses the game.
     */
    public void pauseGame() {
        // TODO: Implement pause logic
        setState(new PausedState());
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
        setState(new PlayState());
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
        setState(new DeadState());
        System.out.println("Time's Up! Game Over.");
    }

    @Override
    public void startRecording() {
        System.out.println("Started Recording");

    }

    @Override
    public void stopRecording() {
        System.out.println("Stopped Recording");

    }

    @Override
    public void autoPlay() {
        System.out.println("Auto-Playing");

    }

    @Override
    public void stepByStep() {
        System.out.println("Step-By-Step Playing");

    }

    public void setState(GameState state) {this.state = state;}
    public AppWindow getGameWindow() {return window;}
    public GameState getState() {return state;}
    public Maze getDomain() {return domain;}
    public Renderer getRenderer() {return renderer;}
    public int getLevel() {return level;}
}
