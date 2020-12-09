package model.gameComponents;

/***
 * Enum of the different states in playable area that have no functionality other than
 * just occupying a spot in the grid.
 *
 * @author Katherine Barbano
 */
public enum SingleState implements GameComponent {
  EMPTY(),
  BLOCKED(),
  PATH();

  /***
   * Name of state
   * @return String
   */
  @Override
  public String getName() {
    return toString();
  }

  /***
   * Width of state
   * @return int
   */
  @Override
  public int getWidth() {
    return 1;
  }

  /***
   * Height of state
   * @return int
   */
  @Override
  public int getHeight() {
    return 1;
  }
}