package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.controller.logic.TimerController;

public record DefeatState(AppController c) implements GameState {
    public DefeatState {
        c.window().showScreen(name());
        c.timerController().pause();
    }
    public void exit(AppController c) { c.exitGame(); }
    public void startNewGame(AppController c, int level) { c.startNewGame(level); }

    public static String name(){return "DefeatState";}

}
