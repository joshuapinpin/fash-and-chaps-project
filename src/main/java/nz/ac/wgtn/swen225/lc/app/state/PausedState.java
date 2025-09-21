package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;

public record PausedState() implements GameState {
    public void resume(GameController c){c.resumeGame();}
    public void save(GameController c){c.saveGame();}
    public void startNewGame(GameController c, int level){c.startNewGame(level);}
    public void exit(GameController c){c.exitGame();}
    public void continueGame(GameController c){c.continueGame();}
}
