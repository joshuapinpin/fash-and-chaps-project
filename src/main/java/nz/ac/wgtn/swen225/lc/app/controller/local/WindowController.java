package nz.ac.wgtn.swen225.lc.app.controller.local;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.gui.layout.*;
import nz.ac.wgtn.swen225.lc.app.gui.screen.*;

public class WindowController {
    AppController c;
    AppWindow w;


    // Screens
    private StartScreen startScreen;
    private PlayScreen playScreen;
    private PauseScreen pauseScreen;
    private VictoryScreen victoryScreen;
    private DefeatScreen defeatScreen;
    private HelpScreen helpScreen;

    // Play Layout Panels
    private TitlePanel titlePanel;
    private MenuPanel menuPanel;
    private LeftPanel leftPanel;
    private RightPanel rightPanel;
    // private GameLayeredPane gamePane;


    public WindowController(AppController c, AppWindow w){
        this.c = c;
        this.w = w;
    }

    // ========== GETTERS ==========
    public AppWindow window() {return w;}
}
