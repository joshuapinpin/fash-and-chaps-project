package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.util.Renderer;
import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.controller.InputController;

import javax.swing.*;

/**
 * Main application window/frame. Contains UI components and embeds the game panel from renderer.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class GameWindow extends JFrame {
    // Reference to GameController
    StatusPanel statusPanel; // Reference to StatusPanel
    // Reference to MazePanel (from renderer)
    private InputController inputController;

    public GameWindow(GameController controller) {
        // TODO: Set up window, menus, status bar, and embed MazePanel
        super("Chaps Challenge");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);
    }

    public void setupInputController(InputController inputController) {
        this.inputController = inputController;
        this.addKeyListener(inputController);
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
