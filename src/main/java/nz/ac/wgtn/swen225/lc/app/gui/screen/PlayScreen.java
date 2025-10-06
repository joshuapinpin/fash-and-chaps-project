package nz.ac.wgtn.swen225.lc.app.gui.screen;

import javax.swing.JPanel;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;

public class PlayScreen extends JPanel {
    private AppController c;

    /**
     * Constructor to initialize the main application window.
     * @param c AppController
     */
    public PlayScreen(AppController c){
        this.c = c;
    }
}
