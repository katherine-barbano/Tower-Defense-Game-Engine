package model.gameComponents;

import java.util.Map;
import model.gameplay.gameplayResources.Position;

/***
 * This interface extends GameComponent as all objects with the Shooter behavior are also game
 * components. It defines the behavior that any game component that would shoot would need.
 * @author alex chao
 */
public interface Shooter extends GameComponent {

  /**
   * Sets the new possible range for the object that is shooting. The object holds this range
   * internally.
   * @param range
   */
  void setRange(Map<Position, GameComponent> range);

  int getXDirection();

  int getYDirection();
}
