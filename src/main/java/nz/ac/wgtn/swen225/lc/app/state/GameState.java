package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.util.Input;

import java.util.HashMap;

public interface GameState{
    /**
     * Handles input based on the current game state.
     * @param controller GameController
     * @param input Input
     */
    default void handleInput(GameController controller, Input input) {
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
    default void moveUp(GameController c){throw new UnsupportedOperationException();}
    default void moveDown(GameController c){throw new UnsupportedOperationException();}
    default void moveLeft(GameController c){throw new UnsupportedOperationException();}
    default void moveRight(GameController c){throw new UnsupportedOperationException();}
    default void pause(GameController c){throw new UnsupportedOperationException();}
    default void resume(GameController c){throw new UnsupportedOperationException();}
    default void save(GameController c){throw new UnsupportedOperationException();}
    default void startNewGame(GameController c, int level){throw new UnsupportedOperationException();}
    default void exit(GameController c){throw new UnsupportedOperationException();}
    default void continueGame(GameController c){throw new UnsupportedOperationException();}
}
