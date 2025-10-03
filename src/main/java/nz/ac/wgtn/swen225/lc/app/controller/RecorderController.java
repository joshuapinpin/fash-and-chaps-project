package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.app.state.AutoReplayState;
import nz.ac.wgtn.swen225.lc.app.state.PausedState;
import nz.ac.wgtn.swen225.lc.app.state.StepReplayState;
import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.recorder.Play;
import nz.ac.wgtn.swen225.lc.recorder.Save;

public class RecorderController {
    Play play;
    Save save;
    boolean isRecording = false;
    GameController controller;
    TimerController timerController;

    /**
     * Initialise the recorder controller with references to play and save classes.
     * @param controller GameController to interact with the game state.
     */
    public RecorderController(GameController controller, TimerController timerController) {
        this.controller = controller;
        this.timerController = timerController;
        play = new Play();
        save = new Save();
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
        play.setSpeed(s);
    }

    /**
     * Save the recorded movements to a file.
     */
    public void saveToFile(){
        isRecording = false;
        save.saveToFile();
    }

    /**
     * Play back the recorded movements step by step.
     * If playback finishes, return to paused state.
     */
    public void stepByStep() {
        System.out.println("Step-By-Step Playing");
        controller.setState(new StepReplayState());
        if(!play.stepByStep(controller))
            controller.setState(new PausedState(timerController));
    }

    /**
     * Automatically play back the recorded movements.
     * If playback finishes, return to paused state.
     */
    public void autoPlay() {
        System.out.println("Auto-Playing");
        controller.setState(new AutoReplayState());
        play.autoPlay(controller);
        controller.setState(new PausedState(timerController));
    }

    /**
     * Add a movement to the recording.
     * @param dir Direction of movement.
     */
    public void addMovement(Input dir){
        if(!isRecording) return;
        save.addMovement(dir);
    }





}
