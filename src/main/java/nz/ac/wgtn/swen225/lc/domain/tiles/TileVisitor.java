package nz.ac.wgtn.swen225.lc.domain.tiles;

/**
 * TileVisitor interface for implementing the Visitor design pattern
 * Allows operations to be performed on different types of tiles without modifying their classes
 * @param <T> the return type of the visit methods
 * @author Hayley Far (300659141)
 */
public interface TileVisitor<T> {
    /**
     * Visit a wall tile
     * @param wall the wall tile to visit
     * @return result of the visit operation
     */
    T visitWall(Wall wall);

    /**
     * Visit a free tile
     * @param free the free tile to visit
     * @return result of the visit operation
     */
    T visitFree(Free free);

    /**
     * Visit an info tile
     * @param info the info tile to visit
     * @return result of the visit operation
     */
    T visitInfo(Info info);

    /**
     * Visit a water tile
     * @param water the water tile to visit
     * @return result of the visit operation
     */
    T visitWater(Water water);

    /**
     * Visit an exit tile
     * @param exit the exit tile to visit
     * @return result of the visit operation
     */
    T visitExit(Exit exit);
}
