package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.util.Input;

public interface GameState {
    void enterState(GameController controller);
    void exitState(GameController controller);
    void handleInput(GameController controller, Input input);
}
