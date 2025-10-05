package nz.ac.wgtn.swen225.lc.app.gui.layout;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.gui.GamePanel;
import nz.ac.wgtn.swen225.lc.app.util.MyButton;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

public class MenuPanel extends JPanel implements ActionListener, GamePanel {
    // Size fields
    public static final int PANEL_WIDTH = AppWindow.WINDOW_WIDTH;
    public static final int PANEL_HEIGHT = AppWindow.HEADER_HEIGHT;
    public static final int BUTTON_GAP = 20;
    public static final int FONT_SIZE = 40;

    // Buttons: Pause, Save, Exit, Resume
    private Map<JButton, Runnable> buttonRunnableMap;
    private AppController controller;
    private BufferedImage bgImg;

    /**
     * Constructor for MenuPanel.
     * @param controller AppController
     */
    public MenuPanel(AppController controller){
        this.controller = controller;
        setLayout(new GridLayout(1, 5, BUTTON_GAP, BUTTON_GAP));
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(BUTTON_GAP, BUTTON_GAP, BUTTON_GAP, BUTTON_GAP));
        setupButtons();
        bgImg = LoadingImg.Water.loadImage();
    }

    private void setupButtons(){
        buttonRunnableMap = new HashMap<>();
        setupSingleButton("Save", () -> controller.saveGame());
        setupSingleButton("Load", () -> controller.loadGame());
        setupSingleButton("Play", () -> controller.continueGame());
        setupSingleButton("Pause", () -> controller.pauseGame());
        setupSingleButton("Exit", () -> controller.exitGame());
    }

    private void setupSingleButton(String label, Runnable action){
        JButton button = MyButton.of(label, PANEL_WIDTH, PANEL_HEIGHT, FONT_SIZE,
                LoadingImg.Rock.loadImage());
        button.addActionListener(this);
        button.setFocusable(false);
        buttonRunnableMap.put(button, action);
        add(button);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if(buttonRunnableMap.containsKey(button)) buttonRunnableMap.get(button).run();
        else System.out.println("No action assigned to this button.");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int size = AppWindow.SQUARE_SIZE;
        int x = 0, y = 0;
        while(x < AppWindow.WINDOW_WIDTH){
            while(y < AppWindow.HEADER_HEIGHT){
                g.drawImage(bgImg, x, y, size, size, this);
                y += size;
            }
            y = 0;
            x += size;
        }
    }

    @Override
    public void updatePanel() {

    }
}
