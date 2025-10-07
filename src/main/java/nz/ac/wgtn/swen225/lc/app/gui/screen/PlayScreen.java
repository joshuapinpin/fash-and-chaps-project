package nz.ac.wgtn.swen225.lc.app.gui.screen;

import javax.swing.*;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.gui.layout.*;
import nz.ac.wgtn.swen225.lc.app.gui.logic.InfoPanel;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.renderer.imgs.Drawable;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

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
    private BufferedImage shell;

    private List<Position> shellPositions;

    /**
     * Constructor to initialize the main application window.
     * @param c AppController
     */
    public PlayScreen(AppController c){
        this.c = c;
        setupLayoutPanels();
        bgImg = LoadingImg.BgWater.loadImage();
        shell = LoadingImg.Shell.loadImage();
        shellPositions = List.of();
//                pos(1,1), pos(5,2), pos(0, 6),
//                pos(4,5), pos(3,10), pos(20,1),
//                pos(20, 7), pos(16,10), pos(19,9)
//        );
    }

    private Position pos(int x, int y){
        return new Position(x, y);
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
        //g.drawImage(bgImg, 0, 0, AppWindow.WINDOW_WIDTH, AppWindow.WINDOW_HEIGHT, this);
        int squareSize = AppWindow.SQUARE_SIZE;
        for(int x = 0; x < AppWindow.WINDOW_COLS; x++){
            for(int y = 0; y < AppWindow.WINDOW_ROWS; y++){
                g.drawImage(bgImg, x * squareSize, y * squareSize, squareSize, squareSize, this);
            }
        }

        // Darken the background
        g.setColor(new Color(0, 0, 0, 40)); // alpha 80 for subtle darkness
        g.fillRect(0, 0, AppWindow.WINDOW_WIDTH, AppWindow.WINDOW_HEIGHT);

        // Shells
        shellPositions.forEach(pos -> drawShell(g, pos, squareSize));
    }

    /** Helper Method for Drawing Shell */
    private void drawShell(Graphics g, Position pos, int squareSize){
        g.drawImage(shell, pos.getX() * squareSize + squareSize/4, pos.getY() * squareSize + squareSize/4,
                squareSize/2, squareSize/2, this);
    }

    // ===== GETTERS ======
    public LeftPanel leftPanel(){ return leftPanel;}
    public Drawable gamePanel(){ return gamePanel;}
    public InfoPanel infoPanel(){ return infoPanel;}
}
