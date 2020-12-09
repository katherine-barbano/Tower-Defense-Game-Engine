package controller.Conditions.Win;

import controller.Conditions.WinLossCondition;
import model.gameplay.MVCInteraction.API.GameControlAPI;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**
 * This represents the win condition where the user has gotten enough sun
 * @author alex chao
 */
public class EnoughSun extends WinLossCondition {

  @Override
  public boolean checkCondition(GameControlAPI game, GameStatusAPI gameStatus) {
    // TODO: 2020-11-17 read in
    return !gameStatus.sunLessThan(500);
  }
}
