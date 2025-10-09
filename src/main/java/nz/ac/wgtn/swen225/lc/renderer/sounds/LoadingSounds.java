package nz.ac.wgtn.swen225.lc.renderer.sounds;

import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.sound.sampled.*;
import java.io.InputStream;

/**
 * Loads different sounds and music
 * Used enums to store and hold sounds to be played within the game
 * @author Emily Ung (300663254)
 */
public enum LoadingSounds {
    VictorySound("sounds/victory.wav"),
    LosingSound("sounds/losing.wav"),
    BackgroundSound("sounds/background.wav"),
    UnlockedSound("sounds/unlock.wav"),
    KeySound("sounds/key.wav"),
    CoinSound("sounds/coin.wav"),
    PlayerDrownSound("sounds/drown.wav");

    private final String filename; //name of file
    private Clip BGCLIP; // saves the clip for bg sound

    /**
     * Enum constructor for sounds
     * @param name - file path
     */
    LoadingSounds(String name) { this.filename = name;}

    /**
     * Loads the file for sound to each enum
     * @return returns an AudioInputStream used to find sound/load sound
     */
    AudioInputStream loadSound() {
        try{
            InputStream stream = LoadingImg.class.getResourceAsStream("/" + filename);//gets the file path as input stream
            if(stream == null) {throw new RuntimeException("Cannot find the sound file: " + filename);}
            return AudioSystem.getAudioInputStream(stream);
        }catch(Exception e) {throw new RuntimeException("Error loading sound: " + filename, e);}
    }

    /**
     * Method to play each sound effect
     * Makes each sound its own thread so sounds can overlap
     * @param volume - controls how loud the sound is
     */
    public void playSoundEffect(float volume) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(loadSound()); //gets audio data

                //controls volume of sound
                FloatControl changeVol = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                changeVol.setValue(volume);

                //ensures the clip plays fully and closes the clip
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) { clip.close();}
                });

                clip.start();
            } catch(Exception e) { throw new RuntimeException("Error playing sound effect: " + filename, e); }
        }).start(); //starts the thread
    }

    /**
     * Method to play background music in a loop
     * @param volume - controls how loud the sound is
     */
    public void playBackgroundMusic(float volume){
            try {
                if(BGCLIP == null){
                    BGCLIP = AudioSystem.getClip();
                    BGCLIP.open(loadSound()); //gets the audio
                }
                //controls volume of sound
                FloatControl changeVol = (FloatControl) BGCLIP.getControl(FloatControl.Type.MASTER_GAIN);
                changeVol.setValue(volume);

                BGCLIP.start();
                BGCLIP.loop(Clip.LOOP_CONTINUOUSLY); // loops the music
            } catch(Exception e) { throw new RuntimeException("Error playing background sound: " + filename, e); }
    }

    /**
     * Restarts the background music
     */
    public void restartBackgroundMusic(){
        if(BGCLIP != null){
            BGCLIP.stop();
            BGCLIP.setFramePosition(0); //resets the clip to beginning
        }
    }

    /**
     * Stops the background music
     */
    public void stopBackgroundMusic(){
        if(BGCLIP != null && BGCLIP.isRunning()){BGCLIP.stop(); }
    }
}