package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.app.gui.GameWindow;
import nz.ac.wgtn.swen225.lc.app.util.Input;

public interface GameController {
    void handleInput(Input input);
    void startNewGame(int level);
    void saveGame();
    void loadGame();
    void pauseGame();
    void resumeGame();
    void exitGame();
    void timeUp();
    GameWindow getGameWindow();
}
