package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.recorder.Play;
import nz.ac.wgtn.swen225.lc.recorder.Save;

public class RecorderController {
    Play play;
    Save save;
    GameController controller;

    public RecorderController(GameController controller){
        this.controller = controller;
        play = new Play();
        save = new Save();
    }

    public void addMovement(Input dir){
        Save.addMovement(dir);
    }

    public void startRecording(){
    }
    public void stopRecording(){}
    public void autoPlay(){}
    public void stepByStep(){}
}
