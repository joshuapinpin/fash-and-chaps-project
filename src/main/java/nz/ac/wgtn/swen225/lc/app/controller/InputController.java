package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.app.util.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

/**
 * Handles user input (keyboard and UI actions) and delegates to GameController.
 *
 * @author <Your Name>
 */
public class InputController implements KeyListener {
    private final GameController controller;
    private final Map<KeyCombo, KeyAction> actions;

    interface KeyAction {void execute(GameController gameController);}
    record KeyCombo(int keyCode, boolean ctrl) {}

    public InputController(GameController controller) {
        this.controller = controller;
        this.actions = new HashMap<>();
        setupActions();
        System.out.println("InputController initialized.");
    }

    private void setupActions(){
        actions.put(new KeyCombo(KeyEvent.VK_UP, false), c -> c.handleInput(Input.MOVE_UP));
        actions.put(new KeyCombo(KeyEvent.VK_DOWN, false), c-> c.handleInput(Input.MOVE_DOWN));
        actions.put(new KeyCombo(KeyEvent.VK_ESCAPE, false), c -> c.handleInput(Input.MOVE_DOWN));
        actions.put(new KeyCombo(KeyEvent.VK_LEFT, false), c -> c.handleInput(Input.MOVE_LEFT));
        actions.put(new KeyCombo(KeyEvent.VK_RIGHT, false), c -> c.handleInput(Input.MOVE_RIGHT));
        actions.put(new KeyCombo(KeyEvent.VK_SPACE, false), c -> c.handleInput(Input.PAUSE));
        actions.put(new KeyCombo(KeyEvent.VK_R, false), c -> c.handleInput(Input.RESUME));
        actions.put(new KeyCombo(KeyEvent.VK_S, true), c -> c.handleInput(Input.SAVE));
        actions.put(new KeyCombo(KeyEvent.VK_1, true), c -> c.handleInput(Input.LOAD_LEVEL_1));
        actions.put(new KeyCombo(KeyEvent.VK_2, true), c -> c.handleInput(Input.LOAD_LEVEL_2));
        actions.put(new KeyCombo(KeyEvent.VK_X, true), c -> c.handleInput(Input.EXIT));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Map key presses to game actions and delegate to controller
        boolean ctrl = (e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0;
        KeyCombo combo = new KeyCombo(e.getKeyCode(), ctrl);
        KeyAction action = actions.get(combo);

        // Displays the key pressed for debugging on terminal
        var keyText = KeyEvent.getKeyText(e.getKeyCode());
        if (action != null) {
            action.execute(controller);
            System.out.println("Key Pressed: " + keyText + (ctrl ? " (CTRL)" : ""));
        }// else System.out.println("Key Pressed (no action): " + keyText + (ctrl ? " (CTRL)" : ""));
    }
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}


