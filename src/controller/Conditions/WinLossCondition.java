package controller.Conditions;


import model.gameplay.MVCInteraction.API.GameControlAPI;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**
 * This class represents a win or loss condition that is dependent on the GameControl and GameStatus
 * APIs. It is used by the Level class to check to see if the level has been loss or won.
 */
public abstract class WinLossCondition {

  /**
   * Returns true if the condition is satisfied, and false otherwise
   * @param game
   * @param gameStatus
   * @return
   */
  public abstract boolean checkCondition(GameControlAPI game, GameStatusAPI gameStatus);
}
