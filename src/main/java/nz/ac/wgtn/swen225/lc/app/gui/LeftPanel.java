package nz.ac.wgtn.swen225.lc.app.gui;


import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.controller.TimerController;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;
import nz.ac.wgtn.swen225.lc.app.util.MyImage;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Displays time left (countdown), current level, keys collected, and treasures remaining.
 * Offers buttons for pausing, saving, quitting, and resuming a game.
 * Should also offer a help button that shows game rules.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class LeftPanel extends JPanel implements GamePanel{
    // Size fields
//    public static final int PANEL_WIDTH = (AppWindow.WINDOW_WIDTH / 4);
    public static final int PANEL_WIDTH = AppWindow.SQUARE_SIZE * 6;
    public static final int PANEL_HEIGHT = AppWindow.MAZE_SIZE;
    public static final int FONT_SIZE = 40;

    private List<JPanel> allPanels;
    private JPanel levelPanel, timerPanel, keysPanel, treasurePanel;
    private JLabel timerLabel, levelLabel;
    private GameController controller;
    private TimerController timerController;
    private BufferedImage bgImg;

    public LeftPanel(GameController controller, TimerController timerController){
        this.controller = controller;
        this.timerController = timerController;
        setLayout(new GridLayout(9, 1));
        setOpaque(false);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setupComponents();
        bgImg = new MyImage("water").getImage();
    }

    private void setupComponents(){
        levelPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BufferedImage img = LoadingImg.Sand.loadImage();
                for(int i = 0; i < 4; i++){
                    g.drawImage(img,AppWindow.SQUARE_SIZE + i * AppWindow.SQUARE_SIZE, 0,
                            AppWindow.SQUARE_SIZE, AppWindow.SQUARE_SIZE, this);
                }
            }
        };
        timerPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BufferedImage img = LoadingImg.Sand.loadImage();
                for(int i = 0; i < 4; i++){
                    g.drawImage(img,AppWindow.SQUARE_SIZE + i * AppWindow.SQUARE_SIZE, 0,
                            AppWindow.SQUARE_SIZE, AppWindow.SQUARE_SIZE, this);
                }
            }
        };
        keysPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int keysLeft = controller.getDomain().getPlayer().getKeysLeft();
                BufferedImage img;
                for(int i = 0; i < 4; i++){
                    if(i < keysLeft) img = LoadingImg.OrangeKey.loadImage();
                    else img = LoadingImg.Sand.loadImage();
                    g.drawImage(img,AppWindow.SQUARE_SIZE + i * AppWindow.SQUARE_SIZE, 0,
                            AppWindow.SQUARE_SIZE, AppWindow.SQUARE_SIZE, this);
                }
            }
        };

        treasurePanel = new JPanel(new GridLayout(4,1)){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for(int i = 0; i < 4; i++){
                    int treasures = controller.getDomain().getPlayer().getTreasuresCollected();
                    BufferedImage img;
                    if(i < treasures) img = LoadingImg.Treasure.loadImage();
                    else img = LoadingImg.Sand.loadImage();
                    g.drawImage(img,AppWindow.SQUARE_SIZE + i * AppWindow.SQUARE_SIZE, 0,
                            AppWindow.SQUARE_SIZE, AppWindow.SQUARE_SIZE, this);
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
        panel.setOpaque(false);

        if(panel == timerPanel){
            timerLabel = new JLabel("0");
            timerLabel.setFont(font.getFont(FONT_SIZE));
            timerLabel.setForeground(Color.white);
            timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            timerLabel.setVerticalAlignment(SwingConstants.CENTER);
            panel.add(timerLabel);
        } else if(panel == levelPanel){
            levelLabel = new JLabel(controller.getLevel() + "");
            levelLabel.setFont(font.getFont(FONT_SIZE));
            levelLabel.setForeground(Color.white);
            levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
            levelLabel.setVerticalAlignment(SwingConstants.CENTER);
            panel.add(levelLabel);
        }

        add(label);
        add(panel);
    }

    @Override
    public void updatePanel() {
        int timeLeft = timerController.getTimeLeft();
        timerLabel.setText(timeLeft + "");
        allPanels.forEach(JPanel::repaint);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int squareSize = AppWindow.SQUARE_SIZE;
        int x = 0, y = 0;
        while(x < PANEL_WIDTH){
            while(y < PANEL_HEIGHT){
                g.drawImage(bgImg, x, y, squareSize, squareSize, this);
                y += squareSize;
            }
            y = 0;
            x += squareSize;
        }
    }
}
