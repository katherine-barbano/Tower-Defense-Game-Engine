package controller.Conditions.Loss;

import controller.Conditions.WinLossCondition;
import model.gameplay.MVCInteraction.API.GameControlAPI;
import model.gameplay.MVCInteraction.API.GameStatusAPI;


/**
 * This represents the loss condition where the player has defeated all enemies, but has not generated
 * sufficient sun
 * @author alex chao
 */
public class NotEnoughSun extends WinLossCondition {

  @Override
  public boolean checkCondition(GameControlAPI game, GameStatusAPI gameStatus) {
    if (gameStatus.progressComplete() && game.noEnemiesExist()) {
      return gameStatus.sunLessThan(500);
    }
    return false;
  }
}
