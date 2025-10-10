package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.util.*;
import nz.ac.wgtn.swen225.lc.app.controller.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.game.GameMapper;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.game.GameState;
import java.io.File;
import java.io.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Class for saving each movement of the Character
 * It coordinates with the App package, which calls
 * the addMovement method, passes an Input object
 * every time the character moves.
 *
 * @author Arushi Bhatnagar Stewart
 */
public class SaveL2 implements Save{
    public record Moves(Input direction, Integer timeLeftMilli){}
    public record FullGame(List<Moves> saveList, GameState state){};
    private static final SaveL2 saveInstance = new SaveL2();
    private List<Moves> playerList;
    // private List<> crabList;
    private final ObjectMapper mapper;
    private GameState gs;
    /** Method to add the Input objects (the directions/movements)
     * of the character to the movements list.
     *
     */
    private SaveL2(){
        playerList = new ArrayList<>();
        mapper = new ObjectMapper();
    }
    /**
     * Factory method to return singleton Save instance
     * @return
     */
    public static SaveL2 of() {
        return saveInstance;
    }
    /** */
    public void reset(){
        playerList = new ArrayList<>();
    }
    /** */
    public void startRecorder(AppController ac){
        Maze maze = ac.domain();
        int levelNumber = ac.level();
        int maxTreasures = ac.persistencyController().maxTreasures();
        int maxKeys = ac.persistencyController().maxKeys();
        int timeLeft = ac.timerController().getTimeLeft();
        gs = new GameMapper().toState(maze, levelNumber, maxTreasures, maxKeys, timeLeft);
    }
    public void updateMovement(Input direction, AppController ac, boolean toSave){
        if(toSave){
            saveToFile(ac);
            return;
        }
        addMovement(direction, ac);
    }
    /** */
    private void addMovement(Input direction, AppController ac) {
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        int timeLeft = ac.timerController().getPreciseTimeMillis();
        playerList.add(new Moves(direction, timeLeft));
    }
    /** This is the core method of this class.
     * In this method, we create a file and write
     * elements in the movement list to the file
     */
    private void saveToFile(AppController ac){
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        File playerMovements = chooseFile();
        if (playerMovements == null) {
            return;
        }
        FullGame fg = new FullGame(playerList, gs);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(playerMovements, fg);
        } catch(IOException e){
            JOptionPane.showMessageDialog(null, "Failed to save file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
