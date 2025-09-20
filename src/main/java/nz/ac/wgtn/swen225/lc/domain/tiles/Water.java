package nz.ac.wgtn.swen225.lc.domain.tiles;

import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;

import java.util.HashMap;
import java.util.Map;

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
        assert pos != null : "Position cannot be null";
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
        //to be implemented for level 2 when player drowns
        throw new UnsupportedOperationException();
    }
}
