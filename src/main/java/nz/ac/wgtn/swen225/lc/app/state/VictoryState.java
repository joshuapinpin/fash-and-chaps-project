package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.controller.TimerController;

public record VictoryState(TimerController timerController) implements GameState {
    public VictoryState{
        timerController.pause();
    }
    public void exit(GameController c) { c.exitGame(); }
    public void startNewGame(GameController c, int level) { c.startNewGame(level); }
}
