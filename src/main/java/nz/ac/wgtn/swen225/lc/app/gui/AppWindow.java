package nz.ac.wgtn.swen225.lc.app.gui;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.controller.local.InputController;
import nz.ac.wgtn.swen225.lc.app.controller.module.RecorderController;
import nz.ac.wgtn.swen225.lc.app.controller.local.TimerController;
import nz.ac.wgtn.swen225.lc.app.gui.screen.DefeatScreen;
import nz.ac.wgtn.swen225.lc.app.gui.screen.PlayScreen;
import nz.ac.wgtn.swen225.lc.app.gui.screen.StartScreen;
import nz.ac.wgtn.swen225.lc.app.gui.screen.VictoryScreen;
import nz.ac.wgtn.swen225.lc.app.state.*;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;

/**
 * Main application window/frame. Contains UI components and embeds the game panel from renderer.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class AppWindow extends JFrame {
    // Game Window Fields
    public static final int SQUARE_SIZE = 60; // 60 pixels
    public static final int WINDOW_WIDTH = SQUARE_SIZE * 21;
    public static final int WINDOW_HEIGHT = SQUARE_SIZE * 13;
    public static final int MAZE_SIZE = SQUARE_SIZE * 9;
    public static final int HEADER_HEIGHT = SQUARE_SIZE * 2;
    public static final int FONT_SIZE_H1 = 40;
    public static final int FONT_SIZE_H2 = 20;

    private final AppController c; // Reference to AppController

    /**
     * Constructor to initialize the main application window.
     * @param controller
     */
    public AppWindow(AppController controller) {
        // TODO: Set up window, menus, status bar, and embed MazePanel
        super("Fash and Chaps :D");
        this.c = controller;
        setupWindow();
    }

    private void setupWindow(){
        addKeyListener(c.inputController());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        pack();
        setFocusable(true);
        requestFocusInWindow();
        setResizable(false);
        setVisible(true);
    }

    /**
     * Utility method to format JLabels consistently.
     * @param label JLabel to format
     * @param fontSize Font size to apply
     */
    public static void formatLabel(JLabel label, int fontSize){
        label.setFont(MyFont.PIXEL.getFont(fontSize));
        label.setForeground(Color.white);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
    }

}
