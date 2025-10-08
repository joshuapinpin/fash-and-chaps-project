package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;

public record StartState(AppController c) implements GameState{
    public StartState{
        c.windowController().changeScreen(name());
        c.timerController().pause();
        c.rendererController().stopMusic();
    }
    public void exit(AppController c) { c.exitGame(); }
    public void startNewGame(AppController c, int level) { c.startNewGame(level); }

    public static String name(){return "StartState";}
}
