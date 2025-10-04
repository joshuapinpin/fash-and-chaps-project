package nz.ac.wgtn.swen225.lc.app.gui.game;


import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.controller.logic.TimerController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;
import nz.ac.wgtn.swen225.lc.domain.entities.EntityColor;
import nz.ac.wgtn.swen225.lc.domain.entities.Key;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Displays time left (countdown), current level, keys collected, and treasures remaining.
 * Offers buttons for pausing, saving, quitting, and resuming a game.
 * Should also offer a help button that shows game rules.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class LeftPanel extends JPanel implements GamePanel{
    public static final int PANEL_WIDTH = AppWindow.SQUARE_SIZE * 6;
    public static final int PANEL_HEIGHT = AppWindow.MAZE_SIZE;
    public static final int FONT_SIZE = 40;

    private JPanel levelPanel, timerPanel, keysPanel, treasurePanel;
    private JLabel timerLabel, levelLabel;
    private BufferedImage bgImg;
    private MyFont font;

    private AppController controller;
    private TimerController timerController;

    private Map<EntityColor, BufferedImage> imageKeyMap;
    private List<JComponent> allComponents;

    /**
     * Constructor for LeftPanel.
     * @param controller AppController
     * @param timerController TimerController
     */
    public LeftPanel(AppController controller, TimerController timerController){
        this.controller = controller;
        this.timerController = timerController;
        this.font = MyFont.PIXEL;
        setLayout(new GridLayout(9, 1));
        setOpaque(false);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setupComponents();
        bgImg = LoadingImg.Water.loadImage();
        imageKeyMap = Map.of(
            EntityColor.PINK, LoadingImg.PinkKey.loadImage(),
            EntityColor.ORANGE, LoadingImg.OrangeKey.loadImage(),
            EntityColor.GREEN, LoadingImg.GreenKey.loadImage(),
            EntityColor.PURPLE, LoadingImg.PurpleKey.loadImage()
        );
    }

    private void setupComponents(){
        allComponents = new ArrayList<>();
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
                List<Key> keys = controller.domain().getPlayer().getKeys();
                int keysLeft = keys.size();


                BufferedImage img;
                for(int i = 0; i < 4; i++){
                    if(i < keysLeft){
                        EntityColor keyColor = keys.get(i).getColor();
                        img = imageKeyMap.get(keyColor);
                    }
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
                    int treasures = controller.domain().getPlayer().getTreasuresCollected();
                    BufferedImage img;
                    if(i < treasures) img = LoadingImg.Treasure.loadImage();
                    else img = LoadingImg.Sand.loadImage();
                    g.drawImage(img,AppWindow.SQUARE_SIZE + i * AppWindow.SQUARE_SIZE, 0,
                            AppWindow.SQUARE_SIZE, AppWindow.SQUARE_SIZE, this);
                }
            }
        };
        setupLabelWithPanel("Level", levelPanel);
        setupLabelWithPanel("Timer",  timerPanel);
        setupLabelWithPanel("Keys",  keysPanel);
        setupLabelWithPanel("Treasure", treasurePanel);
    }

    private void setupLabelWithPanel(String name, JPanel panel){
        JLabel label = new JLabel(name);
        setupLabel(label);
        panel.setOpaque(false);

        if(panel == timerPanel){
            timerLabel = new JLabel("0");
            setupLabel(timerLabel);
            panel.add(timerLabel);
        } else if(panel == levelPanel){
            levelLabel = new JLabel(controller.level() + "");
            setupLabel(levelLabel);
            panel.add(levelLabel);
        }

        add(label); add(panel);
        allComponents.add(label); allComponents.add(panel);
    }

    private void setupLabel(JLabel label){
        label.setFont(font.getFont(FONT_SIZE));
        label.setForeground(Color.white);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
    }

    /**
     * Update the panel's components.
     */
    @Override
    public void updatePanel() {
        int timeLeft = timerController.getTimeLeft();
        timerLabel.setText(timeLeft + "");
        int level = controller.level();
        levelLabel.setText(level + "");
        allComponents.forEach(JComponent::repaint);
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
