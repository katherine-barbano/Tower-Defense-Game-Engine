package view.Splashscreen;

import javafx.scene.control.Button;
import view.GameView.Viewable;

/**
 * The SetupScreenAPI allows other modules to access theGameSelection, Start button, player Name,
 * and the Scene from the SetupScreen.
 * @author Heather Grune (hlg20)
 */
public interface SetupScreenAPI extends Viewable {

  /**
   * Accessor for the game that the user has selected (returns the name of the custom game if the user
   * chooses custom.
   * @return the game the user has selected
   */
  String getGameSelection();

  /**
   * Accessor for the button that is used to start the game based on the game selection.
   * @return the Start Button
   */
  Button getStartButton();

  /**
   * Accessor for the name that the user enter's into the player name text field on the SetupScreen.
   * @return the player's name
   */
  String getPlayerName();

}
