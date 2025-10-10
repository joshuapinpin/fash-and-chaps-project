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
import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.app.util.MyButton;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

/**
 * MenuPanel class represents the top menu panel with buttons for game control.
 * It includes buttons for Pause, Save, Load, Play, and Home.
 * Each button is associated with a specific action in the AppController.
 * The panel is designed to fit within the application's window dimensions.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class MenuPanel extends JPanel implements ActionListener {
    // Size fields
    public static final int PANEL_WIDTH = AppWindow.WINDOW_WIDTH;
    public static final int PANEL_HEIGHT = AppWindow.HEADER_HEIGHT;
    public static final int BUTTON_GAP = 20;
    public static final int FONT_SIZE = 40;

    // Buttons: Pause, Save, Exit, Resume
    private Map<JButton, Runnable> buttonRunnableMap;
    private AppController c;
    private BufferedImage bgImg;

    /**
     * Constructor for MenuPanel.
     * @param controller AppController
     */
    public MenuPanel(AppController controller){
        this.c = controller;
        setLayout(new GridLayout(1, 5, BUTTON_GAP, BUTTON_GAP));
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(BUTTON_GAP, BUTTON_GAP, BUTTON_GAP, BUTTON_GAP));
        setupButtons();
        bgImg = LoadingImg.Water.loadImage();
    }

    private void setupButtons(){
        buttonRunnableMap = new HashMap<>();
        setupSingleButton("Save", () -> c.handleInput(Input.SAVE));
        setupSingleButton("Load", () -> c.handleInput(Input.RESUME));
        setupSingleButton("Play", () -> c.handleInput(Input.CONTINUE));
        setupSingleButton("Pause", () -> c.handleInput(Input.PAUSE));
        setupSingleButton("Home", () -> c.handleInput(Input.EXIT));
    }

    private void setupSingleButton(String label, Runnable action){
        JButton button = MyButton.of(label, PANEL_WIDTH, PANEL_HEIGHT, FONT_SIZE,
                LoadingImg.RockButton.loadImage());
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
    }

}
