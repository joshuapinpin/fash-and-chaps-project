package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.domain.Direction;

public record StepReplayState() implements GameState {
    public void moveUp(GameController c) { c.movePlayer(Direction.UP);}
    public void moveDown(GameController c) { c.movePlayer(Direction.DOWN);}
    public void moveLeft(GameController c) { c.movePlayer(Direction.LEFT);}
    public void moveRight(GameController c) { c.movePlayer(Direction.RIGHT);}
    public void pause(GameController c) { c.pauseGame();}
    public void resume(GameController c) { c.resumeGame();}
    public void save(GameController c) {c.saveGame();}
    public void startNewGame(GameController c, int level) { c.startNewGame(level);}
    public void exit(GameController c) { c.exitGame();}

    public static String name(){return "StepReplayState";}


}
