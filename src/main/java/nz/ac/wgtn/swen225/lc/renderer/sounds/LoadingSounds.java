package nz.ac.wgtn.swen225.lc.renderer.sounds;

import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.sound.sampled.*;
import javax.xml.transform.Source;
import java.io.InputStream;

public enum LoadingSounds {
    VictorySound("sounds/victory.wav"),
    LosingSound("sounds/losing.wav"),
    WalkingSound(""),
    BackgroundSound(""),
    UnlockedSound("sounds/unlock.wav"),
    KeySound("sounds/key.wav"),
    CoinSound("sounds/coin.wav"),
    PlayerDrownSound("sounds/drown.wav"),
    PlayerCrabSound("");

    private final String filename;

    LoadingSounds(String name) {
        this.filename = name;
    }

    AudioInputStream loadSound() {
        try{
            InputStream stream = LoadingImg.class.getResourceAsStream("/" + filename);
            if(stream == null) {throw new RuntimeException("Cannot find the sound file: " + filename);}
            return AudioSystem.getAudioInputStream(stream);
        }catch(Exception e) {throw new RuntimeException("Error loading sound: " + filename, e);}
    }

    public void playSoundEffect(float volume) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(loadSound());

                //controls volume of sound
                FloatControl changeVol = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                changeVol.setValue(volume);

                clip.start();

                //makes sure the whole clip gets played
                Thread.sleep(clip.getMicrosecondLength() / 1000);

            } catch (Exception e) {
                throw new RuntimeException("Error playing sound: " + filename, e);
            }
        }).start();
    }

    /*public void playBackgroundMusic(){
        try{
            SourceDataLine sound = AudioSystem.getSourceDataLine();

        } catch(Exception e){ throw new  RuntimeException("Error loading sound: " + filename); }
    }*/

}
