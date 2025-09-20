package test.nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.entities.*;
import nz.ac.wgtn.swen225.lc.domain.tiles.*;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DomainTest class for testing domain classes, JUnits
 * Sets up a maze and player for testing purposes
 */
public class DomainTest {
    private Maze maze;
    private Player player;

    @BeforeEach
    void setup() {
        // Initialize maze and player for testing
        maze = new Maze(3, 3);
        player = Player.getInstance();
        player.initialiseStartPos(3,3);

        maze.setPlayer(player);

        //setting tiles in the maze
        maze.setTileAt(Wall.of(new Position(0,0)));
        maze.setTileAt(Wall.of(new Position(0,1)));
        maze.setTileAt(Wall.of(new Position(0,2)));
        maze.setTileAt(Free.of(new Position(1,0)));
        maze.setTileAt(Free.of(new Position(1,1)));
        maze.setTileAt(Free.of(new Position(1,2)));
        maze.setTileAt(Free.of(new Position(2,0)));

        //adding key with tile
        Free tileWithKey = Free.of(new Position(2,1));
        tileWithKey.setCollectable(Key.of("Red"));
        maze.setTileAt(tileWithKey);

        maze.setTileAt(Free.of(new Position(2,2)));
    }

    @Test
    void testMazeDimensions() {
        setup();
        assertEquals(3, maze.getRows());
        assertEquals(3, maze.getCols());
    }

    @Test
    void testMazeLayout(){
        setup();

        String expectedLayout =
                "W F F \n" +
                "W P K \n" +
                "W F F \n";
        assertEquals(expectedLayout, maze.toString());
    }

    @Test
    void testPlayerStartPosition() {
        setup();
        assert player != null;
        assertEquals(new Position(1,1), player.getPos());
    }

    @Test
    void testPlayerMove() {
        setup();
        player.move(Direction.UP);
        assertEquals(new Position(1,0), player.getPos());
        assertThrows(IndexOutOfBoundsException.class, () -> {
            maze.movePlayer(Direction.UP);
        });
        player.move(Direction.DOWN);
        assertEquals(new Position(1,1), player.getPos());
        player.move(Direction.LEFT);
        assertEquals(new Position(0,1), player.getPos());
        player.move(Direction.RIGHT);
        assertEquals(new Position(1,1), player.getPos());
    }

    @Test
    void testCollectKey() {
        setup();
        // Move player to the tile with the key
        maze.movePlayer(Direction.DOWN); // (1,2)
        maze.movePlayer(Direction.RIGHT); // (2,2)
        maze.movePlayer(Direction.UP); // (2,1) - Tile with key

        // Check if player has collected the key
        assertEquals(1, player.getKeys().size());
        assertTrue(player.getKeys().contains(Key.of("Red")));
    }

    @Test
    void testWallImpassable() {
        setup();
        maze.movePlayer(Direction.LEFT);
        assertEquals(Wall.of(new Position(0,1)), maze.getTileAt(new Position(0,1)));
        assertEquals(new Position(1,1), player.getPos()); // Position should remain unchanged
    }


}
