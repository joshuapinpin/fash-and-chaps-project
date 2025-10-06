package nz.ac.wgtn.swen225.lc.app.controller.module;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.controller.Controller;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;
import nz.ac.wgtn.swen225.lc.renderer.imgs.Drawable;
import nz.ac.wgtn.swen225.lc.renderer.sounds.LoadingSounds;

public class RendererController implements Controller {
    AppController c;
    Renderer renderer;
    Drawable mazePanel;
    LoadingSounds bgMusic;
    float playVolume = -40f;

    /**
     * Constructor initializes the renderer with the current domain state.
     * @param c AppController
     * @param dc DomainController
     */
    public RendererController(AppController c, DomainController dc){
        this.c = c;
        this.renderer = new Renderer(dc.tileGrid(), dc.player(), dc.monsters());
        this.mazePanel = renderer.getPanel();
        renderer.setDimensions(AppWindow.MAZE_SIZE, AppWindow.MAZE_SIZE);
        bgMusic = LoadingSounds.BackgroundSound;
    }

    /**
     * Called when a new game starts to initialize the renderer with the current domain state.
     */
    @Override
    public void atNewGame(){
        updateMaze(c.domainController());
    }

    /**
     * Updates the GUI components based on the current domain state.
     * Called after any change in the domain (e.g., player move, level change).
     * @param domainController DomainController
     */
    public void updateMaze(DomainController domainController){
        if(renderer == null || domainController.domain() == null){
            throw new RuntimeException("Cannot update GUI: Renderer or Domain is null.");
        }

        // Update Maze
        mazePanel.setAllTiles(domainController.tileGrid(), domainController.player(), domainController.monsters());
        mazePanel.repaint();
    }

    /**
     * Play background music in a loop.
     */
    public void playMusic(){
        bgMusic.playBackgroundMusic(playVolume);
    }

    /**
     * Stop background music.
     */
    public void stopMusic(){
        try{ bgMusic.stopBackgroundMusic(); }
        catch(NullPointerException e){}
    }

    // ===== Getters and Setters =====
    public Renderer renderer(){return renderer;}
    public Drawable mazePanel(){return mazePanel;}
}
