package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.app.state.*;
import nz.ac.wgtn.swen225.lc.app.gui.*;
import nz.ac.wgtn.swen225.lc.app.util.*;
import nz.ac.wgtn.swen225.lc.app.util.Renderer;
import nz.ac.wgtn.swen225.lc.domain.Direction;

/**
 * Central controller for game logic and flow.
 * Handles user input, updates the domain model, and triggers view updates.
 * Coordinates between Model (Domain), View (Renderer), and other controllers.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class AppController implements GameController {
    // MODEL  (Domain module)
    private Domain domain; // Reference to the domain model

    // VIEW (Renderer module)
    private Renderer renderer;// Reference to the renderer/view

    // CONTROLLER Components
    private GameWindow window; // Reference to the main application window
    private GameState state;
    private InputController inputController;
    private TimerController timerController;

    // GAME MANAGEMENT Components
    // Reference to persistence object
    // Reference to recorder object

    // Constructor with Singleton Pattern
    private static AppController INSTANCE;
    private AppController() {
        initialiseControllerComponents();
        startNewGame(1);
    }
    public static AppController getInstance() {
        if(INSTANCE == null) INSTANCE = new AppController();
        return INSTANCE;
    }

    private void initialiseControllerComponents() {
        // Initialize domain model, renderer, and controllers
        domain = Domain.of();
        renderer = Renderer.of();

        inputController = new InputController(this);
        timerController = new TimerController(this);
        window = new GameWindow(this, inputController);
    }

    // ========== Game Controller Implementation ==========
    /**
     * Handles a user input (e.g., move, pause, save, etc).
     */
    public void handleInput(Input input) {
        switch(input){
            case MOVE_UP -> {
                domain.move(Direction.UP);
                updateView();
            }
            case MOVE_DOWN -> {
                domain.move(Direction.DOWN);
                updateView();
            }
            case MOVE_LEFT -> {
                domain.move(Direction.LEFT);
                updateView();
            }
            case MOVE_RIGHT -> {
                domain.move(Direction.RIGHT);
                updateView();
            }
            case PAUSE -> pauseGame();
            case RESUME -> resumeGame();
            case SAVE -> saveGame();
            case LOAD_LEVEL_1 -> startNewGame(1);
            case LOAD_LEVEL_2 -> startNewGame(2);
            case EXIT -> exitGame();
            case ESCAPE -> window.removePauseDialog();
            default -> showError("Unhandled input: " + input);
        }
    }

    private void updateView(){
        if(domain == null || renderer == null){
            showError("Cannot update view: Domain or Renderer is null.");
            return;
        }

        // Tell renderer to renderer current domain state
//        renderer.render();

        // Update UI status display
        window.updateStatus();
    }

    /**
     * Starts a new game at the given level.
     */
    public void startNewGame(int level) {


    }

    /**
     * Pauses the game.
     */
    public void pauseGame() {
        // TODO: Implement pause logic

    }

    /**
     * Resumes the game.
     */
    public void resumeGame() {
        // TODO: Implement resume logic

    }

    /**
     * Saves the current game state.
     */
    public void saveGame(){
        if(domain == null){showError("No game to save!"); return;}
        //TODO: get Persistence to create a "save current game" method
    }

    public void loadGame(){
        // TODO: get Persistence to create a "load saved game" method, which returns a Domain object
    }

    /**
     * Exits the current game.
     */
    public void exitGame() {

    }

    public void timeUp() {
    }

    @Override
    public GameWindow getGameWindow() {return window;}


    private void showError(String message) {window.showErrorDialog(message);}
    private void showMessage(String message, String title) {window.showMessageDialog(message, title);}

}
