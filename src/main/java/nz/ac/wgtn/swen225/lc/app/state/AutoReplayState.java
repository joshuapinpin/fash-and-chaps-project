package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.domain.Direction;

/**
 * State when the game is in auto replay mode.
 * @author Joshua Pinpin (300662880)
 */
public record AutoReplayState(AppController c) implements GameState{
    public AutoReplayState{
        System.out.println("Entered: " + name());
        c.timerController().recorderMode();
    }
    public void moveUp(AppController c) { c.movePlayer(Direction.UP);}
    public void moveDown(AppController c) { c.movePlayer(Direction.DOWN);}
    public void moveLeft(AppController c) { c.movePlayer(Direction.LEFT);}
    public void moveRight(AppController c) { c.movePlayer(Direction.RIGHT);}
    public void startNewGame(AppController c, int level) { c.startNewGame(level);}
    public void exit(AppController c) { c.exitGame();}

    public static String name(){return "AutoReplayState";}

}
