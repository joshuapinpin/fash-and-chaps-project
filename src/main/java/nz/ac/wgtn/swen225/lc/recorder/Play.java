package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.controller.*;
import nz.ac.wgtn.swen225.lc.app.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
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
    /**
     *
     */
    public Play(){
        movements = new ArrayList<>();
        mapper = new ObjectMapper();
    }
    private List<Input> getData() {
        /*
        using new TypeReference<List<MyObject>>() {} to create
        an anonymous subclass of TypeReference,
        it carries the actual generic type (List<Input>)
        in its class signature. Can't do List.class.
         */
        try {
            movements = mapper.readValue(new File("movements.json"), new TypeReference<List<Input>>() {
            });
        } catch (IOException e) {
            // rethrows checked exception as error
            throw new Error(e);
        }
        return movements;
    }

    public void setSpeed(int s) {
        // speed needs to be 1-6
        assert s > 0 : "Speed must me greater than zero";
        speed = s;
    }

    /**
     * Very basic implementation of step by step. Reads one input
     * from the list, everytime method is called.
     * Need to use the observer pattern.
     */
    public void stepByStep(GameController gm) {
        // call in case data has changed
        getData();
        if (movements.isEmpty()) throw new IllegalArgumentException("Character has not moved yet");
        Input direction = movements.get(pos);
        // pass direction to app method
        gm.handleInput(direction);
        System.out.println("step-by-step position: " + pos);
        System.out.println("step-by-step direction: " + direction);
        pos++;
    }

    /**
     * Very basic implementation. In one iteration, reads all the frames
     * Currently, doesn't implement speed.
     * Need to use the observer pattern.
     */
    public void autoPlay(GameController gm) {
        getData();
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
        p.stepByStep(x);
        p.stepByStep(x);
        p.stepByStep(x);
        p.stepByStep(x);
        p.autoPlay(x);
    }
}

