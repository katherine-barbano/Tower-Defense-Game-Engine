package view.GameView;

/**
 * GameViewAPI extends the viewable Interface and defines all public methods that can be called on
 * the GameView by other modules.
 * @author Heather Grune (hlg20)
 */
public interface GameViewAPI extends Viewable {

  /**
   * Update the components in the GameViewAPI.
   */
  void step();


}
