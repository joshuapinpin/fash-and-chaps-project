package nz.ac.wgtn.swen225.lc.app.controller.local;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.controller.Controller;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.gui.screen.*;
import nz.ac.wgtn.swen225.lc.app.gui.layout.*;
import nz.ac.wgtn.swen225.lc.app.gui.logic.*;
import nz.ac.wgtn.swen225.lc.app.state.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static nz.ac.wgtn.swen225.lc.app.gui.AppWindow.MAZE_SIZE;

public class WindowController implements Controller {
    AppController c;
    AppWindow w;

    // Game Panel
    private JPanel gamePanel;

    // Game Logic Panels
    private KeysPanel keysPanel;
    private LevelPanel levelPanel;
    private TimerPanel timerPanel;
    private TreasurePanel treasurePanel;
    private JPanel infoPanel;

    // List of all groups of panels
    private List<JPanel> logicPanels;

    /**
     * Constructor for WindowController.
     * @param c AppController
     */
    public WindowController(AppController c){
        this.c = c;
        this.w = new AppWindow(c);
        setupGamePanels();
    }

    private void setupGamePanels(){
        levelPanel = w.playScreen().leftPanel().levelPanel();
        timerPanel = w.playScreen().leftPanel().timerPanel();
        keysPanel = w.playScreen().leftPanel().keysPanel();
        treasurePanel = w.playScreen().leftPanel().treasurePanel();

        logicPanels = List.of(levelPanel, timerPanel, keysPanel, treasurePanel);

        assert logicPanels.stream()
                .filter(Objects::nonNull)
                .count() == logicPanels.size() : "One or more logic panels are null!";

        infoPanel = w.playScreen().infoPanel();
        gamePanel = w.playScreen().gamePanel();
    }

    // ====== CONTROLLER METHODS ======

    /**
     * Called when new game state is entered.
     * Hides the info panel based on the new state.
     * Initializes the window if entering a new game state.
     */
    @Override
    public void atNewGame(){
        displayInfo(false);
        initialiseWindow();
    }

    /**
     * Show a specific screen based on the screen name.
     * Should be called when the game state changes.
     * @param screenName Name of the screen to show (e.g., "Start", "Play", "Victory", "Defeat").
     */
    public void changeScreen(String screenName){
        w.showScreen(screenName);
    }

    /**
     * Show Info Panel
     * @param doShow true to show, false to hide
     */
    public void displayInfo(boolean doShow) {
        infoPanel.setVisible(doShow);
    }

    /**
     * Initialise the window with game info.
     * Should be called when a new game starts.
     */
    public void initialiseWindow(){
        initialiseTreasure();
        initialiseKeys();
        initialiseTimer();
        updateWindow();
    }

    /**
     * Update the entire window (all panels).
     */
    public void updateWindow(){
        updateLevel();
        updateTimer();
        updateKeys();
        updateTreasure();
        updateGamePanel();
        logicPanels.forEach(JPanel::repaint);
    }

    private void initialiseTreasure() {
        int maxTreasures = c.persistencyController().maxTreasures();
        treasurePanel.initialisePanelInfo(maxTreasures);
    }

    private void initialiseKeys() {
        int maxKeys = c.persistencyController().maxKeys();
        keysPanel.initialisePanelInfo(maxKeys);
    }

    private void initialiseTimer() {
        int startTime = c.persistencyController().maxTime();
        timerPanel.initialisePanelInfo(startTime);
    }

    private void updateLevel(){
        int level = c.level();
        levelPanel.updatePanel(level);
    }

    private void updateTimer(){
        int timeLeft = c.timerController().getTimeLeft();
        timerPanel.updatePanel(timeLeft);
    }

    private void updateKeys(){
        keysPanel.updatePanel(1);
    }

    private void updateTreasure(){
        int treasuresCollected = c.domainController().player().getTreasuresCollected();
        treasurePanel.updatePanel(treasuresCollected);
    }

    private void updateGamePanel(){
        gamePanel.repaint();
    }


    // ========== GETTERS ==========
    public AppWindow window() {return w;}
}
