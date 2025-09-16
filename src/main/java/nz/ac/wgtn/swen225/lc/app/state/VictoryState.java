package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.util.Input;

import java.util.Map;
import java.util.Set;

public class VictoryState implements GameState {
    private static final Set<Input> bannedInputs = Set.of(
            Input.MOVE_UP, Input.MOVE_DOWN, Input.MOVE_LEFT, Input.MOVE_RIGHT,
            Input.PAUSE, Input.ESCAPE, Input.SAVE
    );

    @Override
    public void enterState(GameController controller) {

    }

    @Override
    public void exitState(GameController controller) {

    }

    @Override
    public void handleInput(GameController controller, Input input) {
        Map<Input, Runnable> inputRunnableMap = controller.getInputRunnableMap();
        if(inputRunnableMap.containsKey(input) & !bannedInputs.contains(input)) {
            inputRunnableMap.get(input).run();
        }
        else System.out.println("Entered input is not allowed in Victory State");
    }
}
