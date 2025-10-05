package nz.ac.wgtn.swen225.lc.app.controller.local;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.gui.GamePanel;
import nz.ac.wgtn.swen225.lc.app.gui.screen.*;
import nz.ac.wgtn.swen225.lc.app.gui.layout.*;
import nz.ac.wgtn.swen225.lc.app.state.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static nz.ac.wgtn.swen225.lc.app.gui.AppWindow.MAZE_SIZE;

public class WindowController {
    AppController c;
    AppWindow w;

    // Screens
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private StartScreen startScreen;
    private PlayScreen playScreen;
    private PauseScreen pauseScreen;
    private VictoryScreen victoryScreen;
    private DefeatScreen defeatScreen;
    private HelpScreen helpScreen;

    // Play Layout Panels
    private JPanel titlePanel, menuPanel, leftPanel,  rightPanel, gamePanel, infoPanel;
    private JLayeredPane layeredPane;

    // Game Logic Panels
    private JPanel keysPanel, levelPanel, timerPanel, treasurePanel;

    // List of all groups of panels
    private List<JPanel> screenPanels;
    private List<JPanel> layoutPanels;
    private List<JPanel> logicPanels;

    /**
     * Constructor for WindowController.
     * @param c AppController
     */
    public WindowController(AppController c){
        this.c = c;
        this.w = new AppWindow(c);
        screenPanels = new ArrayList<>();
        logicPanels = new ArrayList<>();
        layoutPanels = new ArrayList<>();
        setupScreens();
        setupLayoutPanels();
        setupLogicPanels();
    }

    // ====== CONTROLLER METHODS ======

    /**
     * Update the entire window (all panels).
     */
    public void updateWindow(){
        playScreen.updatePanel();

        layoutPanels.forEach(panel -> {
            if(panel instanceof GamePanel updatable) updatable.updatePanel();
            panel.repaint();
        });
    }

    /**
     * Show a specific screen based on the screen name.
     * Should be called when the game state changes.
     * @param screenName Name of the screen to show (e.g., "Start", "Play", "Victory", "Defeat").
     */
    public void changeScreen(String screenName){
        cardLayout.show(mainPanel, screenName);
    }

    /**
     * Show Info dialog
     */
    public void displayInfo(boolean doShow) {
        infoPanel.setVisible(doShow);
    }

    // ========== GETTERS ==========
    public AppWindow window() {return w;}


    // ====== SETUP METHODS ======

    private void setupScreens(){
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        setupSingleScreen(startScreen = new StartScreen(c), StartState.name());
        setupSingleScreen(playScreen = new PlayScreen(c), PlayState.name());
        setupSingleScreen(victoryScreen = new VictoryScreen(c), VictoryState.name());
        setupSingleScreen(defeatScreen = new DefeatScreen(c), DefeatState.name());
        // TODO: setupSingleScreen(pauseScreen, new PauseScreen(c), PauseState.name());

        w.setContentPane(mainPanel);
    }

    private void setupSingleScreen(JPanel panel, String name){
        mainPanel.add(panel, name);
        screenPanels.add(panel);
    }

    private void setupLayoutPanels(){
        playScreen.setLayout(new BorderLayout());
        infoPanel = new InfoPanel(c);

        setupSingleLayoutPanel(titlePanel = new TitlePanel(c), BorderLayout.NORTH);
        setupSingleLayoutPanel(menuPanel = new MenuPanel(c), BorderLayout.SOUTH);
        setupSingleLayoutPanel(leftPanel = new LeftPanel(c), BorderLayout.WEST);
        setupSingleLayoutPanel(rightPanel = new RightPanel(c), BorderLayout.EAST);

        gamePanel = c.renderer().getPanel();
        setupGamePanel();

        layeredPane = new GameLayeredPane();
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(infoPanel, JLayeredPane.PALETTE_LAYER);
        playScreen.add(layeredPane, BorderLayout.CENTER);

    }

    private void setupSingleLayoutPanel(JPanel panel, String position){
        playScreen.add(panel, position);
        layoutPanels.add(panel);
    }

    private void setupGamePanel(){
        gamePanel.setPreferredSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        gamePanel.setMinimumSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        gamePanel.setMaximumSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        gamePanel.setBounds(0, 0, MAZE_SIZE, MAZE_SIZE);
        gamePanel.setOpaque(true);
        gamePanel.setBackground(Color.RED);
        gamePanel.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        gamePanel.setVisible(true);
        layoutPanels.add(gamePanel);
    }

    private void setupLogicPanels(){

    }
}
