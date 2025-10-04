package nz.ac.wgtn.swen225.lc.domain.tiles;

/**
 * TileVisitor interface for implementing the Visitor design pattern
 * Allows operations to be performed on different types of tiles without modifying their classes
 * @param <T> the return type of the visit methods
 * @author Hayley Far
 */
public interface TileVisitor<T> {
    T visitWall(Wall wall);
    T visitFree(Free free);
    T visitInfo(Info info);
    T visitWater(Water water);
    T visitExit(Exit exit);
}
