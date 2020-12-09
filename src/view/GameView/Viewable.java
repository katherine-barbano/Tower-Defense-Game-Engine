package view.GameView;

import javafx.scene.Scene;

/**
 * The Viewable Interface defines the setupScene method.  It should be implemented by any
 * class that creates a view to be displayed on a stage.
 */
public interface Viewable {

  /**
   * Create the scene to be displayed in the main stage.
   * @param height Height of the scene
   * @param width Width of the scene
   * @return The scene to be displayed
   */
  Scene setupScene(int height, int width);
}
