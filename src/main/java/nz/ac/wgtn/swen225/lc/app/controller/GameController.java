package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.app.state.*;
import nz.ac.wgtn.swen225.lc.app.gui.*;
import nz.ac.wgtn.swen225.lc.app.util.*;
import nz.ac.wgtn.swen225.lc.app.util.Renderer;

/**
 * Central controller for game logic and flow.
 * Handles user input, updates the domain model, and triggers view updates.
 * Coordinates between Model (Domain), View (Renderer), and other controllers.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class GameController {
    // MODEL  (Domain module)
    private Domain domain; // Reference to the domain model

    // VIEW (Renderer module)
    private Renderer renderer; // Reference to the renderer/view

    // CONTROLLER Components
    private GameWindow window; // Reference to the main application window
    private GameState state;
    private InputController inputController;
    private TimerController timerController;

    // GAME MANAGEMENT Components
    // Reference to persistence object
    // Reference to recorder object

    public GameController() {
        initialiseControllerComponents();
        startNewGame(1);
    }

    private void initialiseControllerComponents() {
        // Initialize domain model, renderer, and controllers
        domain = Domain.of();
        renderer = Renderer.of();

        var self = this;
        inputController = new InputController(self);
        timerController = new TimerController(self);
        window = new GameWindow(self, inputController);
    }

    /**
     * Handles a user input (e.g., move, pause, save, etc).
     */
    public void handleInput(Input input) {
        if(input == Input.MOVE_UP) window.updateStatusPanel(0, -50);
        else if(input == Input.MOVE_DOWN) window.updateStatusPanel(0, 50);
        else if(input == Input.MOVE_LEFT) window.updateStatusPanel(-50, 0);
        else if(input == Input.MOVE_RIGHT) window.updateStatusPanel(50, 0);
//        else if(input == Input.PAUSE) window.showPauseDialog();
//        else if(input == Input.RESUME) showMessage("Resuming game...", "Resume");
//        else if(input == Input.SAVE) saveGame();
//        else if(input == Input.LOAD_LEVEL_1) startNewGame(1);
//        else if(input == Input.LOAD_LEVEL_2) startNewGame(2);
//        else if(input == Input.EXIT) exitGame();

    }

    // ===== UPDATING VIEW =====

    private void updateView(){
        if(domain == null || renderer == null){
            showError("Cannot update view: Domain or Renderer is null.");
            return;
        }

        // Tell renderer to renderer current domain state
//        renderer.render();

        // Update UI status sdisplay
        window.updateStatus();
    }

    // ===== HANDLING GAME FUNCTIONALITY CHANGES =====

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
        if(timerController != null) timerController.stopTimer();
        System.exit(0);
    }

    private void showError(String message) {window.showErrorDialog(message);}
    private void showMessage(String message, String title) {window.showMessageDialog(message, title);}
}
