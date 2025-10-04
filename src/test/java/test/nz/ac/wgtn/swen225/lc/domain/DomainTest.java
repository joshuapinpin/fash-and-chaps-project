package test.nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.entities.*;
import nz.ac.wgtn.swen225.lc.domain.tiles.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DomainTest class for testing domain classes, JUnits
 * Sets up a maze and player for testing purposes
 * Tests player movement, tile interactions, and maze layout
 * @author Hayley Far
 */
public class DomainTest {
    private Maze maze;
    private Player player;

    void miniGame(){
        maze = new Maze(5, 5);
        player = Player.of();
        player.initialiseStartPos(5,5);

        maze.setPlayer(player);
        this.player.setTotalTreasures(4); //for testing purposes, to be set when maze is loaded

        //need at least one observer to make sure it executes the consumer that picks up key etc
        //BECAUSE the picking up key logic (onEnter() in Free) is connected to the consumer
        maze.addObserver(new GameObserver() {});

        //setting tiles in the maze
        maze.setTileAt(Wall.of(new Position(0,0)));
        maze.setTileAt(Wall.of(new Position(0,1)));
        maze.setTileAt(Free.of(new Position(0,2)));

        Free tileWithKeyOrange = Free.of(new Position(0,3));
        tileWithKeyOrange.setCollectable(Key.of(EntityColor.ORANGE));
        maze.setTileAt(tileWithKeyOrange);

        maze.setTileAt(Free.of(new Position(0,4)));
        //--------------------------------
        Free tileWithTreasure = Free.of(new Position(1,0));
        tileWithTreasure.setCollectable(Treasure.of());
        maze.setTileAt(tileWithTreasure);

        maze.setTileAt(Wall.of(new Position(1,1)));
        maze.setTileAt(Info.of("Info", new Position(1,2)));
        maze.setTileAt(Wall.of(new Position(1,3)));
        maze.setTileAt(Wall.of(new Position(1,4)));
        //--------------------------------
        maze.setTileAt(Free.of(new Position(2,0)));
        Free tileWithDoorOrange = Free.of(new Position(2,1));
        tileWithDoorOrange.setCollectable(Door.of(EntityColor.ORANGE));
        maze.setTileAt(tileWithDoorOrange);

        maze.setTileAt(Free.of(new Position(2,2))); //player should be loaded here
        Free tileWithExitLock = Free.of(new Position(2,3));
        tileWithExitLock.setCollectable(ExitLock.of());
        maze.setTileAt(tileWithExitLock);

        maze.setTileAt(Exit.of(new Position(2,4)));
        //--------------------------------
        maze.setTileAt(Free.of(new Position(3,0)));
        maze.setTileAt(Wall.of(new Position(3,1)));
        maze.setTileAt(Free.of(new Position(3,2)));
        maze.setTileAt(Wall.of(new Position(3,3)));
        maze.setTileAt(Wall.of(new Position(3,4)));
        //--------------------------------
        maze.setTileAt(Wall.of(new Position(4,0)));
        maze.setTileAt(Wall.of(new Position(4,1)));

        Free tileWithKeyGreen = Free.of(new Position(4,2));
        tileWithKeyGreen.setCollectable(Key.of(EntityColor.GREEN));
        maze.setTileAt(tileWithKeyGreen);

        maze.setTileAt(Free.of(new Position(4,3)));
        maze.setTileAt(Free.of(new Position(4,4)));
    }

    void monsterGame(){
        maze = new Maze(4, 4);
        player = Player.of();
        player.initialiseStartPos(4,4);

        Monster monster = Monster.of(new Position(2,1));
        maze.setMonster(monster);

        maze.setPlayer(player);

        maze.setTileAt(Wall.of(new Position(0,0)));
        maze.setTileAt(Wall.of(new Position(0,1)));
        maze.setTileAt(Wall.of(new Position(0,2)));
        maze.setTileAt(Wall.of(new Position(0,3)));

        maze.setTileAt(Wall.of(new Position(1,0)));
        maze.setTileAt(Free.of(new Position(1,1)));
        maze.setTileAt(Free.of(new Position(1,2)));
        maze.setTileAt(Wall.of(new Position(1,3)));

        maze.setTileAt(Wall.of(new Position(2,0)));
        maze.setTileAt(Free.of(new Position(2,1)));
        maze.setTileAt(Free.of(new Position(2,2)));
        maze.setTileAt(Wall.of(new Position(2,3)));

        maze.setTileAt(Wall.of(new Position(3,0)));
        maze.setTileAt(Free.of(new Position(3,1)));
        maze.setTileAt(Wall.of(new Position(3,2)));
        maze.setTileAt(Wall.of(new Position(3,3)));

    }

