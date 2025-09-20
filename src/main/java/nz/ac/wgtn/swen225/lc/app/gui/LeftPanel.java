package nz.ac.wgtn.swen225.lc.app.gui;


import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;
import nz.ac.wgtn.swen225.lc.app.util.MyImage;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Displays time left (countdown), current level, keys collected, and treasures remaining.
 * Offers buttons for pausing, saving, quitting, and resuming a game.
 * Should also offer a help button that shows game rules.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class LeftPanel extends JPanel {
    // Size fields
//    public static final int PANEL_WIDTH = (GameWindow.WINDOW_WIDTH / 4);
    public static final int PANEL_WIDTH = GameWindow.SQUARE_SIZE * 4;
    public static final int PANEL_HEIGHT = GameWindow.MAZE_SIZE;
    public static final int FONT_SIZE = 40;

    private List<JPanel> allPanels;
    private JPanel levelPanel, timerPanel, keysPanel, treasurePanel;
    private GameController controller;



    public LeftPanel(GameController controller) {
        this.controller = controller;
        setLayout(new GridLayout(9, 1));
        setOpaque(false);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setupComponents();
    }

    private void setupComponents(){
        levelPanel = new JPanel();
        timerPanel = new JPanel();
        keysPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for(int i = 0; i < 4; i++){
                    g.drawImage(LoadingImg.Sand.loadImage(),i * GameWindow.SQUARE_SIZE, 0,
                            GameWindow.SQUARE_SIZE, GameWindow.SQUARE_SIZE, this);
                }
            }
        };
        treasurePanel = new JPanel(new GridLayout(4,1)){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for(int i = 0; i < 4; i++){
                    g.drawImage(LoadingImg.Sand.loadImage(),i * GameWindow.SQUARE_SIZE, 0,
                            GameWindow.SQUARE_SIZE, GameWindow.SQUARE_SIZE, this);
                }
            }
        };
        setupLabelWithPanel("Level", MyFont.PIXEL, levelPanel);
        setupLabelWithPanel("Timer", MyFont.PIXEL, timerPanel);
        setupLabelWithPanel("Keys", MyFont.PIXEL, keysPanel);
        setupLabelWithPanel("Treasure", MyFont.PIXEL, treasurePanel);
        allPanels = List.of(levelPanel, timerPanel, keysPanel, treasurePanel);
    }
    private void setupLabelWithPanel(String name, MyFont font, JPanel panel){
        JLabel label = new JLabel(name);
        label.setFont(font.getFont(FONT_SIZE));
        label.setForeground(Color.white);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        add(label);
        add(panel);
    }
}
