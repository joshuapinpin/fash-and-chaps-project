package test.nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.domain.Direction;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.EntityColor;
import nz.ac.wgtn.swen225.lc.domain.Key;
import nz.ac.wgtn.swen225.lc.persistency.Persist;
import nz.ac.wgtn.swen225.lc.persistency.PersistManager;
import nz.ac.wgtn.swen225.lc.persistency.Mapper;
import nz.ac.wgtn.swen225.lc.persistency.GameMapper;
import nz.ac.wgtn.swen225.lc.persistency.GameState;
import nz.ac.wgtn.swen225.lc.persistency.LoadedMaze;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersistTest {
    private final int levelNumber = 1;
    private final int maxTreasures = 4;
    private final int maxKeys = 2;
    private final int time = 60;
    private final String save =
            """
                    {\
                      "rows" : 4,\
                      "cols" : 4,\
                      "time" : 60,\
                      "levelInfo" : {\
                      "levelNumber" : 1,\
                      "maxKeys" : 2,\
                      "maxTreasures" : 4\
                      },\
                      "player" : {\
                        "x" : 1,\
                        "y" : 1,\
                        "treasures" : 1,\
                        "maxTreasures" : 2,\
                        "direction" : "UP",\
                        "keyColors" : [ "GREEN" ]\
                      },\
                      "board" : [\
                        [ "F:Crab-RIGHT", "F:Door-ORANGE", "F:Door-GREEN:Crab-LEFT", "F" ]\
                        , [ "F:Key-ORANGE", "F:Key-GREEN:Crab-LEFT", "F:Treasure", "F:ExitLock" ]\
                        , [ "W", "~", "I", "E" ]\
                        , [ "~", "~", "~", "~" ]\
                      ]\
                    }\
            """;

    private static Player defaultPlayer() {
        Player player = Player.of();
        player.addKey(Key.of(EntityColor.GREEN));
        player.setPos(new Position(1, 1));
        player.setDirection(Direction.UP);
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

    @Test
    public void testSerialisation() {
        MockPersistManager mockManager = new MockPersistManager(save);
        Persist persist = new Persist(()->mockManager);
        Optional<LoadedMaze> game = persist.loadGame(null);
        assertTrue(game.isPresent());
        persist.saveGame(game.get().maze(), levelNumber, maxTreasures, maxKeys, time, null);
        String saved = mockManager.saveLog.getLast();
        assertEquals(save.replaceAll("\\s+", ""), saved.replaceAll("\\s+", "")); // so test OS independent
    }
}

class MockPersistManager implements PersistManager<LoadedMaze> {
    Mapper<LoadedMaze, GameState> stateMapper = new GameMapper();
    List<String> saveLog = new ArrayList<>();
    String mockSave;

    MockPersistManager(String mockSave) {
        this.mockSave = mockSave;
    }

    @Override
    public boolean save(LoadedMaze data, JFrame parent) { // do nothing with parent window and file dialogs
        try {
            GameState state = stateMapper.toState(data);
            state.setPlayer(GameStateTest.playerState);
            String save = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(state);
            saveLog.add(save);
            return true;
        } catch (JsonProcessingException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public Optional<LoadedMaze> load(JFrame parent) { // do nothing with parent window and file dialogs
        try {
            GameState game = new ObjectMapper().readValue(mockSave, GameState.class);
            return Optional.of(stateMapper.fromState(game));
        } catch (JsonProcessingException e) {
            throw new AssertionError(e);
        }
    }
}