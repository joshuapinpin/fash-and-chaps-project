package nz.ac.wgtn.swen225.lc.domain.entities;

/**
 * EntityVisitor interface for implementing the Visitor design pattern
 * Allows operations to be performed on different types of entities without modifying their classes
 * @param <T> the return type of the visit methods
 * @author Hayley Far (300659141)
 */
public interface EntityVisitor<T> {
  /**
   * Visit a key entity
   * @param key the key entity to visit
   * @return result of the visit operation
   */
  T visitKey(Key key);

  /**
   * Visit a door entity
   * @param door the door entity to visit
   * @return result of the visit operation
   */
  T visitDoor(Door door);

    /**
     * Visit an exit lock entity
     * @param exitLock the exit lock entity to visit
     * @return result of the visit operation
     */
  T visitExitLock(ExitLock exitLock);

    /**
     * Visit a treasure entity
     * @param treasure the treasure entity to visit
     * @return result of the visit operation
     */
  T visitTreasure(Treasure treasure);
}
