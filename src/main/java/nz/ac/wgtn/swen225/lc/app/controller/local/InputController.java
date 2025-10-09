package nz.ac.wgtn.swen225.lc.app.controller.local;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.state.AutoReplayState;
import nz.ac.wgtn.swen225.lc.app.state.StepReplayState;
import nz.ac.wgtn.swen225.lc.app.util.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

/**
 * Handles user input (keyboard and UI actions) and delegates to AppController.
 *
 * @author <Your Name>
 */
public class InputController implements KeyListener {
    private final AppController c;
    private final Map<KeyCombo, KeyAction> inputs;
    private final Set<Integer> pressedKeys = new HashSet<>();

    /**
     * Represents a combination of key code and control modifier.
     */
    private record KeyCombo(int keyCode, boolean ctrl) {}
    private interface KeyAction {void execute(AppController gameController);}

    public InputController(AppController controller) {
        this.c = controller;
        this.inputs = new HashMap<>();
        setupInputs();
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
        inputs.put(new KeyCombo(KeyEvent.VK_ESCAPE, false), c -> c.handleInput(Input.CONTINUE));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(c.state() instanceof AutoReplayState
                || c.state() instanceof StepReplayState) return;


        int keyCode = e.getKeyCode();
        if (pressedKeys.contains(keyCode)) return;
        pressedKeys.add(keyCode);

        // Map key presses to game actions and delegate to controller
        boolean ctrl = (e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0;
        KeyCombo combo = new KeyCombo(keyCode, ctrl);
        KeyAction action = inputs.get(combo);

        // Displays the key pressed for debugging on terminal
        var keyText = KeyEvent.getKeyText(keyCode);
        if (action != null) action.execute(c);
    }
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode()); // removed to allow for reprocessing.
    }
}


