package controller.Conditions.Loss;

import controller.Conditions.WinLossCondition;
import model.gameplay.MVCInteraction.API.GameControlAPI;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**
 * This represents the loss condition where the player has run out of time
 * @author alex chao
 */
public class NoTime extends WinLossCondition {

  @Override
  public boolean checkCondition(GameControlAPI game, GameStatusAPI gameStatus) {
    // TODO: 11/18/2020 read in from file?
    return (gameStatus.getTimeElapsed() > 90);
  }
}
