package nz.ac.wgtn.swen225.lc.domain.tiles;

import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;

import java.util.HashMap;
import java.util.Map;

/**
 * Wall class representing a wall tile in the game
 * Wall is impassable and does not allow player movement
 * Inherits from Tile class
 * @author Hayley Far
 */
public class Wall extends Tile {
    private record WallKey(Position pos){}
    private static final Map<WallKey, Wall> cache = new HashMap<>();

    /**
     * Private constructor for wall with specified WallKey
     * @param key WallKey containing position of the wall tile
     */
    Wall(WallKey key) {
        super(key.pos());
    }

    /**
     * Static factory method to create or retrieve a wall tile with specified position
     * Uses caching to avoid duplicate wall instances at the same position
     * @param pos position of the wall tile
     * @return Wall instance at the specified position
     */
    public static Wall of(Position pos){
        assert pos != null : "Position cannot be null";
        var key = new WallKey(pos);
        return cache.computeIfAbsent(key, Wall::new);
    }

    /**
     * Wall is not accessible by the player
     * @return false always
     */
    @Override
    public boolean isAccessible(Player p){return false;}

    /**
     * Method to handle player entering the wall tile
     * Wall is impassable, so this method has no implementation
     * @param p player attempting to enter the wall tile
     */
    @Override
    public void onEnter(Player p){}

    /**
     * Override equals method to compare wall tiles based on position
     * @param obj object to compare with
     * @return true if wall tiles are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        Wall wall = (Wall) obj;
        return this.getPos().equals(wall.getPos());
    }
}
