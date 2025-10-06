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

    public void playerDead(){
        if(!player.isAlive()){return;}
        player.die();
        observers.forEach(observer -> observer.onPlayerDie(player));
    }

    /**
     * Get target tile in specified direction from current position
     * Used by player and monsters to determine next tile
     * @param currentPos current position
     * @param direction direction to get target tile
     * @return target tile in direction from current position
     */
    public Tile targetTile(Position currentPos, Direction direction){
        if(currentPos == null || direction == null){
            throw new IllegalArgumentException("Player not set in maze");
        }

        Position toMove = direction.apply(currentPos);
        return getTileAt(toMove);
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

        Tile targetTile = targetTile(player.getPos(), direction);

        if(targetTile.isAccessible(this.player)){
            player.move(direction);

            for(Monster m : monsters){
                if(m.getPos().equals(player.getPos())){
                    playerDead();
                }
            }

            Consumer<GameObserver> event = targetTile.onEnter(player);
            observers.forEach(event); //notify all observers of this event

            assert player.getPos().equals(targetTile.getPos()) : "Player did not move to the correct position";
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
            Tile targetTile = targetTile(m.getPos(), m.getDirection());

            if (targetTile instanceof Wall) {
                m.updateDirection();
            }

            m.move();
            if(m.getPos().equals(player.getPos())){
                playerDead();
            }
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
     * Getter for list of monsters in maze
     * @return list of monsters
     */
    public List<Monster> getMonsters(){
        return this.monsters;
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
     * ~ = Water, W = Wall, F = Free, E = Exit, I = Info, M = Monster
     * Used for JUnit testing to verify maze layout
     * @param pos position to get symbol for
     * @return symbol representing tile at position
     */
    public String getSymbol(Position pos) {
        if (player != null && player.getPos().equals(pos)) return "P";
        if(monsters.stream().anyMatch(m-> m.getPos().equals(pos))) return "M";
        Tile tile = getTileAt(pos);

        return tile.accept(new TileVisitor<String>() {
            @Override
            public String visitWall(Wall wall) {return "W";}

            @Override
            public String visitFree(Free free) {
                if(free.getCollectable().isPresent()){
                    Entity entity = free.getCollectable().get();
                    return entity.accept(new EntityVisitor<String>() {
                        @Override
                        public String visitKey(Key key) {return "K";}

                        @Override
                        public String visitDoor(Door door) {return "D";}

                        @Override
                        public String visitExitLock(ExitLock exitLock) {return "L";}

                        @Override
                        public String visitTreasure(Treasure treasure) {return "T";}
                    });
                }
                return "F";
            }

            @Override
            public String visitInfo(Info info) {return "I";}

            @Override
            public String visitWater(Water water) {return "~";}

            @Override
            public String visitExit(Exit exit) {return "E";}
        });
    }
}
