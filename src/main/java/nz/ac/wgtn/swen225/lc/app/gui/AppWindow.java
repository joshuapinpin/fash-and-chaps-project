package nz.ac.wgtn.swen225.lc.app.gui;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.controller.local.InputController;
import nz.ac.wgtn.swen225.lc.app.controller.module.RecorderController;
import nz.ac.wgtn.swen225.lc.app.controller.local.TimerController;
import nz.ac.wgtn.swen225.lc.app.gui.screen.DefeatScreen;
import nz.ac.wgtn.swen225.lc.app.gui.screen.PlayScreen;
import nz.ac.wgtn.swen225.lc.app.gui.screen.StartScreen;
import nz.ac.wgtn.swen225.lc.app.gui.screen.VictoryScreen;
import nz.ac.wgtn.swen225.lc.app.state.*;

/**
 * Main application window/frame. Contains UI components and embeds the game panel from renderer.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class AppWindow extends JFrame {
    // Game Window Fields
    public static final int SQUARE_SIZE = 60; // 60 pixels
    public static final int WINDOW_WIDTH = SQUARE_SIZE * 21;
    public static final int WINDOW_HEIGHT = SQUARE_SIZE * 13;
    public static final int MAZE_SIZE = SQUARE_SIZE * 9;
    public static final int HEADER_HEIGHT = SQUARE_SIZE * 2;

    // Controllers
    private final AppController c; // Reference to AppController
    private InputController inputController;
    private TimerController timerController;
    private RecorderController recorderController;

    // UI COMPONENTS
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private List<JPanel> allPanels;
    private StartScreen startScreenPanel;
    private PlayScreen playScreenPanel;
    private VictoryScreen victoryScreenPanel;
    private DefeatScreen defeatScreenPanel;

    /**
     * Constructor to initialize the main application window.
     * @param controller
     */
    public AppWindow(AppController controller) {
        // TODO: Set up window, menus, status bar, and embed MazePanel
        super("Fash and Chaps :D");
        this.c = controller;
        //setupScreens();
        setupWindow();
    }
    // ===== SETUP METHODS =====
    private void setupScreens(){
        // Using a CardLayout to switch between different screens
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize all screen panels
        startScreenPanel = new StartScreen(c);
        playScreenPanel = new PlayScreen(c);
        victoryScreenPanel = new VictoryScreen(c);
        defeatScreenPanel = new DefeatScreen(c);
        allPanels = List.of(startScreenPanel, playScreenPanel, victoryScreenPanel, defeatScreenPanel);

        // Add all panels to the main panel with a unique name for each
        mainPanel.add(startScreenPanel, StartState.name());
        mainPanel.add(playScreenPanel, PlayState.name());
        mainPanel.add(victoryScreenPanel, VictoryState.name());
        mainPanel.add(defeatScreenPanel, DefeatState.name());
        setContentPane(mainPanel);
    }

    private void setupWindow(){
        addKeyListener(c.inputController());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        pack();
        setFocusable(true);
        requestFocusInWindow();
        setResizable(false);
        setVisible(true);
    }

    // ===== INTERACTIONS WITH CONTROLLER =====
    /**
     * Update the entire window (all panels).
     */
    public void updateWindow(){
        allPanels.forEach(panel -> {
            if(panel instanceof GamePanel updatable) updatable.updatePanel();
        });
    }

    /**
     * Show a specific screen based on the screen name.
     * Should be called when the game state changes.
     * @param screenName Name of the screen to show (e.g., "Start", "Play", "Victory", "Defeat").
     */
    public void showScreen(String screenName){
        cardLayout.show(mainPanel, screenName);
    }

    /**
     * Show Info dialog
     */
    public void displayInfo(boolean doShow) {
//        if(doShow) playScreenPanel.showInfo();
//        else playScreenPanel.hideInfo();
    }

    /**
     * Update the status bar with current game information.
     */
    // TODO: Must decide what things are needed to be updated in the status bar
    public void updateStatus() {
        // TODO: Update status bar with current game info
    }
}
