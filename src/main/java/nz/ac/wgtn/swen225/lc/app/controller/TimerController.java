package nz.ac.wgtn.swen225.lc.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * Manages the countdown timer for each level.
 * Notifies GameController when time runs out.
 *
 * @author <Your Name>
 */
public class TimerController implements ActionListener {
    private Timer timer;
    private int timeLeft;
    private final GameController controller;

    public TimerController(GameController controller) {
        this(controller, 0); // Default to 300 seconds (5 minutes)
    }
    public TimerController(GameController controller, int initialTime) {
        this.controller = controller;
        this.timeLeft = initialTime;
        // TODO: Initialize timer
    }

    public static int getTimeLimitForLevel(int level) {
        if(level == 1) return 60;
        else throw new IllegalArgumentException("Invalid Level: " + level);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Update time left and notify controller if time runs out
    }

    /**
     * Starts the timer.
     */
    public void start() {
        // TODO: Start timer
    }

    /**
     * Pauses the timer.
     */
    public void pause() {
        // TODO: Pause timer
    }

    /**
     * Resets the timer.
     */
    public void reset(int newTime) {
        // TODO: Reset timer
    }

    public void startTimer(int timeLimit) {
    }

    public void stopTimer() {
    }


}
