package nz.ac.wgtn.swen225.lc.app.controller.local;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.controller.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * Manages the countdown timer for each level.
 * Notifies AppController when time runs out.
 *
 * @author <Your Name>
 */
public class TimerController implements ActionListener, Controller {
    private static final int TIMER_INTERVAL = 50; // Timer ticks every 1/20 second
    private static final int TICKS_PER_SECOND = 1000/TIMER_INTERVAL; // Number of ticks per second
    private static final int LEVEL_1_TIME_LIMIT = 60; // Level 1 time limit in seconds
    private static final int LEVEL_2_TIME_LIMIT = 60; // Level 2 time limit in seconds

    private final AppController c;
    private final Timer timer;
    private int preciseTime;

    /**
     * Initializes the TimerController with a reference to the AppController.
     * @param controller The AppController to notify when time runs out.
     */
    public TimerController(AppController controller) {
        this(controller, 0); // Default to 300 seconds (5 minutes)
    }

    /**
     * Initializes the TimerController with a reference to the AppController and initial time.
     * @param controller The AppController to notify when time runs out.
     * @param initialTime Initial time in seconds.
     */
    public TimerController(AppController controller, int initialTime) {
        this.c = controller;
        this.preciseTime = initialTime * TICKS_PER_SECOND;
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
        if(level == 1) return LEVEL_1_TIME_LIMIT * TICKS_PER_SECOND;
        else if(level == 2) return LEVEL_2_TIME_LIMIT * TICKS_PER_SECOND;
        throw new IllegalArgumentException("Invalid Level: " + level);
    }

    @Override
    public void atNewGame(){
        restartTimer(c.persistencyController().level());
    }

    public void recorderMode() {
        c.timerController().restartTimer(c.level());
        c.timerController().pause();
    }

    /**
     * Handles timer ticks.
     * Called by the Timer every second.
     * @param e unused ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(preciseTime > 0) preciseTime --;
        if(preciseTime % TICKS_PER_SECOND == 0) {
            c.windowController().updateWindow();
            c.domainController().moveCrab();
        }
        if(preciseTime == 0) {
            timer.stop();
            c.defeat();
        }
        System.out.println("Time Left: " + getPreciseTimeMillis() + " seconds");
    }

    /**
     * Starts the timer.
     */
    public void play() {
        if(!timer.isRunning()) timer.start();
    }

    /**
     * Stops the timer.
     */
    public void pause() {
        if(timer.isRunning()) timer.stop();
    }

    /**
     * Starts the timer with a specific time limit.
     * @param level The level number to set the time limit for.
     */
    public void restartTimer(int level) {
        this.preciseTime = getTimeLimitForLevel(level);
        timer.restart();
    }


    /**
     * Gets the remaining time in seconds.
     * @return The remaining time in seconds.
     */
    public int getTimeLeft() {
        return (preciseTime / TICKS_PER_SECOND);
    }

    /**
     * Gets the remaining time in milliseconds.
     * @return The remaining time in milliseconds.
     */
    public int getPreciseTimeMillis(){
        return preciseTime * TIMER_INTERVAL;
    }
}
