package model.gameComponents;

/***
 * This interface extends GameComponent as all objects with the Removable behavior are also game
 * components. It defines the behavior that any game component that would need to be removed from
 * the grid.
 * @author alex chao
 */

public interface Removable extends GameComponent {

  /**
   * Checks to see if a game component is out of the grid
   * @return true if game component is out of range and false otherwise
   */
  boolean isOutOfRange();
}
