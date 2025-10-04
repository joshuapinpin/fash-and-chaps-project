package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.app.state.AutoReplayState;
import nz.ac.wgtn.swen225.lc.app.state.GameState;
import nz.ac.wgtn.swen225.lc.app.state.PausedState;
import nz.ac.wgtn.swen225.lc.app.state.StepReplayState;
import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.recorder.Play;
import nz.ac.wgtn.swen225.lc.recorder.Save;

public class RecorderController {
    Play play;
    Save save;
    Maze domainCopy;
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

    public void startRecording(){
        isRecording = true;
        System.out.println("Started Recording");
    }

    public void stopRecording(){
        isRecording = false;
        save.saveToFile(null); // josh fix this
        System.out.println("Stopped Recording");
    }

    public boolean isRecording(){
        return isRecording;
    }

    public void setSpeed(int s) {
        play.setSpeed(s);
    }

    public void stepByStep() {
        System.out.println("Step-By-Step Playing");
        controller.setState(new StepReplayState());
        if(!play.stepByStep(controller, null)) // josh fix this
            controller.setState(new PausedState(timerController));
    }

    public void autoPlay() {
        System.out.println("Auto-Playing");
        controller.setState(new AutoReplayState());
        play.autoPlay(controller, null); // josh fix this
        controller.setState(new PausedState(timerController));
    }

    public void addMovement(Input dir){
        if(!isRecording) return;
        save.addMovement(dir);
    }





}
