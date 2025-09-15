package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.app.util.Renderer;
import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.controller.InputController;

import javax.swing.*;
import javax.swing.JPanel;
import java.awt.*;

/**
 * Main application window/frame. Contains UI components and embeds the game panel from renderer.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class GameWindow extends JFrame {
    // Game Window Fields
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    // Controllers
    private GameController controller; // Reference to GameController
    private InputController inputController;

    // Panels
    private JPanel rootPanel;
    private JPanel statusPanel; // Reference to StatusPanel
    private JPanel gamePanel;// Reference to GamePanel (from renderer)
    private JPanel menuPanel;

    public GameWindow(GameController controller, InputController inputController) {
        // TODO: Set up window, menus, status bar, and embed MazePanel
        super("Fash and Chaps :D");
        this.controller = controller;
        this.inputController = inputController;
        setupWindow();
        setupPanels();
    }

    /**
     * Sets up the main game window (JFrame) properties.
     */
    private void setupWindow(){
        addKeyListener(inputController);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Using absolute positioning
        getContentPane().setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        pack();
        setFocusable(true);
        requestFocusInWindow();
//        setResizable(false);
        setVisible(true);
        System.out.println("Initialised Game Window (JFrame)");
    }

    private void setupPanels(){
        rootPanel = new RootPanel();
        this.add(rootPanel);
        System.out.println("Initialised Root Panel");

        statusPanel = new StatusPanel(this);
        rootPanel.add(statusPanel);
        System.out.println("Initialised Panels");

        menuPanel = new MenuPanel(this);
        rootPanel.add(menuPanel);
        System.out.println("Initialised Menu Panel");

        gamePanel = new GamePanel(this);
        rootPanel.add(gamePanel);
        System.out.println("Initialised Game Panel");

    }

    // ===== INTERACTIONS WITH CONTROLLER =====

    public void showPauseDialog() {
    }

    public void showHelpDialog() {
    }
    public void removePauseDialog() {
    }

    public void showErrorDialog(String message) {
    }

    public void showMessageDialog(String message, String title) {
    }

    public void setRenderer(Renderer renderer) {
    }

    // TODO: Must decide what things are needed to be updated in the status bar
    public void updateStatus() {
        // TODO: Update status bar with current game info
    }

    // Getters
    public static int getWindowWidth() {return WINDOW_WIDTH;}
    public static int getWindowHeight() {return WINDOW_HEIGHT;}
    public static int getGamePanelWidth() {return GamePanel.PANEL_WIDTH;}
    public static int getGamePanelHeight() {return GamePanel.PANEL_HEIGHT;}


}
