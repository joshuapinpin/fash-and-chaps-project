package nz.ac.wgtn.swen225.lc.app.gui.screen;

import javax.swing.*;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.gui.layout.*;
import nz.ac.wgtn.swen225.lc.app.gui.logic.InfoPanel;
import nz.ac.wgtn.swen225.lc.renderer.imgs.Drawable;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import java.awt.*;
import java.awt.image.BufferedImage;

import static nz.ac.wgtn.swen225.lc.app.gui.AppWindow.MAZE_SIZE;

public class PlayScreen extends JPanel {
    private TitlePanel titlePanel;
    private MenuPanel menuPanel;
    private LeftPanel leftPanel;
    private RightPanel rightPanel;
    private Drawable gamePanel;
    private InfoPanel infoPanel;
    private GameLayeredPane layeredPane;

    private AppController c;
    private BufferedImage bgImg;

    /**
     * Constructor to initialize the main application window.
     * @param c AppController
     */
    public PlayScreen(AppController c){
        this.c = c;
        setupLayoutPanels();
        bgImg = LoadingImg.Background.loadImage();
    }

    private void setupLayoutPanels(){
        setLayout(new BorderLayout());
        infoPanel = new InfoPanel(c);

        setupSingleLayoutPanel(titlePanel = new TitlePanel(c), BorderLayout.NORTH);
        setupSingleLayoutPanel(menuPanel = new MenuPanel(c), BorderLayout.SOUTH);
        setupSingleLayoutPanel(leftPanel = new LeftPanel(c), BorderLayout.WEST);
        setupSingleLayoutPanel(rightPanel = new RightPanel(c), BorderLayout.EAST);
        gamePanel = setupGamePanel();

        layeredPane = new GameLayeredPane();
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(infoPanel, JLayeredPane.PALETTE_LAYER);
        add(layeredPane, BorderLayout.CENTER);
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
        //layoutPanels.add(gamePanel);
        return gamePanel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, AppWindow.WINDOW_WIDTH, AppWindow.WINDOW_HEIGHT, this);
    }

    // ===== GETTERS ======
    public LeftPanel leftPanel(){ return leftPanel;}
    public Drawable gamePanel(){ return gamePanel;}
    public InfoPanel infoPanel(){ return infoPanel;}
}
