package nz.ac.wgtn.swen225.lc.app.gui.screen;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.controller.module.RecorderController;
import nz.ac.wgtn.swen225.lc.app.controller.logic.TimerController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.gui.game.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static nz.ac.wgtn.swen225.lc.app.gui.AppWindow.MAZE_SIZE;

public class PlayScreen extends JPanel implements GamePanel {
    private AppController controller;
    private TimerController timerController;
    private RecorderController recorderController;

    // Panels
    private List<JPanel> allPanels;
    private JPanel titlePanel;
    private JPanel gamePanel;// Reference to MazePanel (from renderer)
    private JPanel leftPanel; // Reference to LeftPanel
    private JPanel rightPanel;
    private JPanel menuPanel;

    /**
     * Constructor to initialize the main application window.
     * @param c AppController
     * @param tc TimerController
     * @param rc RecorderController
     */
    public PlayScreen(AppController c, TimerController tc, RecorderController rc){

        this.controller = c;
        this.timerController = tc;
        this.recorderController = rc;
        setLayout(new BorderLayout());
        setupGame();
    }


    private void setupGame(){
        // Main Panels
        titlePanel = new TitlePanel(controller);
        menuPanel = new MenuPanel(controller);
        leftPanel = new LeftPanel(controller, timerController);
        rightPanel = new RightPanel(controller, recorderController);

        gamePanel = controller.renderer().getPanel();
        gamePanel.setPreferredSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        gamePanel.setMinimumSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        gamePanel.setMaximumSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        gamePanel.setOpaque(false);
        gamePanel.setBorder(BorderFactory.createLineBorder(Color.white, 5));


        allPanels = List.of(
                titlePanel, menuPanel, gamePanel,
                leftPanel, rightPanel);

        // Main Frame
        add(titlePanel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.SOUTH);
        add(leftPanel, BorderLayout.WEST);
        add(gamePanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }


    @Override
    public void updatePanel() {
        allPanels.forEach(panel -> {
            if(panel instanceof GamePanel updatable) updatable.updatePanel();
        });
        allPanels.forEach(JPanel::repaint);
    }
}
