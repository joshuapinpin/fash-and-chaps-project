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
    float playVolume = -20f;

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
        resetMusic();
        updateMaze(c.domainController());
    }

    /**
     * Updates the GUI components based on the current domain state.
     * Called after any change in the domain (e.g., player move, level change).
     * @param dc DomainController
     */
    public void updateMaze(DomainController dc){
        if(renderer == null || dc.domain() == null){
            throw new RuntimeException("Cannot update GUI: Renderer or Domain is null.");
        }

        // Update Maze
        mazePanel.setAllTiles(dc.tileGrid(), dc.player(), dc.monsters());
        mazePanel.repaint();
    }

    /**
     * Play background music in a loop.
     */
    public void playMusic(){
        stopMusic(); // ensures no overlap
        bgMusic.playBackgroundMusic(playVolume);
    }

    /**
     * Stop background music.
     */
    public void stopMusic(){
        try{ bgMusic.stopBackgroundMusic(); }
        catch(NullPointerException e){}
    }

    /**
     * Restart background music from the beginning.
     */
    public void resetMusic(){
        bgMusic.restartBackgroundMusic();
    }

    /**
     * Play victory sound effect
     */
    public void playVictorySound(){
        stopMusic();
        LoadingSounds.VictorySound.playSoundEffect(playVolume);
    }

    /**
     * Play defeat sound effect
     */
    public void playDefeatSound(){
        stopMusic();
        LoadingSounds.LosingSound.playSoundEffect(playVolume);
    }

    /**
     * Play drowning sound effect
     */
    public void playDrowningSound(){
        LoadingSounds.PlayerDrownSound.playSoundEffect(playVolume);
    }

    // ===== Getters and Setters =====
    public Renderer renderer(){return renderer;}
    public Drawable mazePanel(){return mazePanel;}
}
