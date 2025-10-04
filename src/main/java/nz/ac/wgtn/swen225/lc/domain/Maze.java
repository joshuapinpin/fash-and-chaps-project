package nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.entities.*;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;
import nz.ac.wgtn.swen225.lc.domain.tiles.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Maze class representing the maze structure
 * Contains tiles, player reference, and dimensions
 * Provides methods to load maze from file and get tiles at positions
 * @author Hayley Far
 */
public class Maze {
    private Tile[][] tileGrid; //2D array of tiles: [rows][cols]
    private Player player; //reference to player in maze
    private int rows; int cols; //dimensions of maze
    private List<GameObserver> observers = new ArrayList<>();
    private List<Monster> monsters = new ArrayList<>();

    /**
     * Constructor for maze with specified dimensions
     * @param rows number of rows
     * @param cols number of columns
     */
    public Maze(int rows, int cols){
        if(rows <= 0 || cols <= 0){
            throw new IllegalArgumentException("Maze dimensions must be positive");
        }
        this.rows = rows;
        this.cols = cols;
        this.tileGrid = new Tile[rows][cols];

        //initialise player and set start position
        this.player = Player.of();
        this.player.initialiseStartPos(rows, cols);
        this.player.setTotalTreasures(4); //for testing purposes, to be set when maze is loaded

        assert player != null : "Player instance is null";
        assert tileGrid != null : "Tile grid is null";

        //JUST FOR TESTING, REMOVE LATER as observers to be made by app and renderer
        this.addObserver(new GameObserver() {
            @Override
            public void onPlayerMove(Position newPosition) {
                System.out.println("Player moved");
            }

            @Override
            public void onKeyCollected(Key key) {
                System.out.println("Key collected");
            }

            @Override
            public void onTreasureCollected() {
                System.out.println("Treasure collected");
            }

            @Override
            public void onDoorOpened(Door door) {
                System.out.println("Door opened");
            }

            @Override
            public void onInfoMessage() {
                System.out.println("Info tile triggered!");
            }

            @Override
            public void onLevelComplete() {
                System.out.println("Level completed!");
            }

            @Override
            public void onPlayerDie(Player player) {
                System.out.println("Player died!");
            }
        });
    }

    /**
     * Adding an observer to the maze
     * @param o observer to add
     */
    public void addObserver(GameObserver o){
        this.observers.add(o);
    }

    /**
     * Get 2D array of tiles in maze
     * @return 2D array of tiles
     */
    public Tile[][] getTileGrid() {
        return tileGrid;
    }

    /**
     * Get tile at specified position
     * @param p position to get tile at
     * @return tile at position
     */
    public Tile getTileAt(Position p){
        if(p == null){
            throw new IllegalArgumentException("Position cannot be null");
        }
        int x = p.getX();
        int y = p.getY();
        if (x < 0 || x >= cols || y < 0 || y >= rows) {
            //may need to look at this with more testing
            throw new IndexOutOfBoundsException("Position out of maze bounds: " + p);
        }
        Tile tile = tileGrid[y][x];
        assert tile != null : "Tile at position " + p + " is null";

        return tile;
    }

    /**
     * Add tile to 2D tile array at specified position
     * @param tile tile to set
     */
    public void setTileAt(Tile tile) {
        if(tile == null){
            throw new IllegalArgumentException("Tile cannot be null");
        }
        Position pos = tile.getPos();
        if(pos == null){
            throw new IllegalArgumentException("Tile position cannot be null");
        }

        int x = pos.getX();
        int y = pos.getY();
        if (x < 0 || x >= cols || y < 0 || y >= rows) {
            throw new IndexOutOfBoundsException("Position out of maze bounds: " + pos);
        }
        tileGrid[y][x] = tile;

        assert getTileAt(pos) == tile : "Tile was not set correctly at position: " + pos;
    }

