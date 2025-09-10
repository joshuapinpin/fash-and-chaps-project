package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.app.util.Action;
import nz.ac.wgtn.swen225.lc.app.state.*;
import nz.ac.wgtn.swen225.lc.app.ui.*;
import nz.ac.wgtn.swen225.lc.app.util.Domain;
import nz.ac.wgtn.swen225.lc.app.util.Renderer;

import javax.swing.*;

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
    private PersistenceController persistenceController;

    private JPanel panel;

    public GameController() {
        initialiseControllerComponents();
        setupMVCBindings();
        startNewGame(1);
    }

    private void initialiseControllerComponents() {
        // Initialize domain model, renderer, and controllers
        domain = Domain.of();
        renderer = Renderer.of();

        var self = this;
        window = new GameWindow(self);
        inputController = new InputController(self);
        persistenceController = new PersistenceController(self);
        timerController = new TimerController(self);

        window.setupInputController(inputController);
    }

    private void setupMVCBindings() {
        // Controller observes and coordinates Model and View
        // When Model changes, Controller updates View
        // When user interacts with View, Controller updates Model

        // Setup bindings between model, view, and controller
        // e.g., domain.addListener(renderer);
        // e.g., renderer.setController(this);
    }

    /**
     * Handles a user action (e.g., move, pause, save, etc).
     */
    public void handleAction(Action action) {
        // TODO: Implement action handling
        if(action == Action.MOVE_UP){}
        if(action == Action.MOVE_DOWN){}
        if(action == Action.MOVE_LEFT){}
        if(action == Action.MOVE_RIGHT){}
        
    }

    // ===== UPDATING VIEW =====

    private void updateView(){
        if(domain == null || renderer == null){
            showError("Cannot update view: Domain or Renderer is null.");
            return;
        }

        // Tell renderer to redner current domain state
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
        persistenceController.saveCurrentGame(domain);
    }

    public void loadGame(){
        Domain loaded = persistenceController.loadSavedGame();
        if(loaded == null){showError("No saved game found!"); return;}

        // Replace current domain with loaded one
        domain = loaded;
//        currentLevel = domain.getCurrentLevel();

        // Update renderer to reflect loaded game state
//        renderer.setModel(domain);
        updateView();

        // ToDo: decide whether it restarts to creates new timer
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
