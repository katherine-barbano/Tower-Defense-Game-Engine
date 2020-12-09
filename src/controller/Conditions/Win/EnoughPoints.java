package controller.Conditions.Win;

import controller.Conditions.WinLossCondition;
import model.gameplay.MVCInteraction.API.GameControlAPI;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**
 * This represents the win condition where the user has gotten enough points
 * @author alex chao
 */
public class EnoughPoints extends WinLossCondition {

  @Override
  public boolean checkCondition(GameControlAPI game, GameStatusAPI gameStatus) {
    // TODO: 2020-11-18 make bigger
    return gameStatus.pointsGreaterThan(500);
  }
}