    /**
     * Move player in specified direction if target tile is accessible
     * The direction dependent on what user input is
     * Notify observers of player movement and any entity interactions when player enters a tile
     * @param direction direction to move player
     */
    public void movePlayer(Direction direction){
        System.out.println("*DEBUG* Inside of the Domain Package Now");

        //find tile in the direction player wants to move
        if(player == null || direction == null){
            throw new IllegalArgumentException("Player not set in maze");
        }

        Position toMove = direction.apply(player.getPos());
        Tile targetTile = getTileAt(toMove);

        if(targetTile.isAccessible(this.player)){
            player.move(direction);
            Consumer<GameObserver> event = targetTile.onEnter(player);
            observers.forEach(event); //notify all observers of this event

            assert player.getPos().equals(toMove) : "Player did not move to the correct position";
        }
        player.setDirection(direction); //update player direction regardless of move success
        assert player.getDirection() == direction : "Player direction not updated correctly";
    }

    /**
     * Update all monsters in the maze
     * Each monster moves in its current direction
     * If a monster collides with a wall, it updates its direction
     * Moves monster and checks for collision with player after move
     * Notify observers of any events (e.g., player death)
     */
    //this will be called by app/controller every game tick
    //the monsters if for loop may not be in sync
    public void ping() {
        Consumer <GameObserver> collisionEvent = observer -> {};

        for (Monster m : monsters) {
            //check if next tile in direction is a wall
            Position toMove = m.getDirection().apply(m.getPos());
            Tile targetTile = getTileAt(toMove);

            if (targetTile instanceof Wall) {
                m.updateDirection();
            }

            m.move();
            collisionEvent = m.checkCollisionWithPlayer(this.player);

            observers.forEach(collisionEvent);
        }
    }

    /**
     * Getter for number of rows in maze
     * @return number of rows
     */
    public int getRows(){
        return this.rows;
    }

    /**
     * Getter for number of columns in maze
     * @return number of columns
     */
    public int getCols(){
        return this.cols;
    }

    /**
     * Setter for player reference in maze
     * @param player player to set
     */
    public void setPlayer(Player player){
        if(player == null){
            throw new IllegalArgumentException("Player cannot be null");
        }
        this.player = player;
    }

    /**
     * Getter for player reference in maze
     * @return player in maze
     */
    public Player getPlayer(){
        return this.player;
    }

    public void setMonster(Monster m){
        if(m == null){
            throw new IllegalArgumentException("Monster cannot be null");
        }
        this.monsters.add(m);
    }

    /**
     * String representation of maze for debugging in JUnit tests
     * Going through each tile in maze dimensions and getting symbol for it
     * @return string representation of maze
     */
    @Override
    public String toString(){
        String result = "";
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                Position pos = new Position(c, r);
                String symbol = getSymbol(pos);
                result += symbol + " ";
            }
            result += "\n";
        }
        return result;
    }

    /**
     * Get symbol for tile at specified position for string representation
     * P = Player, K = Key, D = Door, T = Treasure, L = ExitLock
     * ~ = Water, W = Wall, F = Free, E = Exit, I = Info
     * @param pos position to get symbol for
     * @return symbol representing tile at position
     */
    public String getSymbol(Position pos) {
        if (player != null && player.getPos().equals(pos)) return "P";
        Tile tile = getTileAt(pos);

        if (tile instanceof Wall) {
            return "W";
        } else if (tile instanceof Free) {
            Free freeTile = (Free) tile;
            if (freeTile.getCollectable().isPresent()) {
                Entity entity = freeTile.getCollectable().get();
                if (entity instanceof Key) {
                    return "K";
                } else if (entity instanceof Door) {
                    return "D";
                } else if (entity instanceof Treasure) {
                    return "T";
                } else if (entity instanceof ExitLock) {
                    return "L";
                }
            }
        } else if (tile instanceof Exit) {
            return "E";
        } else if (tile instanceof Water) {
            return "~";
        } else if (tile instanceof Info){
            return "I";
        }
        return "F";
    }
}
