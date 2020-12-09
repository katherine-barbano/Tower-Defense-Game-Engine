package controller.Managers;

import javafx.util.Duration;
import model.gameplay.MVCInteraction.API.GameControlAPI;
import model.gameplay.MVCInteraction.API.GameStatusAPI;


/**
 * Defines the common behavior that a LevelManager would need in order to set up flexibility for
 * different types of games and levels
 *
 * @author Megan Richards
 */
public interface LevelManager {

  void update(Duration animationSpeed);

  GameControlAPI getGame();

  void reset();

  GameStatusAPI getGameStatus();
}
