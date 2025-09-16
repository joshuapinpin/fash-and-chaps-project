package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.app.state.*;
import nz.ac.wgtn.swen225.lc.app.gui.*;
import nz.ac.wgtn.swen225.lc.app.util.*;
import nz.ac.wgtn.swen225.lc.app.util.Renderer;
import nz.ac.wgtn.swen225.lc.domain.Direction;

import java.util.Map;
import java.util.HashMap;

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
    private Map<Input, Runnable> inputMap;

    // GAME MANAGEMENT Components
    // Reference to persistence object
    // Reference to recorder object

    // Constructor with Singleton Pattern
    private static AppController INSTANCE;
    private AppController() {
        initialiseControllerComponents();
        initialiseInputMap();
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

        //inputController = new InputController(this);
        timerController = new TimerController(this);
        window = new GameWindow(this, inputController);
    }

    private void initialiseInputMap(){
        // Initialize input to input map
        inputMap = new HashMap<>();
        inputMap.put(Input.MOVE_UP, this::moveUp);
        inputMap.put(Input.MOVE_DOWN, this::moveDown);
        inputMap.put(Input.MOVE_LEFT, this::moveLeft);
        inputMap.put(Input.MOVE_RIGHT, this::moveRight);
        inputMap.put(Input.PAUSE, this::pauseGame);
        inputMap.put(Input.RESUME, this::resumeGame);
        inputMap.put(Input.SAVE, this::saveGame);
        inputMap.put(Input.LOAD_LEVEL_1, () -> startNewGame(1));
        inputMap.put(Input.LOAD_LEVEL_2, () -> startNewGame(2));
        inputMap.put(Input.EXIT, this::exitGame);
        inputMap.put(Input.ESCAPE, window::removePauseDialog);
    }

    // ========== Game Controller Implementation ==========
    /**
     * Handles a user input (e.g., move, pause, save, etc).
     */
    public void handleInput(Input input) {
        if(inputMap.containsKey(input)) inputMap.get(input).run();
        else throw new IllegalArgumentException("Invalid input");
    }

    private void moveUp(){domain.move(Direction.UP); updateView();}
    private void moveDown(){domain.move(Direction.DOWN); updateView();}
    private void moveLeft(){domain.move(Direction.LEFT); updateView();}
    private void moveRight(){domain.move(Direction.RIGHT); updateView();}

    // ===== View Methods =====
    /**
     * Updates the view based on the current state of the domain model.
     */
    private void updateView(){
        if(domain == null || renderer == null){
            showError("Cannot update view: Domain or Renderer is null.");
            return;
        }

        // Tell renderer the current domain state
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
