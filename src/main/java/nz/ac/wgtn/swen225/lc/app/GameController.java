
package nz.ac.wgtn.swen225.lc.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

/**
 * GameController acts as the Controller in MVC, handling user input and game state transitions.
 * @author SWEN225 Team
 */
public class GameController implements KeyListener, ActionListener {
    private MainFrame mainFrame;
    private Timer timer;

    public GameController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.timer = new Timer(1000, this); // 1 second timer for countdown
    }

    public void initialize() {
        // TODO: Setup key bindings, start new game, etc.
    }

    public void startGame(int level) {
        // TODO: Load level from domain/persistency
        // TODO: Reset timer and game state
    }

    public void pauseGame() {
        timer.stop();
        // mainFrame.showPauseDialog();
    }

    public void resumeGame() {
        timer.start();
        // mainFrame.hidePauseDialog();
    }

    public void saveGame() {
        // TODO: Save game state using persistency
    }

    public void loadGame() {
        // TODO: Load game state using persistency
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO: Handle movement keys and game controls
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        // Timer tick: update countdown, check for timeout
    }
}