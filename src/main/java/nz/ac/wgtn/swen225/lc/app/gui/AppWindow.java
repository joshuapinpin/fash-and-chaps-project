package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.controller.InputController;
import nz.ac.wgtn.swen225.lc.app.controller.RecorderController;
import nz.ac.wgtn.swen225.lc.app.controller.TimerController;

import javax.swing.*;
import javax.swing.JPanel;
import java.awt.*;
import java.util.List;

/**
 * Main application window/frame. Contains UI components and embeds the game panel from renderer.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class AppWindow extends JFrame {
    // Game Window Fields
    public static final int SQUARE_SIZE = 60; // 60 pixels
    public static final int WINDOW_WIDTH = 1260;
    public static final int WINDOW_HEIGHT = 780;
    public static final int MAZE_SIZE = SQUARE_SIZE * 9;
    public static final int HEADER_HEIGHT = SQUARE_SIZE * 2;

    // Controllers
    private GameController controller; // Reference to GameController
    private InputController inputController;
    private TimerController timerController;
    private RecorderController recorderController;

    // PANELS
    private List<JPanel> allPanels;
    private JPanel titlePanel;
    private JPanel gamePanel;// Reference to MazePanel (from renderer)
    private JPanel leftPanel; // Reference to LeftPanel
    private JPanel rightPanel;
    private JPanel menuPanel;

    /**
     * Constructor to initialize the main application window.
     * @param controller
     * @param inputController
     */
    public AppWindow(GameController controller, InputController inputController,
            TimerController timerController, RecorderController recorderController) {
        // TODO: Set up window, menus, status bar, and embed MazePanel
        super("Fash and Chaps :D");
        this.controller = controller;
        this.inputController = inputController;
        this.timerController = timerController;
        this.recorderController = recorderController;

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
        // Main Panels
        titlePanel = new TitlePanel(controller);
        menuPanel = new MenuPanel(controller);
        gamePanel = setupMazePanel();
        leftPanel = new LeftPanel(controller, timerController);
        rightPanel = new RightPanel(controller, recorderController);

        allPanels = List.of(titlePanel, menuPanel, gamePanel, leftPanel, rightPanel);

        // Main Frame
        add(titlePanel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.SOUTH);
        add(leftPanel, BorderLayout.WEST);
        add(gamePanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        System.out.println("Initialised Panels");
    }

    private JPanel setupMazePanel(){
        JPanel panel = controller.getRenderer().getPanel();
        panel.setPreferredSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        panel.setMinimumSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        panel.setMaximumSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createLineBorder(Color.white, 5));
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

    public void updateWindow(){
        allPanels.forEach(panel -> {
            if(panel instanceof GamePanel updatable) updatable.updatePanel();
        });
        allPanels.forEach(JPanel::repaint);
    }


    // TODO: Must decide what things are needed to be updated in the status bar
    public void updateStatus() {
        // TODO: Update status bar with current game info
    }



    // Getters
    public static int getWindowWidth() {return WINDOW_WIDTH;}
    public static int getWindowHeight() {return WINDOW_HEIGHT;}



}
