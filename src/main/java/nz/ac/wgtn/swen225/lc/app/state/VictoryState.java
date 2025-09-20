package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;

public record VictoryState() implements GameState {
    public void exit(GameController c) { c.exitGame(); }
    public void startNewGame(GameController c, int level) { c.startNewGame(level); }
}
