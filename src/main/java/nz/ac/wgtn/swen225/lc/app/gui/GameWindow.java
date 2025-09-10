package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.app.util.Renderer;
import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.controller.InputController;

import javax.swing.*;
import javax.swing.JPanel;
import java.awt.*;

/**
 * Main application window/frame. Contains UI components and embeds the game panel from renderer.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class GameWindow extends JFrame {
    private GameController controller; // Reference to GameController
    private StatusPanel statusPanel; // Reference to StatusPanel
    // Reference to MazePanel (from renderer)
    private InputController inputController;
    public int x = 50, y = 50;

    public GameWindow(GameController controller, InputController inputController) {
        // TODO: Set up window, menus, status bar, and embed MazePanel
        super("Chaps Challenge");
        this.controller = controller;
        this.inputController = inputController;
        setupWindow();
        setupPanels();
    }

    private void setupWindow(){
        addKeyListener(inputController);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // temporary, for testing purposes only.
        setSize(800, 600);
        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);
        System.out.println("Initialised Game Window (JFrame)");
    }

    private void setupPanels(){
        statusPanel = new StatusPanel();
        statusPanel.setBackground(Color.red);
        statusPanel.setBounds(x,y,50,50);
        this.add(statusPanel);
        System.out.println("Initialised Panels");
    }

    public void updateStatusPanel(int x, int y){
        statusPanel.setBounds(this.x += x, this.y += y, 50, 50);
    }

    public void showPauseDialog() {
    }

    public void showErrorDialog(String message) {
    }

    public void showMessageDialog(String message, String title) {
    }

    public void setRenderer(Renderer renderer) {
    }

    // TODO: Must decide what things are needed to be updated in the status bar
    public void updateStatus() {
        // TODO: Update status bar with current game info
    }

}
