package nz.ac.wgtn.swen225.lc.domain.tiles;

import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;

import java.util.HashMap;
import java.util.Map;

/**
 * Water class representing a water tile in the game
 * Water is impassable and causes the player to drown (to be implemented in level 2)
 * Inherits from Tile class
 * @author Hayley Far
 */
public class Water extends Tile{
    private record WaterKey(Position pos){}
    private static final Map<WaterKey,Water> cache = new HashMap<>();

    /**
     * Private constructor for water with specified WaterKey
     * @param key WaterKey containing position of the water tile
     */
    Water(WaterKey key) {
        super(key.pos());
    }

    /**
     * Static factory method to create a water tile with specified position
     * @param pos position of the water tile
     * @return new Water instance
     */
    public static Water of(Position pos){
        if(pos == null){
            throw new IllegalArgumentException("Position cannot be null");
        }
        var key = new WaterKey(pos);
        return cache.computeIfAbsent(key, Water::new);
    }

    /**
     * Method to handle player entering the water tile
     * Currently unimplemented, to be defined for level 2 when player drowns
     * @param player player entering the water tile
     */
    @Override
    public void onEnter(Player player) {
        if(player == null){
            throw new IllegalArgumentException("Player cannot be null");
        }
        //to be implemented for level 2 when player drowns
        throw new UnsupportedOperationException();
    }

    /**
     * Override equals method to compare water tiles based on position
     * @param obj object to compare with
     * @return true if water tiles are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        if(!(obj instanceof Water other)) return false;
        return this.getPos().equals(other.getPos());
    }
}
