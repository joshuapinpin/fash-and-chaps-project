package nz.ac.wgtn.swen225.lc.app.controller.module;
import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.controller.Controller;
import nz.ac.wgtn.swen225.lc.app.controller.local.TimerController;
import nz.ac.wgtn.swen225.lc.app.state.AutoReplayState;
import nz.ac.wgtn.swen225.lc.app.state.PausedState;
import nz.ac.wgtn.swen225.lc.app.state.StepReplayState;
import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.recorder.*;

public class RecorderController implements Controller {
    Play autoplayL1;
    Play stepbystepL1;
    Save saveL1;
    boolean isRecording = false;

    AppController c;
    TimerController timerController;

    /**
     * Initialise the recorder controller with references to play and save classes.
     * @param controller AppController to interact with the game state.
     */
    public RecorderController(AppController controller, TimerController timerController) {
        this.c = controller;
        this.timerController = timerController;
        autoplayL1 = AutoplayL1.of();
        stepbystepL1 = StepByStepL1.of();
        saveL1 = SaveL1.of();
    }

    /**
     * Reset recording state at the start of a new game.
     */
    @Override
    public void atNewGame(){
        isRecording = false;
    }

    /**
     * Add a movement to the recording.
     * @param dir Direction of movement.
     */
    public void addMovement(Input dir){
        if(!isRecording) return;
        saveL1.updateMovement(dir, c, false);
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
        if(!isRecording) return;
        isRecording = false;
        System.out.println("Stopped Recording");
        saveToFile();
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
        autoplayL1.setSpeed(s);
    }

    /**
     * Save the recorded movements to a file.
     */
    public void saveToFile(){
        isRecording = false;
        saveL1.updateMovement(Input.SAVE, c, true);
    }

    /**
     * Play back the recorded movements step by step.
     * If playback finishes, return to paused state.
     */
    public void stepByStep() {
        System.out.println("Step-By-Step Playing");
        c.setState(new StepReplayState(c));
        if(!stepbystepL1.play(c))
            c.setState(new PausedState(c));
    }

    /**
     * Automatically play back the recorded movements.
     * If playback finishes, return to paused state.
     */
    public void autoPlay() {
        System.out.println("Auto-Playing");
        c.setState(new AutoReplayState(c));
        autoplayL1.play(c);
        // controller.setState(new PausedState(controller));
    }
}
