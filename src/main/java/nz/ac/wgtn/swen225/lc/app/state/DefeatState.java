package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.controller.TimerController;

public record DefeatState(TimerController timerController) implements GameState {
    public DefeatState {
        timerController.pause();
    }
    public void exit(GameController c) { c.exitGame(); }
    public void startNewGame(GameController c, int level) { c.startNewGame(level); }

    public static String name(){return "DefeatState";}

}
