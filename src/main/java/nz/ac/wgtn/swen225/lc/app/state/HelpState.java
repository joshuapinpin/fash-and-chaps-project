package nz.ac.wgtn.swen225.lc.app.state;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;

public record HelpState(AppController c) implements GameState{
    public HelpState{
        c.windowController().changeScreen(name());
        c.rendererController().stopMusic();
    }
    public void exit(AppController c){c.exitGame();}
    public static String name(){return "HelpState";}
}
