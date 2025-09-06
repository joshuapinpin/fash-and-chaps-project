package nz.ac.wgtn.swen225.lc.app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles user input (keyboard and UI actions) and delegates to GameController.
 *
 * @author <Your Name>
 */
public class InputHandler implements KeyListener {
    private final GameController controller;

    public InputHandler(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO: Handle key typed events
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO: Map key presses to game actions and delegate to controller
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO: Handle key released events
    }
}
