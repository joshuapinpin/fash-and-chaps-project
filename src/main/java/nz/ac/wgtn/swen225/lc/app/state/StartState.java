package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;

/**
 * The state when the game is at the start screen.
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public record StartState(AppController c) implements GameState{
    public StartState{
        c.windowController().changeScreen(name());
        c.timerController().pause();
        c.rendererController().stopMusic();
    }
    public void exit(AppController c) { c.exitGame(); }
    public void resume(AppController c) { c.resumeGame();}
    public void startNewGame(AppController c, int level) { c.startNewGame(level); }

    public static String name(){return "StartState";}
}
