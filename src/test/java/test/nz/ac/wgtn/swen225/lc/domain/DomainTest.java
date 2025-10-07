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

    //-----------------------------------------
    //Tests for miniGame setup
    @Test
    void testMazeDimensions() {
        miniGame();
        assertEquals(5, maze.getRows());
        assertEquals(5, maze.getCols());

        assertThrows(IllegalArgumentException.class, ()-> {
            Maze invalidMaze = new Maze(0, 5);
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            Maze invalidMaze = new Maze(5, -1);
        });
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

        assertEquals(25, maze.getTileGrid().length * maze.getTileGrid()[0].length);
    }

    @Test
    void testPlayerStartPosition() {
        miniGame();
        assertThrows(IllegalArgumentException.class, ()-> {
            maze.setPlayer(null);
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            player.initialiseStartPos(0,0);
        });
        assertEquals(player, maze.getPlayer());
        assertEquals(new Position(2,2), player.getPos());
        assertEquals("P", maze.getSymbol(new Position(2,2)));
    }

    @Test
    void testPositions(){
        miniGame();
        Position p1 = new Position(1,1);
        assertThrows(IndexOutOfBoundsException.class, ()-> {
            Position p = new Position(-1,0);
        });
        p1.setX(3);
        p1.setY(4);
        assertEquals("x: 3 y: 4", p1.toString());
        assertEquals(true, p1.equals(new Position(3,4)));

        assertThrows(IllegalArgumentException.class, ()-> {
            p1.setX(-1);
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            p1.setY(-1);
        });
    }

    @Test
    void testFreeTile(){
        miniGame();

        //get free tile object to test
        Tile freeTile = maze.getTileAt(new Position(0,2));
        Free free = (Free) freeTile;
        assertEquals(true, free.equals(Free.of(new Position(0,2))));

        assertThrows(IllegalArgumentException.class, ()-> {
            Tile t = Free.of(null);
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            free.onEnter(null);
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            free.isAccessible(null);
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            free.setCollectable(null);
        });

    }

    @Test
    void testDirection(){
        miniGame();
        Direction d = player.getDirection(); //default direction is DOWN
        assertEquals(0, d.getDx());
        assertEquals(1, d.getDy());

        assertEquals(new Position(2,1), Direction.UP.apply(new Position(2,2)));
        assertThrows(IllegalArgumentException.class, ()-> {
            player.getDirection().apply(null);
        });
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

        assertThrows(IllegalArgumentException.class, ()-> {
            maze.movePlayer(null);
        });
    }

    @Test
    void testAliveState(){
        miniGame();
        assertThrows(IllegalArgumentException.class, ()-> {
            player.move(null);
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            player.addKey(null);
        });

        int treasuresBefore = player.getTreasuresCollected();
        player.collectTreasure();
        assertEquals(treasuresBefore+1, player.getTreasuresCollected());
    }

    @Test
    void testDeadState(){
        miniGame();
        player.die();
        player.move(Direction.LEFT);
        assertEquals(new Position(2,2), player.getPos()); //position should remain unchanged
        player.collectTreasure();
        assertEquals(0, player.getTreasuresCollected()); //treasure count should remain unchanged
        player.addKey(Key.of(EntityColor.GREEN));
        assertEquals(0, player.getKeys().size()); //key count should remain unchanged
        assertEquals(false, player.isAlive());
    }

    @Test
    void testWallImpassable() {
        miniGame();

        //get wall tile object to test
        Tile wallTile = maze.getTileAt(new Position(3,3));
        Wall wall = (Wall) wallTile;
        assertEquals(true, wall.equals(Wall.of(new Position(3,3))));


        maze.movePlayer(Direction.RIGHT);
        maze.movePlayer(Direction.DOWN); //wall tile
        assertEquals(Wall.of(new Position (3,3)), maze.getTileAt(new Position(3,3)));
        assertEquals(new Position(3,2), player.getPos()); // Position should remain unchanged
    }

    @Test
    void testTileExceptions(){
        miniGame();
        assertThrows(IllegalArgumentException.class, ()-> {
            maze.setTileAt(null);
        });

        assertThrows(IndexOutOfBoundsException.class, ()-> {
            maze.getTileAt(new Position(-1,0));
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            maze.setTileAt(null);
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            Tile t = Wall.of(null);
            maze.setTileAt(t);
        });

        assertThrows(IndexOutOfBoundsException.class, ()-> {
            Tile t = Wall.of(new Position(-1,-1));
            maze.setTileAt(t);
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            maze.targetTile(null, null);
        });
    }

    @Test
    void testCollectKey() {
        miniGame();

        //get green key object to test before and after collecting
        Tile keyTile = maze.getTileAt(new Position(4,2));
        Free freeTile = (Free) keyTile;
        Key greenKey = (Key) freeTile.getCollectable().get();
        assertEquals(true, greenKey.equals(Key.of(EntityColor.GREEN)));

        assertThrows(IllegalArgumentException.class, ()-> {
            greenKey.onInteract(null);
        });

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

    @Test
    void testDoor(){
        miniGame();
        //get door object to test before and after opening
        Tile doorTileBefore = maze.getTileAt(new Position(2,1));
        Free freeTileBefore = (Free) doorTileBefore;
        Door door = (Door) freeTileBefore.getCollectable().get(); //orange door
        assertEquals(true, door.equals(Door.of(EntityColor.ORANGE)));

        assertThrows(IllegalArgumentException.class, ()-> {
            door.onInteract(null);
        });

        assertThrows(IllegalStateException.class, ()-> { //player does not have key yet
            door.onInteract(player);
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            door.canInteract(null);
        });

        assertEquals(false, door.hasCorrectKey(player)); //should not have key yet
        player.addKey(Key.of(EntityColor.ORANGE));
        assertEquals(true, door.hasCorrectKey(player));
        assertEquals(true, door.canInteract(player)); //should be able to open now

        assertThrows(IllegalArgumentException.class, ()-> {
            door.hasCorrectKey(null);
        });

    }

    @Test
    void testTreasures(){
        miniGame();
        assertEquals(4, player.getTotalTreasures());
        assertEquals(0, player.getTreasuresCollected());

        //get treasure object to test before and after collecting
        Tile treasureTile = maze.getTileAt(new Position(1,0));
        Free freeTile = (Free) treasureTile;
        Treasure treasure = (Treasure) freeTile.getCollectable().get();
        assertEquals(true, treasure.equals(Treasure.of()));

        assertThrows(IllegalArgumentException.class, ()-> {
            treasure.onInteract(null);
        });

        player.collectTreasure();
        assertEquals(1, player.getTreasuresCollected());
        player.collectTreasure();
        player.collectTreasure();
        player.collectTreasure();
        assertEquals(true, player.allTreasuresCollected());
        assertThrows(AssertionError.class, ()-> {
            player.setTotalTreasures(-1);
        });
        assertThrows(IllegalStateException.class, ()-> {
            treasure.onInteract(player); //all treasures already collected
        });
    }

    @Test
    void exitLockAndVictory(){
        miniGame();
        player.setTotalTreasures(1);
        //get exit lock object to test before and after collecting all treasures
        Tile exitLockTile = maze.getTileAt(new Position(2,3));
        Free freeTile = (Free) exitLockTile;
        ExitLock exitLock = (ExitLock) freeTile.getCollectable().get();
        assertEquals(true, exitLock.equals(ExitLock.of()));

        //cannot pass through exit lock yet
        assertEquals(false, exitLock.canInteract(player));
        assertThrows(IllegalArgumentException.class, ()-> {
            exitLock.canInteract(null);
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            maze.movePlayer(null);
        });

        player.collectTreasure(); //collecting all treasures

        assertThrows(IllegalArgumentException.class, ()-> {
            exitLock.onInteract(null);
        });
        maze.movePlayer(Direction.DOWN); //to exit lock
        assertEquals(new Position(2,3), player.getPos()); //should be able to

        maze.movePlayer(Direction.DOWN); //to exit
        assertEquals(new Position(2,4), player.getPos()); //should be able to
    }

    @Test
    void testInfo(){
        miniGame();
        //get info tile object to test
        Tile infoTile = maze.getTileAt(new Position(1,2));
        Info info = (Info) infoTile;
        assertEquals(true, info.equals(Info.of("Info", new Position(1,2))));
    }

    @Test
    void testExit(){
        miniGame();
        //get exit tile object to test
        Tile exitTile = maze.getTileAt(new Position(2,4));
        Exit exit = (Exit) exitTile;
        assertEquals(true, exit.equals(Exit.of(new Position(2,4))));

        assertThrows(IllegalArgumentException.class, ()-> {
            exit.onEnter(null);
        });
    }

    //-----------------------------------------
    //Tests for monsterGame setup
    @Test
    void testLayoutMonster(){
        monsterGame();

        String expectedLayout =
                "W W W W \n" +
                "W F M F \n" +
                "W F P W \n" +
                "W W W W \n";
        assertEquals(expectedLayout, maze.toString());

        assertThrows(IllegalArgumentException.class, ()-> {
            maze.setMonster(null);
        });
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
    void testMonsterPosition(){
        monsterGame();
        List<Monster> monsters= maze.getMonsters();
        Monster m1 = monsters.get(0);

        assertEquals(Direction.LEFT, m1.getDirection());
        m1.updateDirection();
        assertEquals(Direction.RIGHT, m1.getDirection());
        m1.updateDirection();
        assertEquals(Direction.LEFT, m1.getDirection());
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

        maze.playerDead(); //should just return
    }

    //-----------------------------------------
    //Tests for waterGame setup
    @Test
    void testLayoutWater(){
        waterGame();

        String expectedLayout =
                "F ~ \n" +
                "~ P \n";
        assertEquals(expectedLayout, maze.toString());
    }

    @Test
    void testWaterTile(){
        waterGame();
        //get water tile object to test
        Tile waterTile = maze.getTileAt(new Position(0,1));
        Water water = (Water) waterTile;
        assertEquals(true, water.equals(Water.of(new Position(0,1))));

        assertThrows(IllegalArgumentException.class, ()-> {
            Tile t = Water.of(null);
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            water.onEnter(null);
        });

        assertEquals(new Position(1,1), player.getPos());
        maze.movePlayer(Direction.LEFT); //player going onto water tile
        assertEquals(false, player.isAlive());
    }
}
