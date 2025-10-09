package test.nz.ac.wgtn.swen225.lc.persistency.save;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.domain.Direction;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.entities.EntityColor;
import nz.ac.wgtn.swen225.lc.domain.entities.Key;
import nz.ac.wgtn.swen225.lc.persistency.saver.Persist;
import nz.ac.wgtn.swen225.lc.persistency.saver.PersistManager;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import test.nz.ac.wgtn.swen225.lc.persistency.levelloader.GameStateTest;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersistTest {
    private String save =
            "{"
            +"\n  \"rows\" : 4,"
            +"\n  \"cols\" : 4,"
            +"\n  \"board\" : ["
            +"\n [ \"F:Crab-RIGHT\", \"F:Door-ORANGE\", \"F:Door-GREEN:Crab-LEFT\", \"F\" ]"
            +"\n, [ \"F:Key-ORANGE\", \"F:Key-GREEN:Crab-LEFT\", \"F:Treasure\", \"F:ExitLock\" ]"
            +"\n, [ \"W\", \"~\", \"I:temporary\", \"E\" ]"
            +"\n, [ \"~\", \"~\", \"~\", \"~\" ]"
            +"\n ]"
            +"\n}";

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

    @Disabled // TODO: update the test string to include player
    @Test
    public void testSerialisation() {
        MockPersistManager mockManager = new MockPersistManager(save);
        Persist<Maze> persist = new Persist<>(()->mockManager);
        Optional<Maze> game = persist.loadGame(blankWindow());
        assertTrue(game.isPresent());
        persist.saveGame(game.get(), blankWindow());
        String saved = mockManager.saveLog.getLast();
        assertEquals(save, saved.replace("\r\n", "\n")); // so test OS independent
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
    Mapper<Maze, GameState> stateMapper = new GameMapper();
    List<String> saveLog = new ArrayList<>();
    String mockSave;

    MockPersistManager(String mockSave) {
        this.mockSave = mockSave;
    }

    @Override
    public void save(Maze data, JFrame parent) { // do nothing with parent window and file dialogs
        try {
            GameState state = stateMapper.toState(data);
            state.setPlayer(GameStateTest.playerState);
            String save = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(state);
            saveLog.add(save);
        } catch (JsonProcessingException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public Optional<Maze> load(JFrame parent) { // do nothing with parent window and file dialogs
        try {
            GameState game = new ObjectMapper().readValue(mockSave, GameState.class);
            return Optional.of(stateMapper.fromState(game));
        } catch (JsonProcessingException e) {
            throw new AssertionError(e);
        }
    }
}