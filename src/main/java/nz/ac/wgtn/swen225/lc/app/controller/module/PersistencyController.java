package nz.ac.wgtn.swen225.lc.app.controller.module;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.persistency.Levels;

public class PersistencyController  {
    private AppController c;
    private Levels currentLevel;
    private int level;
    private int maxKeys;
    private int maxTreasures;
    private int maxTime;

    public PersistencyController(AppController c) {this.c = c;}

    public Levels loadLevel(int level){
        if(level == 1) this.currentLevel = Levels.LevelOne;
        else if(level == 2) this.currentLevel = Levels.LevelTwo;
        else throw new IllegalArgumentException("Invalid level: " + level);
        updateFields(level);
        return currentLevel;
    }

    private void updateFields(int level){
        this.level = level;
        this.maxKeys = currentLevel.maxKeys();
        this.maxTreasures = currentLevel.maxTreasures();
        this.maxTime = currentLevel.maxTime();
    }

    // ========== GETTERS ==========
    public void setLevel(int level){loadLevel(level);}

    public Levels currentLevel() {return currentLevel;}
    public int level() {return level;}
    public int maxKeys() {return maxKeys;}
    public int maxTreasures() {return maxTreasures;}
    public int maxTime() {return maxTime;}
}
