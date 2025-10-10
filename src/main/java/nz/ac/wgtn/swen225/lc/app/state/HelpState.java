package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;

/**
 * Help state.
 * Displays instructions on how to play the game, including controls and game mechanics.
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public record HelpState(AppController c) implements GameState{
    public HelpState{
        c.windowController().changeScreen(name());
        c.rendererController().stopMusic();
    }
    public void exit(AppController c){c.exitGame();}
    public static String name(){return "HelpState";}
}