    void waterGame(){
        maze = new Maze(2, 2);
        player = Player.of();
        player.initialiseStartPos(2,2);

        Monster monster = Monster.of(new Position(2,1));
        maze.setMonster(monster);

        maze.setPlayer(player);

        maze.setTileAt(Free.of(new Position(0,0)));
        maze.setTileAt(Water.of(new Position(0,1)));
        maze.setTileAt(Water.of(new Position(1,0)));
        maze.setTileAt(Free.of(new Position(1,1)));
    }

    @Test
    void testMazeDimensions() {
        miniGame();
        assertEquals(5, maze.getRows());
        assertEquals(5, maze.getCols());
    }

    @Test
    void testMazeLayout(){
        miniGame();

        String expectedLayout =
                "W T F F W \n" +
                "W W D W W \n" +
                "F I P F K \n" +
                "K W L W F \n" +
                "F W E W F \n";
        assertEquals(expectedLayout, maze.toString());
    }

    @Test
    void testPlayerStartPosition() {
        miniGame();
        assert player != null;
        assertEquals(new Position(2,2), player.getPos());
    }

    @Test
    void testPlayerMove() {
        miniGame();
        maze.movePlayer(Direction.UP);
        assertEquals(new Position(2,2), player.getPos()); //cannot through locked door
        maze.movePlayer(Direction.LEFT);
        assertEquals(new Position(1,2), player.getPos()); //on info tile
        maze.movePlayer(Direction.LEFT);
        maze.movePlayer(Direction.DOWN); //gets orange key
        maze.movePlayer(Direction.UP);

        assertThrows(IndexOutOfBoundsException.class, ()-> {
            maze.movePlayer(Direction.LEFT);
        });
    }

    @Test
    void testWallImpassable() {
        miniGame();
        maze.movePlayer(Direction.RIGHT);
        maze.movePlayer(Direction.DOWN); //wall tile
        assertEquals(Wall.of(new Position (3,3)), maze.getTileAt(new Position(3,3)));
        assertEquals(new Position(3,2), player.getPos()); // Position should remain unchanged
    }

    @Test
    void testCollectKey() {
        miniGame();
        maze.movePlayer(Direction.RIGHT);
        maze.movePlayer(Direction.RIGHT); // get green key
        assertEquals(1, player.getKeys().size());
        assertTrue(player.getKeys().contains(Key.of(EntityColor.GREEN)));
    }

    @Test
    void testOpenDoorAndTreasure() {
        miniGame();
        maze.movePlayer(Direction.LEFT);
        maze.movePlayer(Direction.LEFT);
        maze.movePlayer(Direction.DOWN); // gets orange key
        maze.movePlayer(Direction.UP);
        maze.movePlayer(Direction.RIGHT);
        maze.movePlayer(Direction.RIGHT); //back to the middle

        maze.movePlayer(Direction.UP); //to open door
        assertEquals(new Position(2,1), player.getPos()); //door should be open

        maze.movePlayer(Direction.UP);
        maze.movePlayer(Direction.LEFT); //collect treasure
        assertEquals(1, player.getTreasuresCollected());
    }

    //-------------------------------------
    @Test
    void testLayoutMonster(){
        monsterGame();

        String expectedLayout =
                "W W W W \n" +
                "W F M F \n" +
                "W F P W \n" +
                "W W W W \n";
        assertEquals(expectedLayout, maze.toString());
    }

    @Test
    void testMonsterMove(){
        monsterGame();
        List<Monster> monsters= maze.getMonsters();
        Monster m1 = monsters.get(0);

        //monsters start moving left, and starts at (2,1)
        maze.ping();
        assertEquals(new Position(1,1), m1.getPos());
        maze.ping(); //hits wall, should change direction and move
        assertEquals(new Position(2,1), m1.getPos());
        maze.ping();
        assertEquals(new Position(3,1), m1.getPos());
    }

    @Test
    void testMonsterCollision(){
        monsterGame();
        List<Monster> monsters= maze.getMonsters();
        Monster m1 = monsters.get(0);

        maze.movePlayer(Direction.UP);
        assertEquals(false, player.isAlive());
    }

    @Test
    void monsterMoveAndHitPlayer(){
        monsterGame();
        List<Monster> monsters= maze.getMonsters();
        Monster m1 = monsters.get(0);

        maze.ping(); //monster moves left
        maze.movePlayer(Direction.UP);
        maze.ping(); //monster moves right and hits player
        assertEquals(false, player.isAlive());
    }

    //-------------------------------
    @Test
    void testWaterTile(){
        waterGame();

        assertEquals(new Position(1,1), player.getPos());
        maze.movePlayer(Direction.LEFT); //player going onto water tile
        assertEquals(false, player.isAlive());
    }
}
