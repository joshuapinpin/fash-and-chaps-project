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
    private final Map<KeyCombo, KeyAction> inputs;
    private final Set<Integer> pressedKeys = new HashSet<>();

    /**
     * Represents a combination of key code and control modifier.
     */
    private record KeyCombo(int keyCode, boolean ctrl) {}
    private interface KeyAction {void execute(GameController gameController);}

    public InputController(GameController controller) {
        this.controller = controller;
        this.inputs = new HashMap<>();
        setupInputs();
        System.out.println("InputController initialized.");
    }

    private void setupInputs(){
        inputs.put(new KeyCombo(KeyEvent.VK_X, true), c -> c.handleInput(Input.EXIT));
        inputs.put(new KeyCombo(KeyEvent.VK_S, true), c -> c.handleInput(Input.SAVE));
        inputs.put(new KeyCombo(KeyEvent.VK_R, true), c -> c.handleInput(Input.RESUME));
        inputs.put(new KeyCombo(KeyEvent.VK_1, true), c -> c.handleInput(Input.LOAD_LEVEL_1));
        inputs.put(new KeyCombo(KeyEvent.VK_2, true), c -> c.handleInput(Input.LOAD_LEVEL_2));
        inputs.put(new KeyCombo(KeyEvent.VK_UP, false), c -> c.handleInput(Input.MOVE_UP));
        inputs.put(new KeyCombo(KeyEvent.VK_DOWN, false), c-> c.handleInput(Input.MOVE_DOWN));
        inputs.put(new KeyCombo(KeyEvent.VK_LEFT, false), c -> c.handleInput(Input.MOVE_LEFT));
        inputs.put(new KeyCombo(KeyEvent.VK_RIGHT, false), c -> c.handleInput(Input.MOVE_RIGHT));
        inputs.put(new KeyCombo(KeyEvent.VK_SPACE, false), c -> c.handleInput(Input.PAUSE));
        inputs.put(new KeyCombo(KeyEvent.VK_ESCAPE, false), c -> c.handleInput(Input.ESCAPE));
    }

    @Override public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // Only process if not already pressed
        if (pressedKeys.contains(keyCode)) return;
        pressedKeys.add(keyCode);
        // Map key presses to game actions and delegate to controller
        boolean ctrl = (e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0;
        KeyCombo combo = new KeyCombo(keyCode, ctrl);
        KeyAction action = inputs.get(combo);

        // Displays the key pressed for debugging on terminal
        var keyText = KeyEvent.getKeyText(keyCode);
        if (action != null) {
            action.execute(controller);
//            System.out.println("Key Pressed: " + keyText + (ctrl ? " (CTRL)" : ""));
        }
        // else System.out.println("Key Pressed (no action): " + keyText + (ctrl ? " (CTRL)" : ""));
    }
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode()); // removed to allow for reprocessing.
    }
}


