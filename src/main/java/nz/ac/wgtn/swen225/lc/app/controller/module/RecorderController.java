package nz.ac.wgtn.swen225.lc.app.controller.module;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.controller.local.TimerController;
import nz.ac.wgtn.swen225.lc.app.state.AutoReplayState;
import nz.ac.wgtn.swen225.lc.app.state.PausedState;
import nz.ac.wgtn.swen225.lc.app.state.StepReplayState;
import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.recorder.Play;
import nz.ac.wgtn.swen225.lc.recorder.Save;
import nz.ac.wgtn.swen225.lc.recorder.SaveL1; // changed
import nz.ac.wgtn.swen225.lc.recorder.AutoplayL1; // changed
import nz.ac.wgtn.swen225.lc.recorder.StepByStepL1; // changed

public class RecorderController {
    Play autoplay;
    Play stepbystep;
    Save saveL1; // changed
    boolean isRecording = false;
    AppController controller;
    TimerController timerController;

    /**
     * Initialise the recorder controller with references to play and save classes.
     * @param controller AppController to interact with the game state.
     */
    public RecorderController(AppController controller, TimerController timerController) {
        this.controller = controller;
        this.timerController = timerController;
        autoplay = AutoplayL1.of(); // changed to work
        stepbystep = StepByStepL1.of(); // changed to work
        saveL1 = SaveL1.of(); // changed to work
    }

    /**
     * Start recording the player's movements.
     */
    public void startRecording(){
        isRecording = true;
        System.out.println("Started Recording");
    }

    /**
     * Stop recording the player's movements and save to file.
     */
    public void stopRecording(){
        isRecording = false;
        System.out.println("Stopped Recording");
        saveL1.updateMovement(Input.SAVE, controller, true); // changed
    }

    /**
     * Check if currently recording.
     * @return true if recording, false otherwise.
     */
    public boolean isRecording(){
        return isRecording;
    }

    /**
     * Set the speed of playback.
     * @param s Speed in milliseconds between moves.
     */
    public void setSpeed(int s) {
        autoplay.setSpeed(s); // changed
    }

    /**
     * Save the recorded movements to a file.
     */
    public void saveToFile(){
        isRecording = false;
        saveL1.updateMovement(Input.SAVE, controller, true); // changed
    }

    /**
     * Play back the recorded movements step by step.
     * If playback finishes, return to paused state.
     */
    public void stepByStep() {
        System.out.println("Step-By-Step Playing");
        controller.setState(new StepReplayState());
        if(!stepbystep.play(controller))
            controller.setState(new PausedState(controller));
    }

    /**
     * Automatically play back the recorded movements.
     * If playback finishes, return to paused state.
     */
    public void autoPlay() {
        System.out.println("Auto-Playing");
        controller.setState(new AutoReplayState());
        autoplay.play(controller); // changed to work
        //controller.setState(new PausedState(controller));
    }

    /**
     * Add a movement to the recording.
     * @param dir Direction of movement.
     */
    public void addMovement(Input dir){
        if(!isRecording) return;
        saveL1.updateMovement(dir, controller, false); // changed to work
    }





}
