package model.gameComponents.enemyGridComponents.projectiles;

/**
 * @author Priya Rathinavelu
 * This class is another type of basic projectile that extends from the abstract Projectile class.
 * Its purpose is to serve as a normal type of projectile that can move the full length of the
 * grid. It depends only on the projectile abstract class and does not make any changes to it
 * (nothing is being overrided here). This projectile can be created by indicating in the properties
 * file for a shoot enemy or tower. By writing "Single" for "ProjectileType", this type of
 * projectile will be created.
 */
public class SingleProjectile extends Projectile {


  public SingleProjectile(int projectileSpeed, int projectileDamage, int numRows, int numCols,
      int xDirectionSpeed, int yDirectionSpeed, int animationSpeed, Class<?> shotFrom) {
    //Just passed in information, should be data object created from properties file
    super(projectileSpeed, projectileDamage, numRows, numCols,
        xDirectionSpeed, yDirectionSpeed, animationSpeed, shotFrom);
  }


}
