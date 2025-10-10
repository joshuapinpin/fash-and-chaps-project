package nz.ac.wgtn.swen225.lc.app.gui.screen;

import javax.swing.*;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.gui.layout.*;
import nz.ac.wgtn.swen225.lc.app.gui.logic.InfoPanel;
import nz.ac.wgtn.swen225.lc.app.state.PausedState;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.renderer.imgs.Drawable;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static nz.ac.wgtn.swen225.lc.app.gui.AppWindow.MAZE_SIZE;

/**
 * The main play screen for the game, containing all panels and the game view.
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class PlayScreen extends JPanel {
    private TitlePanel titlePanel;
    private MenuPanel menuPanel;
    private LeftPanel leftPanel;
    private RightPanel rightPanel;
    private Drawable gamePanel;
    private InfoPanel infoPanel;
    private PausePanel pausePanel;
    private GameLayeredPane layeredPane;

    private CardLayout cardLayout;
    private JPanel overlayPanel;

    private AppController c;
    private BufferedImage bgImg;
    private boolean displayingPause = false;
    private String previousCard = "";

    /**
     * Constructor to initialize the main application window.
     * @param c AppController
     */
    public PlayScreen(AppController c){
        this.c = c;
        setupLayoutPanels();
        setupLayeredPanels();
        bgImg = LoadingImg.Background.loadImage();
    }

    private void setupLayoutPanels(){
        setLayout(new BorderLayout());
        infoPanel = new InfoPanel(c);
        pausePanel = new PausePanel(c);

        setupSingleLayoutPanel(titlePanel = new TitlePanel(c), BorderLayout.NORTH);
        setupSingleLayoutPanel(menuPanel = new MenuPanel(c), BorderLayout.SOUTH);
        setupSingleLayoutPanel(leftPanel = new LeftPanel(c), BorderLayout.WEST);
        setupSingleLayoutPanel(rightPanel = new RightPanel(c), BorderLayout.EAST);
        gamePanel = setupGamePanel();

    }

    private void setupSingleLayoutPanel(JPanel panel, String position){
        add(panel, position);
    }

    private Drawable setupGamePanel(){
        Drawable gamePanel = c.renderer().getPanel();
        gamePanel.setPreferredSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        gamePanel.setMinimumSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        gamePanel.setMaximumSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        gamePanel.setBounds(0, 0, MAZE_SIZE, MAZE_SIZE);
        gamePanel.setOpaque(true);
        gamePanel.setBackground(Color.RED);
        gamePanel.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        gamePanel.setVisible(true);
        return gamePanel;
    }

    private void setupLayeredPanels(){
        JPanel blankPanel = new JPanel();
        blankPanel.setOpaque(false);
        blankPanel.setVisible(false);
        blankPanel.setBounds(0,0, MAZE_SIZE, MAZE_SIZE);

        cardLayout = new CardLayout();
        overlayPanel = new JPanel(cardLayout);
        overlayPanel.setOpaque(false);
        overlayPanel.setVisible(true);
        overlayPanel.setBounds(0,0, MAZE_SIZE, MAZE_SIZE);

        overlayPanel.add(blankPanel, "Blank");
        overlayPanel.add(infoPanel, InfoPanel.name());
        overlayPanel.add(pausePanel, PausedState.name());


        layeredPane = new GameLayeredPane();
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(overlayPanel, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);
    }

    /**
     * Change the overlay panel to the specified panel.
     * @param name The name of the panel to display.
     */
    public void changeOverlay(String name){
        cardLayout.show(overlayPanel, name);
        displayingPause = false;
    }

    /**
     * Display the info panel overlay.
     */
    public void displayInfo(){
        previousCard = InfoPanel.name();
        changeOverlay(InfoPanel.name());
    }

    /**
     * Display the pause panel overlay.
     */
    public void displayPause(){
        changeOverlay(PausedState.name());
        displayingPause = true;
    }

    /**
     * Display a blank overlay (no overlay).
     */
    public void displayBlank(){
        previousCard = "Blank";
        changeOverlay("Blank");
    }

    /**
     * Display the previous overlay panel.
     */
    public void displayPrevious(){
        System.out.println("Displaying previous overlay");
        changeOverlay(previousCard);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int squareSize = AppWindow.SQUARE_SIZE;
        for(int x = 0; x < AppWindow.WINDOW_COLS; x++){
            for(int y = 0; y < AppWindow.WINDOW_ROWS; y++){
                g.drawImage(bgImg, x * squareSize, y * squareSize, squareSize, squareSize, this);
            }
        }
        //Darken the background
        g.setColor(new Color(0, 0, 0, 60));
        g.fillRect(0, 0, AppWindow.WINDOW_WIDTH, AppWindow.WINDOW_HEIGHT);
    }

    // ===== GETTERS ======
    public LeftPanel leftPanel(){ return leftPanel;}
    public Drawable gamePanel(){ return gamePanel;}
    public InfoPanel infoPanel(){ return infoPanel;}
    public PausePanel pausePanel(){ return pausePanel;}

    public boolean isPaused(){ return displayingPause;}
}
