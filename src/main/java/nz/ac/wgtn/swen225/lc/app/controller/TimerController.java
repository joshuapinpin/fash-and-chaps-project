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

    /**
     * Initializes the TimerController with a reference to the GameController.
     * @param controller The GameController to notify when time runs out.
     */
    public TimerController(GameController controller) {
        this(controller, 0); // Default to 300 seconds (5 minutes)
    }

    /**
     * Initializes the TimerController with a reference to the GameController and initial time.
     * @param controller The GameController to notify when time runs out.
     * @param initialTime Initial time in seconds.
     */
    public TimerController(GameController controller, int initialTime) {
        this.controller = controller;
        this.timeLeft = initialTime;
        // TODO: Initialize timer
        timer = new Timer(TIMER_INTERVAL, this);
        timer.setInitialDelay(0);
    }

    /**
     * Gets the time limit for a given level.
     * @param level The level number (1 or 2).
     * @return The time limit in seconds.
     */
    public static int getTimeLimitForLevel(int level) {
        if(level == 1) return LEVEL_1_TIME_LIMIT;
        else if(level == 2) return LEVEL_2_TIME_LIMIT;
        throw new IllegalArgumentException("Invalid Level: " + level);
    }

    /**
     * Handles timer ticks.
     * Called by the Timer every second.
     * @param e unused ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(timeLeft > 0) timeLeft--;
        if(timeLeft == 0) {
            timer.stop();
            controller.timeUp();
        }
        controller.getGameWindow().updateWindow();
    }

    /**
     * Starts the timer.
     */
    public void start() {
        if(!timer.isRunning()) timer.start();
    }

    /**
     * Stops the timer.
     */
    public void pause() {if(timer.isRunning()) timer.stop();}

    /**
     * Starts the timer with a specific time limit.
     * @param level The level number to set the time limit for.
     */
    public void startTimer(int level) {
        this.timeLeft = getTimeLimitForLevel(level);
        timer.restart();
    }


    /**
     * Gets the remaining time in seconds.
     * @return The remaining time in seconds.
     */
    public int getTimeLeft() {
        return timeLeft;
    }
}
