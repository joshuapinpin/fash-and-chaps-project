package nz.ac.wgtn.swen225.lc.domain.entities;

/**
 * EntityVisitor interface for implementing the Visitor design pattern
 * Allows operations to be performed on different types of entities without modifying their classes
 * @param <T> the return type of the visit methods
 * @author Hayley Far
 */
public interface EntityVisitor<T> {
  T visitKey(Key key);
  T visitDoor(Door door);
  T visitExitLock(ExitLock exitLock);
  T visitTreasure(Treasure treasure);
}
