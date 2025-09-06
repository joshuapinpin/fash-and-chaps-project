package nz.ac.wgtn.swen225.lc.domain;

/**
 * Implementing the Strategy Pattern for the different Tile actions
 * Having the concrete strategies (eg. free, key, door)
 *
 * @author Hayley Far (Student ID: 300659141)
 */
public interface TileAction {
    void action(Entity player);
}
