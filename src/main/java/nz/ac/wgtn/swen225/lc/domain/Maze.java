package nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.entities.*;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;
import nz.ac.wgtn.swen225.lc.domain.tiles.*;

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
        this.player = Player.getInstance();
        this.player.initialiseStartPos(rows, cols);
        this.player.setTreasuresLeft(2); //for testing purposes, to be set when maze is loaded

        assert player != null : "Player instance is null";
        assert tileGrid != null : "Tile grid is null";
    }

    /**
     * Add default tiles to maze for testing purposes for integration
     * To delete later
     * In a real implementation, tiles would be loaded from a file
     */
    public void addTiles(){
        setTileAt(Free.of(new Position(0,0)));
        setTileAt(Free.of(new Position(1,0)));
        setTileAt(Wall.of(new Position(2,0)));
        setTileAt(Free.of(new Position(3,0)));
        setTileAt(Free.of(new Position(4,0)));
        setTileAt(Free.of(new Position(5,0)));
        setTileAt(Wall.of(new Position(6,0)));
        setTileAt(Free.of(new Position(7,0)));
        setTileAt(Free.of(new Position(8,0)));

        Free tileWithTreasure = Free.of(new Position(0,1));
        tileWithTreasure.setCollectable(Treasure.of());
        setTileAt(tileWithTreasure);
        setTileAt(Free.of(new Position(1,1)));
        setTileAt(Wall.of(new Position(2,1)));
        setTileAt(Free.of(new Position(3,1)));
        setTileAt(Free.of(new Position(4,1)));
        Free tileWithKey = Free.of(new Position(5,1));
        tileWithKey.setCollectable(Key.of(EntityColor.ORANGE));
        setTileAt(tileWithKey);
        setTileAt(Wall.of(new Position(6,1)));
        setTileAt(Free.of(new Position(7,1)));
        setTileAt(Free.of(new Position(8,1)));

        setTileAt(Wall.of(new Position(0,2)));
        setTileAt(Wall.of(new Position(1,2)));
        setTileAt(Wall.of(new Position(2,2)));
        setTileAt(Free.of(new Position(3,2)));
        setTileAt(Free.of(new Position(4,2)));
        setTileAt(Free.of(new Position(5,2)));
        setTileAt(Wall.of(new Position(6,2)));
        setTileAt(Wall.of(new Position(7,2)));
        setTileAt(Wall.of(new Position(8,2)));

        setTileAt(Free.of(new Position(0,3)));
        setTileAt(Wall.of(new Position(1,3)));
        setTileAt(Free.of(new Position(2,3)));
        setTileAt(Free.of(new Position(3,3)));
        setTileAt(Free.of(new Position(4,3)));
        setTileAt(Free.of(new Position(5,3)));
        setTileAt(Free.of(new Position(6,3)));
        setTileAt(Wall.of(new Position(7,3)));
        setTileAt(Free.of(new Position(8,3)));

        setTileAt(Free.of(new Position(0,4)));
        Free tileWithDoor = Free.of(new Position(1,4));
        tileWithDoor.setCollectable(Door.of(EntityColor.PURPLE));
        setTileAt(tileWithDoor);
        setTileAt(Free.of(new Position(2,4)));
        setTileAt(Free.of(new Position(3,4)));
        setTileAt(Info.of("Info", new Position(4,4)));
        setTileAt(Free.of(new Position(5,4)));
        setTileAt(Free.of(new Position(6,4)));
        Free tileWithDoor2 = Free.of(new Position(7,4));
        tileWithDoor2.setCollectable(Door.of(EntityColor.ORANGE));
        setTileAt(tileWithDoor2);
        setTileAt(Free.of(new Position(8,4)));

        setTileAt(Free.of(new Position(0,5)));
        setTileAt(Wall.of(new Position(1,5)));
        setTileAt(Free.of(new Position(2,5)));
        setTileAt(Free.of(new Position(3,5)));
        setTileAt(Free.of(new Position(4,5)));
        setTileAt(Free.of(new Position(5,5)));
        setTileAt(Free.of(new Position(6,5)));
        setTileAt(Wall.of(new Position(7,5)));
        setTileAt(Free.of(new Position(8,5)));

        setTileAt(Free.of(new Position(0,6)));
        setTileAt(Wall.of(new Position(1,6)));
        setTileAt(Wall.of(new Position(2,6)));
        setTileAt(Free.of(new Position(3,6)));
        setTileAt(Free.of(new Position(4,6)));
        setTileAt(Free.of(new Position(5,6)));
        setTileAt(Wall.of(new Position(6,6)));
        setTileAt(Wall.of(new Position(7,6)));
        setTileAt(Free.of(new Position(8,6)));

        setTileAt(Free.of(new Position(0,7)));
        setTileAt(Wall.of(new Position(1,7)));
        setTileAt(Free.of(new Position(2,7)));
        setTileAt(Free.of(new Position(3,7)));
        setTileAt(Free.of(new Position(4,7)));
        setTileAt(Free.of(new Position(5,7)));
        Free tileWithTreasure2 = Free.of(new Position(6,7));
        tileWithTreasure2.setCollectable(Treasure.of());
        setTileAt(tileWithTreasure2);
        setTileAt(Wall.of(new Position(7,7)));
        setTileAt(Free.of(new Position(8,7)));

        setTileAt(Free.of(new Position(0,8)));
        setTileAt(Wall.of(new Position(1,8)));
        setTileAt(Free.of(new Position(2,8)));
        setTileAt(Free.of(new Position(3,8)));
        setTileAt(Free.of(new Position(4,8)));
        setTileAt(Free.of(new Position(5,8)));
        setTileAt(Free.of(new Position(6,8)));
        setTileAt(Wall.of(new Position(7,8)));
        Free tileWithKey2 = Free.of(new Position(8,8));
        tileWithKey2.setCollectable(Key.of(EntityColor.PURPLE));
        setTileAt(tileWithKey2);

        setTileAt(Free.of(new Position(0,9)));
        setTileAt(Wall.of(new Position(1,9)));
        setTileAt(Free.of(new Position(2,9)));
        setTileAt(Wall.of(new Position(3,9)));
        Free tileWithExitLock = Free.of(new Position(4,9));
        tileWithExitLock.setCollectable(ExitLock.of());
        setTileAt(tileWithExitLock);
        setTileAt(Wall.of(new Position(5,9)));
        setTileAt(Free.of(new Position(6,9)));
        setTileAt(Wall.of(new Position(7,9)));
        setTileAt(Free.of(new Position(8,9)));
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
            targetTile.onEnter(player);

            assert player.getPos().equals(toMove) : "Player did not move to the correct position";
        }
        player.setDirection(direction); //update player direction regardless of move success
        assert player.getDirection() == direction : "Player direction not updated correctly";
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
