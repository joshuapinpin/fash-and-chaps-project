package nz.ac.wgtn.swen225.lc.renderer.sounds;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public enum LoadingSounds {
    VictorySound(""),
    LosingSound(""),
    WalkingSound(""),
    BackgroundSound(""),
    UnlockedSound(""),
    KeySound(""),
    CoinSound(""),
    PlayerDrownSound(""),
    PlayerCrabSound("");

    private final String filename;

    LoadingSounds(String name) {
        this.filename = name;



    }

}
