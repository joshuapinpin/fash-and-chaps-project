package nz.ac.wgtn.swen225.lc.app.controller.module;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.persistency.Levels;
import nz.ac.wgtn.swen225.lc.persistency.GamePersist;
import nz.ac.wgtn.swen225.lc.persistency.LoadedMaze;

import java.util.Optional;

/**
 * Controller for handling game persistency (saving and loading).
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class PersistencyController  {
    private AppController c;
    private Levels currentLevel;
    private GamePersist persist;
    private int level;
    private int maxKeys;
    private int maxTreasures;
    private int maxTime;

    /**
     * Constructor for PersistencyController.
     * @param c Reference to the main AppController
     */
    public PersistencyController(AppController c) {
        this.c = c;
        this.persist = new GamePersist();
    }

    /**
     * Loads the specified level.
     * @param level Level number to load (1 or 2)
     * @return The loaded level enum
     * @throws IllegalArgumentException if the level number is invalid
     */
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

    /**
     * Loads a saved game if one exists.
     * If no saved game exists, does nothing.
     */
    public void loadGame(){
        Optional<LoadedMaze> domainOptional = persist.loadGame(c.windowController().window());

        c.inputController().clearPressedKeys();// To prevent stuck keys

        if(domainOptional.isEmpty()) return;
        c.windowController().window().requestFocusInWindow();
        LoadedMaze lm = domainOptional.get();
        level = lm.levelInfo().levelNumber();
        loadLevel(level);
        c.domainController().updateDomain(lm.maze());
        c.timerController().startTimerFrom(lm.time());
        c.windowController().atNewGame();
        c.rendererController().atNewGame();
        c.continueGame();
    }

    /**
     * Saves the current game state.
     */
    public boolean saveGame(){
        boolean successful = persist.saveGame(c.domainController().domain(),
                currentLevel.levelNumber(),
                maxTreasures,
                maxKeys,
                c.timerController().getTimeLeft(),
                c.windowController().window());
        c.inputController().clearPressedKeys();
        return successful;
    }


    // ========== GETTERS ==========
    public void setLevel(int level){loadLevel(level);}

    public Levels currentLevel() {return currentLevel;}
    public int level() {return level;}
    public int maxKeys() {return maxKeys;}
    public int maxTreasures() {return maxTreasures;}
    public int maxTime() {return maxTime;}
}
