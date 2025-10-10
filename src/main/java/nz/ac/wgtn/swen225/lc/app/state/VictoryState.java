package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;

/**
 * The state when the player has won the game.
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public record VictoryState(AppController c) implements GameState {
    public VictoryState{
        c.windowController().changeScreen(name());
        c.timerController().pause();
        c.rendererController().stopMusic();
        c.rendererController().playVictorySound();
    }
    public void exit(AppController c) { c.exitGame(); }
    public void resume(AppController c) { c.resumeGame();}
    public void startNewGame(AppController c, int level) { c.startNewGame(level); }

    public static String name(){return "VictoryState";}

}
