package model.gameComponents.playerGridComponents;

import controller.ConfigObjects.TowerConfig;
import java.util.HashMap;
import java.util.Map;
import model.factory.ProjectileFactory;
import model.gameComponents.GameComponent;
import model.gameComponents.Shooter;
import model.gameplay.MVCInteraction.API.GameStatusAPI;
import model.gameplay.gameplayResources.Position;

/**
 * The shoot tower is a type of tower that shoots projectiles
 *
 * @author alex chao
 */
public class ShootTower extends Tower implements Shooter {

  private final ProjectileFactory myProjectileFactory;
  private final String myProjectileType;
  private final int myXDirection;
  private final int myYDirection;
  private final int stepsBeforeNextAction;
  private Map<Position, GameComponent> possibleRange;
  private int stepsSinceLastAction = 0;

  public ShootTower(TowerConfig towerConfig, int numberOfRows, int numberOfColumns,
      int animationSpeed) {
    super(towerConfig, animationSpeed);
    int myDamage = towerConfig.getDamage();
    stepsBeforeNextAction = towerConfig.getRate();
    myXDirection = towerConfig.getXDirection();
    myYDirection = towerConfig.getYDirection();
    int projectileSpeed = towerConfig.getProjectileSpeed();
    myProjectileType = towerConfig.getProjectileType();
    myProjectileFactory = new ProjectileFactory(projectileSpeed, myDamage, numberOfRows,
        numberOfColumns, myXDirection,
        myYDirection, animationSpeed, Tower.class);
  }

  /**
   * This creates a projectile from the current position of the tower that is passed in, if there
   * is currently an enemy in its range
   * @param currentPosition
   * @param gameStatus
   * @return
   */
  @Override
  public Map<Position, GameComponent> enactAction(Position currentPosition,
      GameStatusAPI gameStatus) {
    Map<Position, GameComponent> newComponents = new HashMap<>();
    Position nextPosition = new Position(currentPosition.getRow() + myYDirection,
        currentPosition.getColumn() + myXDirection);
    newComponents.put(nextPosition, myProjectileFactory.createProjectile(myProjectileType));
    return newComponents;
  }

  private boolean getShootingActive() {
    if (possibleRange != null) {
      for (Position currentPosition : possibleRange.keySet()) {
        if (possibleRange.get(currentPosition).toString().equals("Enemy")) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean checkAction() {
    if ((stepsSinceLastAction >= stepsBeforeNextAction) && getShootingActive()) {
      this.stepsSinceLastAction = 0;
      return true;
    } else {
      this.stepsSinceLastAction++;
      return false;
    }
  }


  @Override
  public void setRange(Map<Position, GameComponent> range) {
    possibleRange = range;
  }

  @Override
  public int getXDirection() {
    return myXDirection;
  }

  @Override
  public int getYDirection() {
    return myYDirection;
  }
}
