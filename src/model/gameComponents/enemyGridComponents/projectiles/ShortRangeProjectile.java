package model.gameComponents.enemyGridComponents.projectiles;

import model.gameplay.gameplayResources.Position;

/**@author PriyaRathinavelu
 * This is the short range projectile that extends the Projectile abstract class. It is used as a
 * projectile that is limited in its range and cannot travel the full distance of the game grid.
 * Instead, it can only travel a few steps infront of it. This class depends on the projectile
 * abstract class and is also unique because it overrides the checkIfRemove method. Now, this
 * method checks if a certain number of steps have occurred and if so, the projectile is removed.
 * (Number of steps not determined from properties file - explained more in analysis). This type
 * of projectile can be used when a projectile needs to be created (such as through the projectile
 * factory). The type "ShortRange" can be listed under projectileType in the properties file.
 */
public class ShortRangeProjectile extends Projectile {

  public ShortRangeProjectile(int projectileSpeed, int projectileDamage, int numRows, int numCols,
      int xDir, int yDir, int animationSpeed, Class componentShotFrom) {
    super(projectileSpeed, projectileDamage, numRows, numCols, xDir, yDir, animationSpeed,
        componentShotFrom);
  }

  /**
   *This overridded method is used for determining whether the projectile should be removed from the
   * grid
   * @param movedPosition moved position of the projectile
   * @param gridRows number of grid rows
   * @param gridCols number of grid columns
   * @param numberSteps number of steps that have passed
   * @return whether the projectile needs to be removed if it has moved out of bounds OR if a
   * certain number of steps have been reached (this limits its range)
   */
  @Override
  public boolean checkIfRemove(Position movedPosition, int gridRows, int gridCols,
      int numberSteps) {
    return (movedPosition.getColumn() + getHeight() >= getNumberCols() - 1
        || movedPosition.getRow() + getWidth() >= getNumberRows() - 1 ||
        movedPosition.getColumn() < 0 || movedPosition.getRow() < 0 || numberSteps > 15);
  }

}
