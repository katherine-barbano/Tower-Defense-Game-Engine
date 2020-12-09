package view.Splashscreen;

import javafx.scene.control.Button;
import view.GameView.Viewable;

/**
 * API for the first splashscreen.  Implements the viewable interface, and allows other modules
 * to access the scene for the splashscreen, along with the user's language selection and the setup
 * Button.
 * @author Heather Grune (hlg20)
 */
public interface FirstSplashscreenAPI extends Viewable {

  /**
   * Accessor for the user's language selection
   * @return String representing the language selection
   */
  String getLanguageSelection();

  /**
   * Accessor for the button that triggers language setup
   * @return the setup Button
   */
  Button getSetUpButton();

}
