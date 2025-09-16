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
    private static final int TIMER_INTERVAL = 1000; // Timer ticks every second
    private static final int LEVEL_1_TIME_LIMIT = 60; // Level 1 time limit in seconds
    private static final int LEVEL_2_TIME_LIMIT = 120; // Level 2 time limit in seconds

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
        timer = new Timer(TIMER_INTERVAL, this);
        timer.setInitialDelay(0);
    }

    public static int getTimeLimitForLevel(int level) {
        if(level == 1) return LEVEL_1_TIME_LIMIT;
        else if(level == 2) return LEVEL_2_TIME_LIMIT;
        throw new IllegalArgumentException("Invalid Level: " + level);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Update time left and notify controller if time runs out
        if(timeLeft > 0) timeLeft--;
        if(timeLeft == 0) {
            timer.stop();
            controller.timeUp();
        }
    }

    /**
     * Starts the timer.
     */
    public void start() {
        if(!timer.isRunning()) timer.start();
    }

    /**
     * Pauses the timer.
     */
    public void pause() {
        if(timer.isRunning()) timer.stop();
    }

    /**
     * Resets the timer.
     */
    public void reset(int newTime) {
        this.timeLeft = newTime;
        timer.restart();
    }

    public void startTimer(int timeLimit) {
        this.timeLeft = timeLimit;
        timer.restart();
    }

    public void stopTimer() {
        pause();
    }


}
