package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;

public record HelpState(AppController c) implements GameState{
    public HelpState{
        c.timerController().pause();
    }
    public void exit(AppController c){c.exitGame();}
    public void continueGame(AppController c){c.continueGame();}

    public static String name(){return "HelpState";}
}
