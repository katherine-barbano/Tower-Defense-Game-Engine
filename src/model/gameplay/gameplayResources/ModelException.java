package model.gameplay.gameplayResources;

/***
 * Throw this class that extends runtime exception when there is an exception anywhere in model.
 *
 * @author Katherine Barbano
 */
public class ModelException extends RuntimeException {

  /***
   * Create new ModelException constructor
   * @param message Message to use
   */
  public ModelException(String message) {
    super(message);
  }

  /***
   * Create new ModelException constructor
   * @param message Message to use
   * @param cause Throwable
   */
  public ModelException(String message, Throwable cause) {
    super(message, cause);
  }
}
