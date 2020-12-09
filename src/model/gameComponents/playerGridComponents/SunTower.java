package model.gameComponents.playerGridComponents;

import controller.ConfigObjects.TowerConfig;
import java.util.HashMap;
import java.util.Map;
import model.gameComponents.GameComponent;
import model.gameplay.MVCInteraction.API.GameStatusAPI;
import model.gameplay.gameplayResources.Position;

/**
 * The sun tower is a tower that generates sun
 *
 * @author alex chao
 */
public class SunTower extends Tower {

  private final int mySun;

  public SunTower(TowerConfig towerConfig, int numberOfRows, int numberOfColumns,
      int animationSpeed) {
    super(towerConfig, animationSpeed);
    mySun = towerConfig.getSun();
  }

  /**
   * This method updates the amount of sun in the GameStatus by the amount of sun that was read
   * in from the properties file
   * @param currentPosition
   * @param gameStatus
   * @return
   */
  @Override
  public Map<Position, GameComponent> enactAction(Position currentPosition,
      GameStatusAPI gameStatus) {
    Map<Position, GameComponent> newComponents = new HashMap<>();
    gameStatus.changeSunAmount(mySun);
    return newComponents;
  }
}
