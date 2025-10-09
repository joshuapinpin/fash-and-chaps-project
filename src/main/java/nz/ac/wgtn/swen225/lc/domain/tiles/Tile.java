package nz.ac.wgtn.swen225.lc.domain.tiles;

import nz.ac.wgtn.swen225.lc.domain.GameObserver;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;

import java.util.function.Consumer;

/**
 * Abstract Tile class representing a tile in the game
 * Tiles can have different types and behaviors
 * Each tile has a position and accessibility status
 * @author Hayley Far (300659141)
 */
public abstract class Tile {
    private Position pos; //position of the tile in the maze

    /**
     * Constructor for tile with specified position
     * @param pos position of the tile
     */
    public Tile(Position pos) {
        if (pos == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        this.pos = pos;
    }

    /**
     * Getter for tile position
     * @return position of the tile
     */
    public Position getPos() {
        return pos;
    }

    /**
     * Check if the tile is accessible by the player
     * @return true if accessible, false otherwise
     */
    public boolean isAccessible(Player p) {
        return true;
    }

    /**
     * Abstract method to handle player entering the tile
     * To be implemented by subclasses for specific behaviors
     * @param player player entering the tile
     * @return Consumer that accepts a GameObserver to notify about the event
     */
    public abstract Consumer<GameObserver> onEnter(Player player);

    /**
     * Accept method for visitor pattern
     * @param visitor TileVisitor instance
     * @param <T> return type of the visitor operation
     * @return result of visitor's visit method
     */
    public abstract <T> T accept(TileVisitor<T> visitor);
}
