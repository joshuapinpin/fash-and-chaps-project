package nz.ac.wgtn.swen225.lc.app.controller.module;
import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.controller.Controller;
import nz.ac.wgtn.swen225.lc.app.controller.local.TimerController;
import nz.ac.wgtn.swen225.lc.app.state.AutoReplayState;
import nz.ac.wgtn.swen225.lc.app.state.PausedState;
import nz.ac.wgtn.swen225.lc.app.state.PlayState;
import nz.ac.wgtn.swen225.lc.app.state.StepReplayState;
import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.recorder.*;

/**
 * Controller for recording and replaying player movements.
 * Integrates with Play and Save classes to manage recording state.
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class RecorderController implements Controller {
    Play autoplayL1;
    Play stepbystepL1;
    PlayL2 autoplayL2; // changed
    PlayL2 stepbystepL2; // changed
    Save saveL1;
    Save saveL2; // changed
    boolean isRecording = false;
    boolean isL2 = true;
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
        autoplayL2 = AutoplayL2.of(); // changed
        stepbystepL2 = StepByStepL2.of(); // changed
        saveL2 = SaveL2.of(); // changed
    }

    /**
     * Reset recording state at the start of a new game.
     */
    @Override
    public void atNewGame(){
        isRecording = false;
        saveL1.reset();
        saveL2.reset(); // changed
        autoplayL1.reset(); // changed
        autoplayL2.reset(); // changed
        stepbystepL1.reset(); // changed
        stepbystepL2.reset(); // changed
    }

    private void setLevel(int level){
        if(level == 1) isL2 = false;
        else isL2 = true;
    }

    /**
     * Add a movement to the recording.
     * @param dir Direction of movement.
     */
    public void addMovement(Input dir){
        if(!isRecording) return;

        System.out.println("Recorded move: " + dir);
        if(isL2) saveL2.updateMovement(dir, c, false); // changed
        else{ // changed
            saveL1.updateMovement(dir, c, false);
        }
    }

    /**
     * Start recording the player's movements.
     */
    public void startRecording(){
        if(isRecording || !(c.state() instanceof PlayState)) return;
        setLevel(c.persistencyController().level());
        isRecording = true;
        System.out.println("Started Recording");
        saveL1.startRecorder(c);
    }

    /**
     * Stop recording the player's movements and save to file.
     */
    public void stopRecording(){
        if(!isRecording) return;
        c.pauseGame();
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
        if(isL2) autoplayL2.setSpeed(s); // changed
        else{ // changed
            autoplayL1.setSpeed(s);
        }
    }

    /**
     * Save the recorded movements to a file.
     */
    public void saveToFile(){
        isRecording = false;
        if(isL2) saveL2.updateMovement(Input.SAVE, c, true); // changed
        else{ // changed
            saveL1.updateMovement(Input.SAVE, c, true);
        }
    }

    /**
     * Play back the recorded movements step by step.
     * If playback finishes, return to paused state.
     */
    public void stepByStep() {
        c.pauseGame();
        System.out.println("Step-By-Step Playing");
        c.setState(new StepReplayState(c));

        // Note to marker, I would've done dynamic dispatch but recorder made different interfaces :(
        if(isL2){
            if(!stepbystepL2.play(c))c.setState(new PausedState(c));
        }
        else{ // changed
            if(!stepbystepL1.play(c)) c.setState(new PausedState(c));
        }
    }

    /**
     * Automatically play back the recorded movements.
     * If playback finishes, return to paused state.
     */
    public void autoPlay() {
        c.pauseGame();
        System.out.println("Auto-Playing");
        c.setState(new AutoReplayState(c));
        if(isL2)  {
            autoplayL2.play(c); // changed
        }
        else{ // changed
            autoplayL1.play(c);
        }


    }
}
