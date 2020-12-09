package controller.Conditions.Loss;

import controller.Conditions.WinLossCondition;
import model.gameplay.MVCInteraction.API.GameControlAPI;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**
 * This represents the loss condition where an enemy has crossed the line
 * @author alex chao
 */
public class CrossLine extends WinLossCondition {

  @Override
  public boolean checkCondition(GameControlAPI game, GameStatusAPI gameStatus) {
    return game.singleEnemyIsOutOfRange();
  }
}
