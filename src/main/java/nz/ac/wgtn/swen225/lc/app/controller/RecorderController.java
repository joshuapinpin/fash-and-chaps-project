package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.recorder.Play;
import nz.ac.wgtn.swen225.lc.recorder.Save;

public class RecorderController {
    Play play;
    Save save;
    boolean isRecording = false;
    GameController controller;

    /**
     * Initialise the recorder controller with references to play and save classes.
     * @param controller GameController to interact with the game state.
     */
    public RecorderController(GameController controller) {
        this.controller = controller;
        play = new Play();
        save = new Save();
    }

    public void startRecording(){
        isRecording = true;
        System.out.println("Started Recording");
    }

    public void stopRecording(){
        isRecording = false;
        save.saveToFile();
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
        play.stepByStep(controller);
    }

    public void autoPlay() {
        System.out.println("Auto-Playing");
        play.autoPlay(controller);
    }

    public void addMovement(Input dir){
        if(!isRecording) return;
        save.addMovement(dir);
    }





}
