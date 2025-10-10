package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.util.Input;

/**
 * Interface representing different game states.
 * Each state can override methods to handle specific inputs.
 * Default implementations throw UnsupportedOperationException.
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public interface GameState{
    /**
     * Handles input based on the current game state.
     * @param controller AppController
     * @param input Input
     */
    default void handleInput(AppController controller, Input input) {
        switch(input){
            case MOVE_UP -> moveUp(controller);
            case MOVE_DOWN -> moveDown(controller);
            case MOVE_LEFT -> moveLeft(controller);
            case MOVE_RIGHT -> moveRight(controller);
            case PAUSE -> pause(controller);
            case SAVE -> save(controller);
            case RESUME -> resume(controller);
            case LOAD_LEVEL_1 -> startNewGame(controller, 1); // Default to level 1 for new game
            case LOAD_LEVEL_2 -> startNewGame(controller, 2); // Default to level 1 for new game
            case EXIT -> exit(controller);
            case CONTINUE -> continueGame(controller);
            default -> throw new IllegalArgumentException("Unexpected input: " + input);
        }
    }
    /**
     * Default implementations throw UnsupportedOperationException.
     * Override only the methods relevant to the specific state.
     */
    default void moveUp(AppController c){throw new UnsupportedOperationException();}
    default void moveDown(AppController c){throw new UnsupportedOperationException();}
    default void moveLeft(AppController c){throw new UnsupportedOperationException();}
    default void moveRight(AppController c){throw new UnsupportedOperationException();}
    default void pause(AppController c){throw new UnsupportedOperationException();}
    default void resume(AppController c){throw new UnsupportedOperationException();}
    default void save(AppController c){throw new UnsupportedOperationException();}
    default void startNewGame(AppController c, int level){throw new UnsupportedOperationException();}
    default void exit(AppController c){throw new UnsupportedOperationException();}
    default void continueGame(AppController c){throw new UnsupportedOperationException();}
}
