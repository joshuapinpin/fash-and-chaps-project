package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.controller.*;
import nz.ac.wgtn.swen225.lc.app.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Initial outline for class to replay the actions of the character.
 * Need to change the algorithm and use the observer pattern.
 *
 * @author Arushi Bhatnagar Stewart
 */

// use game controller handle input?
public class Play {
    private static int speed;
    private static int pos; // count for step by step playing
    final ObjectMapper mapper;
    private static List<Input> movements;
    private InputStream fileStream;

    public Play(){
        movements = new ArrayList<>();
        mapper = new ObjectMapper();
        pos = 0;
        speed = 1;
    }
    public void setSpeed(int s) {
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        // speed needs to be 1-6
        assert s > 0 : "Speed must me greater than zero";
        speed = s;
    }
    public void LoadingFile(String filename){
        try{
            // putting a bracket in front of filename means it looks for file from root of classpath
            // InputStream object used to read file contents
            fileStream = Play.class.getResourceAsStream("/" + filename);
            if(fileStream == null) {throw new RuntimeException("Cannot find recording: " + filename);}
            fileStream.close();
        } catch(IOException e){ throw new RuntimeException("Error loading the recording: " + filename, e);}
    }
    /**
     * This methods reads the list of movements from the json file
     * and assigns it to our movement arraylist field.
     */
    private List<Input> getData(String filename) {
        LoadingFile(filename);
        /*
        using new TypeReference<List<MyObject>>() {} to create
        an anonymous subclass of TypeReference,
        it carries the actual generic type (List<Input>)
        in its class signature. Can't do List.class.
         */
        try {
            movements = mapper.readValue(fileStream, new TypeReference<List<Input>>() {
            });
        } catch (IOException e) {
            // rethrows checked exception as error
            throw new Error(e);
        }
        return movements;
    }

    /**
     * Very basic implementation of step by step. Reads one input
     * from the list, everytime method is called.
     * Need to use the observer pattern.
     */
    public boolean stepByStep(GameController gm, String filename) {
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        // make the method return true while we still have positions to go
        if(pos == movements.size()){pos = 0; return false;}
        getData(filename);
        // call in case data has changed
        getData(filename);
        if (movements.isEmpty()) throw new IllegalArgumentException("Character has not moved yet");
        Input direction = movements.get(pos);
        // pass direction to app method
        gm.handleInput(direction);
        System.out.println("step-by-step position: " + pos);
        System.out.println("step-by-step direction: " + direction);
        pos++;
        return true;
    }

    /**
     * Very basic implementation. In one iteration, reads all the frames
     * Currently, doesn't implement speed.
     * Need to use the observer pattern.
     */
    public void autoPlay(GameController gm, String filename) {
        System.out.println("*DEBUG* Inside of the Recorder Package Now");
        getData(filename);
        if (movements.isEmpty()) throw new IllegalArgumentException("Character has not moved yet");
        for (int frame = 0; frame < movements.size(); frame++) {
            System.out.println("autoplay position: " + frame);
            Input frame1 = movements.get(frame);
            gm.handleInput(frame1);
            System.out.println("autoplay direction: " + frame1);
        }
    }
    public static void main(String[] args) {
        Play p = new Play();
        p.setSpeed(2);
        GameController x = GameController.of();
        p.stepByStep(x, "movements.json");
        p.stepByStep(x, "movements.json");
        p.stepByStep(x, "movements.json");
        p.stepByStep(x, "movements.json");
        p.autoPlay(x, "movements.json");
    }
}

