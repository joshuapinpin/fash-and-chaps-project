package nz.ac.wgtn.swen225.lc.app.gui.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.controller.local.TimerController;
import nz.ac.wgtn.swen225.lc.app.controller.module.RecorderController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import static nz.ac.wgtn.swen225.lc.app.gui.AppWindow.MAZE_SIZE;
import nz.ac.wgtn.swen225.lc.app.gui.GamePanel;
import nz.ac.wgtn.swen225.lc.app.gui.layout.InfoPanel;
import nz.ac.wgtn.swen225.lc.app.gui.layout.LeftPanel;
import nz.ac.wgtn.swen225.lc.app.gui.layout.MenuPanel;
import nz.ac.wgtn.swen225.lc.app.gui.layout.RightPanel;
import nz.ac.wgtn.swen225.lc.app.gui.layout.TitlePanel;

public class PlayScreen extends JPanel implements GamePanel {
    private AppController controller;
    private TimerController timerController;
    private RecorderController recorderController;

    // Panels
    private List<JPanel> allPanels;
    private JPanel titlePanel;
    private JPanel leftPanel; // Reference to LeftPanel
    private JPanel rightPanel;
    private JPanel menuPanel;

    // Game Panels
    private JLayeredPane layeredPane;
    private JPanel gamePanel;// Reference to MazePanel (from renderer)
    private JPanel infoPanel; // Reference to InfoPanel

    /**
     * Constructor to initialize the main application window.
     * @param c AppController
     */
    public PlayScreen(AppController c){
        this.controller = c;
        this.timerController = c.timerController();
        this.recorderController = c.recorderController();
        //setLayout(new BorderLayout());
        //setupGame();
    }

    private void setupGame(){
        // Main Panels
        titlePanel = new TitlePanel(controller);
        menuPanel = new MenuPanel(controller);
        leftPanel = new LeftPanel(controller);
        rightPanel = new RightPanel(controller);
        infoPanel = new InfoPanel(controller);

        gamePanel = controller.renderer().getPanel();
        gamePanel.setPreferredSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        gamePanel.setMinimumSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        gamePanel.setMaximumSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        gamePanel.setBounds(0, 0, AppWindow.MAZE_SIZE, AppWindow.MAZE_SIZE);
        gamePanel.setOpaque(true);
        gamePanel.setBackground(Color.RED);
        gamePanel.setBorder(BorderFactory.createLineBorder(Color.white, 5));

        gamePanel.setVisible(true);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        layeredPane.setLayout(null);
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(infoPanel, JLayeredPane.PALETTE_LAYER);

        // Main Frame
        add(titlePanel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.SOUTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        add(layeredPane, BorderLayout.CENTER);
        allPanels = List.of(titlePanel, menuPanel, gamePanel, leftPanel, rightPanel);
    }

    /**
     * Show the info panel over the game panel.
     */
    public void showInfo(){infoPanel.setVisible(true);}

    /**
     * Hide the info panel over the game panel.
     */
    public void hideInfo(){infoPanel.setVisible(false);}

    @Override
    public void updatePanel() {
//        allPanels.forEach(panel -> {
//            if(panel instanceof GamePanel updatable) updatable.updatePanel();
//        });
//        allPanels.forEach(JPanel::repaint);
        repaint();
    }
}
