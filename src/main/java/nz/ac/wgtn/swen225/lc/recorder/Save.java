package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * Class for saving each movement of the Character
 * It coordinates with the App package, which calls
 * the addMovement method, passes an Input object
 * every time the character moves.
 * Need to change the algorithm and use the observer pattern.
 *
 * @author Arushi Bhatnagar Stewart
 */
public class Save{
    private final List<Input> movements;
    final ObjectMapper mapper;
    private String filename;
    /** Method to add the Input objects (the directions/movements)
     * of the character to the movements list.
     *
     */
    public Save(){
        movements = new ArrayList<>();
        mapper = new ObjectMapper();
    }
    public void addMovement(Input direction) {
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        movements.add(direction);
    }
    /** Returns movement list. */
    public List<Input> movements() {
        return movements;
    }
    /** This is the core method of this class.
     * In this method, we create a file and write
     * elements in the movement list to the file
     */
    public void saveToFile(){
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        File playerMovements = new File("movements.json");
        try {
            mapper.writeValue(playerMovements, movements);
        } catch(IOException e){
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        Save s = new Save();
        s.addMovement(Input.MOVE_UP);
        s.addMovement(Input.MOVE_DOWN);
        s.addMovement(Input.MOVE_LEFT);
        s.addMovement(Input.MOVE_RIGHT);
        s.saveToFile();
    }
}
