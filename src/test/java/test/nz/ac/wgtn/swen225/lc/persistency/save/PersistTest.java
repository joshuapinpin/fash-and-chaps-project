package test.nz.ac.wgtn.swen225.lc.persistency.save;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.domain.Direction;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.entities.EntityColor;
import nz.ac.wgtn.swen225.lc.domain.entities.Key;
import nz.ac.wgtn.swen225.lc.persistency.saver.PersistManager;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersistTest {

    private static Player defaultPlayer() {
        Player player = Player.of();
        player.addKey(Key.of(EntityColor.GREEN));
        player.addKey(Key.of(EntityColor.ORANGE));
        player.setPos(new Position(1, 2));
        player.setDirection(Direction.RIGHT);
        player.setTotalTreasures(3);
        player.collectTreasure();
        return player;
    }

    private static void assertDefaultPlayer(Player player) {
        assertEquals(List.of(Key.of(EntityColor.GREEN), Key.of(EntityColor.ORANGE)), player.getKeys());
        assertEquals(new Position(1, 2), player.getPos());
        assertEquals(Direction.RIGHT, player.getDirection());
        assertEquals(3, player.getTotalTreasures());
        assertEquals(1, player.getTotalTreasures());
    }

    private static JFrame blankWindow() {
        JFrame frame = new JFrame("Testing window, parent to file dialogs.");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setVisible(true);
        return frame;
    }

    @Test
    public void testSerialisation() {
        // use persist and a MockPersistManager
    }

//    @Test
//    public void testPlayerRoundTrip() {
//        ObjectMapper mapper = new ObjectMapper();
//        Player player = defaultPlayer();
//        try {
//            String serialised = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(player);
//            Player deserialised = mapper.readValue(serialised, Player.class);
//            assertDefaultPlayer(deserialised);
//        } catch (JsonProcessingException e) {
//            throw new AssertionError(e);
//        }
//    }
}

class MockPersistManager implements PersistManager<Maze> {
    ObjectMapper mapper = new ObjectMapper();
    List<String> log = new ArrayList<>();
    String mockSave;

    MockPersistManager(String mockSave) {
        this.mockSave = mockSave;
    }

    @Override
    public void save(Maze data, JFrame parent) { // do nothing with parent window and file dialogs
        try {
            String save = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
            log.add(save);
        } catch (JsonProcessingException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public Optional<Maze> load(JFrame parent) { // do nothing with parent window and file dialogs
        try {
            return Optional.of(mapper.readValue(mockSave, Maze.class));
        } catch (JsonProcessingException e) {
            throw new AssertionError(e);
        }
    }
}