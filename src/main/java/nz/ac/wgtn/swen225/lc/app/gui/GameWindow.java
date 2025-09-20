package nz.ac.wgtn.swen225.lc.app.gui;

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
    public static final int WINDOW_WIDTH = 1260;
    public static final int WINDOW_HEIGHT = 720;
    public static final int SQUARE_SIZE = ((WINDOW_HEIGHT / 4) * 3) / 9; // 60 pixels

    // Controllers
    private GameController controller; // Reference to GameController
    private InputController inputController;

    // PANELS
    // Top
    private JPanel titlePanel;
    
    // Middle
    private JPanel rootPanel;
    private JPanel gamePanel;// Reference to MazePanel (from renderer)
    
    // Left
    private JPanel leftPanel; // Reference to LeftPanel
    private JPanel levelPanel;
    private JPanel timerPanel;
    
    // Right
    private JPanel rightPanel;
    private JPanel keysPanel;
    private JPanel treasurePanel;
    
    // Bottom
    private JPanel menuPanel;
    

    public GameWindow(GameController controller, InputController inputController) {
        // TODO: Set up window, menus, status bar, and embed MazePanel
        super("Fash and Chaps :D");
        this.controller = controller;
        this.inputController = inputController;
        setupWindow();
        setupPanels();
        //setupDialogs();

    }

    /**
     * Sets up the main game window (JFrame) properties.
     */
    private void setupWindow(){
        addKeyListener(inputController);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        pack();
        setLayout(new BorderLayout());
        setFocusable(true);
        requestFocusInWindow();
        setResizable(false);
        setVisible(true);
        System.out.println("Initialised Game Window (JFrame)");
    }

    private void setupPanels(){
        titlePanel = new TitlePanel(controller);
        menuPanel = new MenuPanel(controller);
        rootPanel = new RootPanel(controller);
        leftPanel = new LeftPanel(controller);
        rightPanel = new RightPanel(controller);
        gamePanel = setupMazePanel();

        add(titlePanel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.SOUTH);
        add(rootPanel);
        rootPanel.add(leftPanel);
        rootPanel.add(gamePanel);
        rootPanel.add(rightPanel);

        System.out.println("Initialised Panels");
    }

    private JPanel setupMazePanel(){
        JPanel panel = new MazePanel(controller);
        int panelSize = (GameWindow.WINDOW_HEIGHT / 4) * 3;
        panel.setPreferredSize(new Dimension(panelSize, panelSize));
        panel.setMinimumSize(new Dimension(panelSize, panelSize));
        panel.setMaximumSize(new Dimension(panelSize, panelSize));
        panel.setBorder(BorderFactory.createLineBorder(new Color(0x362702), 5));
        return panel;
        //return controller.getRenderer().getPanel();
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


    // TODO: Must decide what things are needed to be updated in the status bar
    public void updateStatus() {
        // TODO: Update status bar with current game info
    }

    public void updateInventory(){

    }

    // Getters
    public static int getWindowWidth() {return WINDOW_WIDTH;}
    public static int getWindowHeight() {return WINDOW_HEIGHT;}
    public static int getGamePanelWidth() {return MazePanel.PANEL_WIDTH;}
    public static int getGamePanelHeight() {return MazePanel.PANEL_HEIGHT;}


}
