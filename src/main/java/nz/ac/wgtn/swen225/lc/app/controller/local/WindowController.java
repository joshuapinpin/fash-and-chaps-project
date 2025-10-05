package nz.ac.wgtn.swen225.lc.app.controller.local;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.gui.screen.*;
import nz.ac.wgtn.swen225.lc.app.gui.layout.*;
import nz.ac.wgtn.swen225.lc.app.gui.logic.*;
import nz.ac.wgtn.swen225.lc.app.state.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static nz.ac.wgtn.swen225.lc.app.gui.AppWindow.MAZE_SIZE;

public class WindowController {
    AppController c;
    AppWindow w;

    // Main
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Screens
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
    private KeysPanel keysPanel;
    private LevelPanel levelPanel;
    private TimerPanel timerPanel;
    private TreasurePanel treasurePanel;

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
        Stream.of(screenPanels, layoutPanels, logicPanels)
            .flatMap(List::stream)
            .forEach(Component::repaint);
        updateLevel();
        updateTimer();
    }

    public void updateLevel(){
        String level = "" + c.level();
        levelPanel.updateLogic(level);
    }

    public void updateTimer(){
        String timeLeft = "" + c.timerController().getTimeLeft();
        timerPanel.updateLogic(timeLeft);
    }

    public void updateKeys(){

    }

    public void updateTreasure(){

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
     * Show Info Panel
     * @param doShow true to show, false to hide
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
        gamePanel = setupGamePanel();

        layeredPane = new GameLayeredPane();
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(infoPanel, JLayeredPane.PALETTE_LAYER);
        playScreen.add(layeredPane, BorderLayout.CENTER);
    }

    private void setupSingleLayoutPanel(JPanel panel, String position){
        playScreen.add(panel, position);
        layoutPanels.add(panel);
    }

    private JPanel setupGamePanel(){
        JPanel gamePanel = c.renderer().getPanel();
        gamePanel.setPreferredSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        gamePanel.setMinimumSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        gamePanel.setMaximumSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        gamePanel.setBounds(0, 0, MAZE_SIZE, MAZE_SIZE);
        gamePanel.setOpaque(true);
        gamePanel.setBackground(Color.RED);
        gamePanel.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        gamePanel.setVisible(true);
        layoutPanels.add(gamePanel);
        return gamePanel;
    }

    private void setupLogicPanels(){
        setupSingleLogicPanel("Level", levelPanel = new LevelPanel(c));
        setupSingleLogicPanel("Timer", timerPanel = new TimerPanel(c));
        setupSingleLogicPanel("Keys", keysPanel = new KeysPanel(c));
        setupSingleLogicPanel("Treasure", treasurePanel = new TreasurePanel(c));
        leftPanel.add(new JLabel());
    }

    private void setupSingleLogicPanel(String name, JPanel panel){
        JLabel nameLabel = new JLabel(name);
        AppWindow.formatLabel(nameLabel, AppWindow.FONT_SIZE_H1);
        panel.add(nameLabel);
        leftPanel.add(nameLabel);
        leftPanel.add(panel);
    }
}
