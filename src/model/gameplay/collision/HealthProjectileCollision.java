package model.gameplay.collision;

import model.gameComponents.Damager;
import model.gameComponents.GameComponent;
import model.gameComponents.Health;
import model.gameComponents.enemyGridComponents.projectiles.Projectile;
import model.gameplay.gameplayResources.ModelException;
import model.gameplay.playableArea.PlayableArea;

/***
 * Describes the results of a collision between a Health component (ie an enemy or a tower) and
 * a Projectile
 *
 * @author Katherine Barbano
 */
public class HealthProjectileCollision implements Collision {

  private final PlayableArea healthArea;
  private final PlayableArea projectileArea;
  private final GameComponent healthComponent;
  private final GameComponent projectile;

  /***
   * Instantiated by CollisionFactory so that the first component is the Health, and the
   * second component is the Projectile
   * @param healthComponent Health
   * @param projectile Projectile
   * @param healthArea PlayableArea that Health is contained in
   * @param projectileArea PlayableArea that Projectile is contained in
   */
  public HealthProjectileCollision(GameComponent healthComponent, GameComponent projectile,
      PlayableArea healthArea, PlayableArea projectileArea) {
    this.healthComponent = healthComponent;
    this.projectile = projectile;
    this.healthArea = healthArea;
    this.projectileArea = projectileArea;
  }

  /***
   * If the projectile was shot from an enemy and hits a tower, the tower will take damage.
   * If the projectile was shot from a tower and hits an enemy, the enemy will take damage.
   * If a projectile shot from a tower hits a tower, nothing happens, and vice versa for enemies.
   * Removes the health component if it is no longer alive.
   * Always removes the projectile.
   */
  @Override
  public void updateCollidedGameComponents() {
    try {
      if (!((Projectile) projectile).getShooterType().isInstance(healthComponent)) {
        ((Health) healthComponent).updateHealth((Damager) projectile);
        if (!((Health) healthComponent).isAlive()) {
          healthArea.removeGameComponent(healthComponent);
        }
        projectileArea.removeGameComponent(projectile);
      }
    } catch (ModelException e) {
      //do nothing. multiple enemies have collided with projectile, and another collision has already removed the projectile in the same step.
    }
  }
}
