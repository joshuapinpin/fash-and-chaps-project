package nz.ac.wgtn.swen225.lc.app.controller.module;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;
import nz.ac.wgtn.swen225.lc.renderer.imgs.Drawable;
import nz.ac.wgtn.swen225.lc.renderer.sounds.LoadingSounds;

public class RendererController {
    Renderer renderer;
    Drawable mazePanel;
    LoadingSounds bgMusic;
    float playVolume = -40f;

    /**
     * Constructor initializes the renderer with the current domain state.
     * @param controller AppController
     * @param domainController DomainController
     */
    public RendererController(AppController controller, DomainController domainController){
        this.renderer = new Renderer(domainController.tileGrid(), domainController.player(),
                domainController.monsters());
        this.mazePanel = renderer.getPanel();
        renderer.setDimensions(AppWindow.MAZE_SIZE, AppWindow.MAZE_SIZE);
        bgMusic = LoadingSounds.BackgroundSound;
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
        mazePanel.setAllTiles(domainController.tileGrid(), domainController.player());
        mazePanel.repaint();
    }

    public void playMusic(){
        bgMusic.playBackgroundMusic(playVolume);
    }

    public void stopMusic(){
        bgMusic.stopBackgroundMusic();
    }

    // ===== Getters and Setters =====
    public Renderer renderer(){return renderer;}
    public Drawable mazePanel(){return mazePanel;}
}
