package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Class for saving each movement of the Character
 * It coordinates with the App package, which calls
 * the addMovement method, passes an Input object
 * every time the character moves.
 *
 * @author Arushi Bhatnagar Stewart
 */
public class Save{
    private static final List<Input> movements = new ArrayList<>();
    final static ObjectMapper mapper = new ObjectMapper();
    private static final Save instance = new Save();
    private Save() {}// Private constructor to prevent instantiation
    /** Singleton pattern, as we don't need multiple Save objects. */
    public static Save instance() { return instance; }
    /** Method to add the Input objects (the directions/movements)
     * of the character to the movements list.
     */
    public static void addMovement(Input direction) {
        movements.add(direction);
    }
    /** Returns movement list. */
    public static List<Input> movements() {
        return movements;
    }
    /** This is the core method of this class.
     * In this method, we create a file and write
     * elements in the movement list to the file
     */
    public static void saveToFile(){
        File playerMovements = new File("movements.json");
        try {
            mapper.writeValue(playerMovements, movements);
        } catch(IOException e){
            // rethrows checked exception as error
            throw new Error(e);
        }
    }
    public static void main(String[] args){
        addMovement(Input.MOVE_UP);
        addMovement(Input.MOVE_DOWN);
        addMovement(Input.MOVE_LEFT);
        addMovement(Input.MOVE_RIGHT);
        saveToFile();
    }
}
