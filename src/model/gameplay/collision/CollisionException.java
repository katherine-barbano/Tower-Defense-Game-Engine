package model.gameplay.collision;

import model.gameplay.gameplayResources.ModelException;

/***
 * Throw this class that extends model exception when there is an exception related to collisions.
 * @author Katherine Barbano
 */
public class CollisionException extends ModelException {

  /***
   * Create new CollisionException constructor
   * @param message Message to use
   */
  public CollisionException(String message) {
    super(message);
  }

  /***
   * Create new CollisionException constructor
   * @param message Message to use
   * @param cause Throwable
   */
  public CollisionException(String message, Throwable cause) {
    super(message, cause);
  }
}
