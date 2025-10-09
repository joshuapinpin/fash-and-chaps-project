package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;

public record DefeatState(AppController c) implements GameState {
    public DefeatState {
        c.windowController().changeScreen(name());
        c.timerController().pause();
        c.rendererController().stopMusic();
        c.rendererController().playDefeatSound();
    }
    public void exit(AppController c) { c.exitGame(); }
    public void resume(AppController c) { c.resumeGame();}
    public void startNewGame(AppController c, int level) { c.startNewGame(level); }

    public static String name(){return "DefeatState";}

}
