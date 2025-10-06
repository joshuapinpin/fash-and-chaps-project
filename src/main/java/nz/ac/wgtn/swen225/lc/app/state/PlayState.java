package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.domain.Direction;

/**
 * State representing the game being actively played.
 * @author Joshua Pinpin
 */
public record PlayState(AppController c) implements GameState {
    public PlayState{
        c.windowController().changeScreen(name());
        c.timerController().play();

        // Music
        c.rendererController().stopMusic(); // ensures no overlap
        c.rendererController().playMusic();
    }
    public void moveUp(AppController c) { c.movePlayer(Direction.UP);}
    public void moveDown(AppController c) { c.movePlayer(Direction.DOWN);}
    public void moveLeft(AppController c) { c.movePlayer(Direction.LEFT);}
    public void moveRight(AppController c) { c.movePlayer(Direction.RIGHT);}
    public void pause(AppController c) { c.pauseGame();}
    public void resume(AppController c) { c.resumeGame();}
    public void save(AppController c) {c.saveGame();}
    public void startNewGame(AppController c, int level) { c.startNewGame(level);}
    public void exit(AppController c) { c.exitGame();}

    public static String name(){return "PlayState";}
}
