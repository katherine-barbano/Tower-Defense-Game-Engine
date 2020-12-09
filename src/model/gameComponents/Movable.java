package model.gameComponents;

import java.util.Set;
import model.gameplay.gameplayResources.Position;

/***
 * This interface extends Removable as any game component that can be moved also needs to be able to
 * be removed It defines the behavior that any game component that would need to be move within the
 * the grid.
 * @author alex chao
 */
public interface Movable extends Removable {

  /**
   * Given the current position of the game component and the possible states, it returns the Position
   * object representing the next position of the Game Component
   * @param currentPosition
   * @param openStates
   * @return new Position
   */
  Position getNextPosition(Position currentPosition, Set<Position> openStates);
}
