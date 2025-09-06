package nz.ac.wgtn.swen225.lc.domain;

/**
 * Interface to represent the behaviour of a tile
 * Concrete implementations to define the specific actions for the type of tile when the player is on it
 * eg. collecting a key, unlocking door, blocked from wall
 *
 * @author Hayley Far (Student ID: 300659141)
 */
public interface TileAction {
    void action(Entity player);
}
