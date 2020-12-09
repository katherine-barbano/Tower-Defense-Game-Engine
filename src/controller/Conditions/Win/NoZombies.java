package controller.Conditions.Win;

import controller.Conditions.WinLossCondition;
import model.gameplay.MVCInteraction.API.GameControlAPI;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**
 * This represents the win condition where the user has defeated all the enemies
 * @author alex chao
 */
public class NoZombies extends WinLossCondition {

  @Override
  public boolean checkCondition(GameControlAPI game, GameStatusAPI gameStatus) {
    if (gameStatus.progressComplete()) {
      return game.noEnemiesExist();
    }
    return false;
  }
}