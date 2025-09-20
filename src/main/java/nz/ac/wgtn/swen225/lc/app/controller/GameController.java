package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.state.GameState;
import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.domain.Direction;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;

public interface GameController {
    static GameController of() {
        return App.getInstance();
    }
    void handleInput(Input input);

    // States
    void setState(GameState state);
    GameState getState();

    // Game Actions
    void movePlayer(Direction dir);
    void startNewGame(int level);
    void saveGame();
    void loadGame();
    void pauseGame();
    void resumeGame();
    void continueGame();
    void exitGame();
    void timeUp();

    // Controller Componentws
    AppWindow getGameWindow();
    Maze getDomain();
    Renderer getRenderer();
}
