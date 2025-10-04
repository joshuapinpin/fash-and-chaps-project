package nz.ac.wgtn.swen225.lc.renderer.sounds;

import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.sound.sampled.*;
import java.io.InputStream;

public enum LoadingSounds {
    VictorySound("sounds/victory.wav"),
    LosingSound("sounds/losing.wav"),
    WalkingSound(""),
    BackgroundSound("sounds/background.wav"),
    UnlockedSound("sounds/unlock.wav"),
    KeySound("sounds/key.wav"),
    CoinSound("sounds/coin.wav"),
    PlayerDrownSound("sounds/drown.wav"),
    PlayerCrabSound("");

    private final String filename;
    private Clip BGCLIP; // saves the clip for bg sound

    LoadingSounds(String name) {
        this.filename = name;
    }

    /**
     * Loads the file for sound to each enum
     * @return returns an AudioInputStream used to find sound/load sound
     */
    AudioInputStream loadSound() {
        try{
            InputStream stream = LoadingImg.class.getResourceAsStream("/" + filename);
            if(stream == null) {throw new RuntimeException("Cannot find the sound file: " + filename);}
            return AudioSystem.getAudioInputStream(stream);
        }catch(Exception e) {throw new RuntimeException("Error loading sound: " + filename, e);}
    }

    /**
     * Method to play each sound effect
     * @param volume - controls how loud the sound is
     */
    public void playSoundEffect(float volume) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(loadSound()); // gets the file as audio

                //controls volume of sound
                FloatControl changeVol = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                changeVol.setValue(volume);
                clip.start();

                //makes sure the whole clip gets played
                Thread.sleep(clip.getMicrosecondLength() / 1000);
            } catch (Exception e) {
                throw new RuntimeException("Error playing sound effect: " + filename, e);
            }
        }).start();
    }

    /**
     * Method to play background music in a loop
     * @param volume - controls how loud the sound is
     */
    public void playBackgroundMusic(float volume){
            try {
                BGCLIP = AudioSystem.getClip();
                BGCLIP.open(loadSound()); //gets the audio

                //controls volume of sound
                FloatControl changeVol = (FloatControl) BGCLIP.getControl(FloatControl.Type.MASTER_GAIN);
                changeVol.setValue(volume);
                
                BGCLIP.start();
                BGCLIP.loop(Clip.LOOP_CONTINUOUSLY); // loops the music

            } catch (Exception e) {
                throw new RuntimeException("Error playing background sound: " + filename, e);
            }

    }

    /**
     * Stops the background music
     */
    public void stopBackgroundMusic(){
        BGCLIP.stop();
    }

}
