package nz.ac.wgtn.swen225.lc.domain.tiles;

import nz.ac.wgtn.swen225.lc.domain.GameObserver;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Water class representing a water tile in the game
 * Water is impassable and causes the player to drown (to be implemented in level 2)
 * Inherits from Tile class
 * @author Hayley Far (300659141)
 */
public class Water extends Tile{
    private record WaterKey(Position pos){} // Record to represent unique key for water caching based on position
    private static final Map<WaterKey,Water> cache = new HashMap<>(); // Cache to store created water instances

    /**
     * Private constructor for water with specified WaterKey
     * @param key WaterKey containing position of the water tile
     */
    Water(WaterKey key) {
        super(key.pos());
    }

    /**
     * Static factory method to create a water tile with specified position (Flyweight pattern)
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
     * Player to drown in water (die), using observer pattern to notify observers of drowning
     * @param player player entering the water tile
     * @return Consumer to notify observers of player drowning
     */
    @Override
    public Consumer<GameObserver> onEnter(Player player) {
        if(player == null){
            throw new IllegalArgumentException("Player cannot be null");
        }
        player.die();
        return observer -> observer.onPlayerDrown(player);
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

    /**
     * Accept method for visitor pattern
     * @param visitor TileVisitor instance
     * @param <T> return type of the visitor's operation
     * @return result of visitor's visitWater method
     */
    @Override
    public <T> T accept(TileVisitor<T> visitor) {
        return visitor.visitWater(this);
    }
}
