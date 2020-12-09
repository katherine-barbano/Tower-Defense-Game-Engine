package model.gameplay.collision;

import model.gameComponents.GameComponent;
import model.gameComponents.enemyGridComponents.projectiles.Projectile;
import model.gameplay.gameplayResources.ModelException;
import model.gameplay.playableArea.PlayableArea;

/***
 * Describes the results of a collision between two projectiles
 *
 * @author Katherine Barbano
 */
public class ProjectileProjectileCollision implements Collision {

  private final PlayableArea projectile1Area;
  private final PlayableArea projectile2Area;
  private final GameComponent projectile1;
  private final GameComponent projectile2;

  /***
   * Instantiated by CollisionFactory
   * @param projectile1 projectile
   * @param projectile2 projectile
   * @param projectile1Area PlayableArea the projectile1 is in
   * @param projectile2Area PlayableArea the projectile2 is in
   */
  public ProjectileProjectileCollision(GameComponent projectile1, GameComponent projectile2,
      PlayableArea projectile1Area, PlayableArea projectile2Area) {
    this.projectile1Area = projectile1Area;
    this.projectile2Area = projectile2Area;
    this.projectile1 = projectile1;
    this.projectile2 = projectile2;
  }

  /***
   * Removes both projectiles if they are not shot from the same type of shooter.
   * For example, if tower and enemy projectiles collide, they cancel each other out
   * and are both removed
   */
  @Override
  public void updateCollidedGameComponents() {
    try {
      if (((Projectile) projectile1).getShooterType() != ((Projectile) projectile2)
          .getShooterType()) {
        projectile1Area.removeGameComponent(projectile1);
        projectile2Area.removeGameComponent(projectile2);
      }
    } catch (ModelException e) {
      removeProjectile2First();
    }
  }

  private void removeProjectile2First() {
    try {
      if (((Projectile) projectile1).getShooterType() != ((Projectile) projectile2)
          .getShooterType()) {
        projectile2Area.removeGameComponent(projectile2);
        projectile1Area.removeGameComponent(projectile1);
      }
    } catch (ModelException e) {
      //do nothing. more than two projectiles have collided, and another collision has already removed one of the projectiles in the same step.
    }
  }
}
