package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;
import nz.ac.wgtn.swen225.lc.renderer.imgs.Drawable;

public class RendererController {
    Renderer renderer;
    Drawable mazePanel;
    AppWindow window;


    public RendererController(GameController controller, DomainController domainController){
        this.renderer = new Renderer(domainController.tileGrid(), domainController.player());
        renderer.setDimensions(AppWindow.MAZE_SIZE, AppWindow.MAZE_SIZE);
        this.mazePanel = renderer.getPanel();
    }

    public void updateGui(DomainController domainController){
        if(renderer == null || domainController.domain() == null){
            throw new RuntimeException("Cannot update GUI: Renderer or Domain is null.");
        }

        // Update Maze
        mazePanel.setAllTiles(domainController.tileGrid(), domainController.player());
        mazePanel.repaint();

        // Update Supporting GUI Elements
        window.updateWindow();
    }

    public Renderer renderer(){return renderer;}
    public Drawable mazePanel(){return mazePanel;}
    public AppWindow window(){return window;}
    public void setWindow(AppWindow window){this.window = window;}
}
