package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;

public record PausedState(AppController c) implements GameState {
    public PausedState{
        c.timerController().pause();
    }
    public void resume(AppController c){c.resumeGame();}
    public void pause(AppController c){c.continueGame();}
    public void save(AppController c){c.saveGame();}
    public void startNewGame(AppController c, int level){c.startNewGame(level);}
    public void exit(AppController c){c.exitGame();}
    public void continueGame(AppController c){c.continueGame();}

    public static String name(){return "PausedState";}

}
