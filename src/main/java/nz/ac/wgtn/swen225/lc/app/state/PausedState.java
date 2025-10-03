package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.controller.TimerController;

public record PausedState(TimerController timerController) implements GameState {
    public PausedState{
        timerController.pause();
    }
    public void resume(GameController c){c.resumeGame();}
    public void save(GameController c){c.saveGame();}
    public void startNewGame(GameController c, int level){c.startNewGame(level);}
    public void exit(GameController c){c.exitGame();}
    public void continueGame(GameController c){c.continueGame();}

    public static String name(){return "PausedState";}

}
