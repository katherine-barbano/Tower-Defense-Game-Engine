package model.gameplay.collision;

import model.gameComponents.Actionable;
import model.gameComponents.Damager;
import model.gameComponents.GameComponent;
import model.gameComponents.Health;
import model.gameplay.gameplayResources.ModelException;
import model.gameplay.playableArea.PlayableArea;

/***
 * Describes the results of a collision between a Tower and an Enemy
 *
 * @author Katherine Barbano
 */
public class TowerEnemyCollision implements Collision {

  private final PlayableArea towerArea;
  private final PlayableArea enemyArea;
  private final GameComponent tower;
  private final GameComponent enemy;

  /***
   * Instantiated by CollisionFactory so that the first component is the Tower, and the
   * second component is the Enemy.
   * @param tower Tower
   * @param enemy Enemy
   * @param towerArea PlayableArea that Tower is contained in
   * @param enemyArea PlayableArea that Enemy is contained in
   */
  public TowerEnemyCollision(GameComponent tower, GameComponent enemy, PlayableArea towerArea,
      PlayableArea enemyArea) {
    this.towerArea = towerArea;
    this.enemyArea = enemyArea;
    this.tower = tower;
    this.enemy = enemy;
  }

  /***
   * Gradually drain the health of the tower while the enemy is passing over it
   * Remove the tower if it is no longer alive
   */
  @Override
  public void updateCollidedGameComponents() {
    try {
      if (enemy instanceof Actionable) {
        ((Actionable) enemy).setStepsBeforeNextAction(0);
      }
      ((Health) tower).updateHealth((Damager) enemy);
      if (!((Health) tower).isAlive()) {
        towerArea.removeGameComponent(tower);
      }
    } catch (ModelException e) {
      //do nothing. multiple enemies have collided with tower, and another collision has already removed the tower in the same step.
    }
  }
}
