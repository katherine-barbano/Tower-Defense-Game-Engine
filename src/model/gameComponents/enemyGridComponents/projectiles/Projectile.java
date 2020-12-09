package model.gameComponents.enemyGridComponents.projectiles;

import java.util.Set;
import model.gameComponents.Damager;
import model.gameComponents.GameComponent;
import model.gameComponents.Movable;
import model.gameplay.gameplayResources.Position;

/**
 * @author Priya Rathinavelu
 * The purpose of the class is to create a projectile object that is used by enemies and towers
 * for shooting. It assumes that a direction is given within the projectile (there is not default
 * value). This class depends on the Movable and Damager interface. The movable interface is needed
 *  so that the projectile's position can be updated and move within the game. The damager interface
 *   is used because the projectile inflicts damage onto whatever it interacts with. So,
 *   it will need to override methods related to getting the next position of the projectile. This
 *   class can be used by creating an instance of projectile and updating its position. In our code,
 *   the ShootEnemy and ShootTower create a projectile factory which creates instances of the
 *   projectile.
 */
public abstract class Projectile implements Damager, Movable {


  private final int speed;
  private final Class<?> shooterComponent;
  private final int damage;
  private final int numberCols;
  private final int numberRows;
  private final int xDirection;
  private final int yDirection;
  private final int animationSpeed;
  private boolean shouldRemove;
  private int numberSteps;


  /**
   *
   * @param projectileSpeed speed of projectile (how many grid columns should be passed each step)
   * @param projectileDamage amount of damage the projectile should give
   * @param numRows number of grid rows
   * @param numCols number of grid columns
   * @param xDir x direction of the projectile
   * @param yDir y direction of the projectile
   * @param animationSpeed speed of animation used for determining projectile size
   * @param componentShotFrom Class object needed for determining which class shot the projectile
   */
  public Projectile(int projectileSpeed, int projectileDamage, int numRows, int numCols, int xDir,
      int yDir, int animationSpeed, Class<?> componentShotFrom) {
    speed = projectileSpeed;
    damage = projectileDamage;
    numberCols = numCols;
    numberRows = numRows;
    xDirection = xDir;
    yDirection = yDir;
    this.animationSpeed = animationSpeed;
    shooterComponent = componentShotFrom;
  }


  /**
   *
   * @see Damager#getDamage()
   */
  @Override
  public int getDamage() {
    return damage;
  }

  /**
   * @see GameComponent#getName()
   * */
  @Override
  public String getName() {
    return "Projectile";
  }

  public int getSpeed() {
    return speed;
  }

  /**
   * @see GameComponent#getWidth()
   */
  @Override
  public int getWidth() {
    return getAnimationSpeed() / 2;
  }

  /**
   * @see GameComponent#getHeight()
   */
  @Override
  public int getHeight() {
    return getAnimationSpeed() / 2;
  }

  public int getAnimationSpeed() {
    return animationSpeed;
  }

  public int getXDirection() {
    return xDirection;
  }


  public int getYDirection() {
    return yDirection;
  }

  /**
   *
   * @see GameComponent#toString()
   */
  @Override
  public String toString() {
    return "Projectile";
  }

  public int getNumberCols() {
    return numberCols;
  }

  public int getNumberRows() {
    return numberRows;
  }

  public Class<?> getShooterType() {
    return shooterComponent;
  }

  /**
   *
   * @param currentPosition current Position of projectile
   * @param speed speed of the projectile
   * @param xDirection x direction of the projectile
   * @param yDirection y direction of the projectile
   * @return an updated position of the projectile based on the speed and direction
   */
  protected Position determineNextPosition(Position currentPosition, int speed, int xDirection,
      int yDirection) {
    int newCol = currentPosition.getColumn() + xDirection * speed;
    int newRow = currentPosition.getRow() + yDirection * speed;
    return new Position(newRow, newCol);
  }


  /**
   *This method gets the next position of the projectile by looking at its current position and
   * can also check whether the next position would make the projectile go out of bounds
   * @see Movable#getNextPosition(Position, Set)
   */
  @Override
  public Position getNextPosition(Position currentPosition, Set<Position> openStates) {
    Position movedPosition = determineNextPosition(currentPosition, getSpeed(), getXDirection(),
        getYDirection());
    numberSteps++;
    if (checkIfRemove(movedPosition, numberRows, numberCols, numberSteps)) {
      shouldRemove = true;
      return currentPosition;
    }
    for (Position openPosition : openStates) {
      if (movedPosition.equals(openPosition)) {
        return movedPosition;
      }
    }
    if (checkJumpedPosition(openStates, movedPosition)) {
      return getJumpedPosition(movedPosition);
    }
    shouldRemove = true;
    return currentPosition;
  }

  /**
   *This method checks whether the "jumped" position is valid
   * @param openStates set of possible open states the projectile can move to
   * @param newPositionSameDirection position to be tested
   * @return if the jumped position is valid and within open states
   */
  protected boolean checkJumpedPosition(Set<Position> openStates,
      Position newPositionSameDirection) {
    Position newPositionJumped = getJumpedPosition(newPositionSameDirection);
    for (Position possible : openStates) {
      if (possible.equals(newPositionJumped)) {
        return true;
      }
    }
    return false;
  }

  /**
   *This method creates the "jumped" position (position slightly moved over)
   * @param newPositionSameDirection current Position
   * @return a new Position slightly moved over
   */
  protected Position getJumpedPosition(Position newPositionSameDirection) {
    return new Position(newPositionSameDirection.getRow() + 1,
        newPositionSameDirection.getColumn() + 1);
  }

  public int getNumberSteps() {
    return numberSteps;
  }

  /**
   *This method checks whether the projectile needs to be removed (used by grid)
   * @param movedPosition moved position of the projectile
   * @param gridRows number of grid rows
   * @param gridCols number of grid columns
   * @param numberSteps number of steps that have passed
   * @return boolean of whether the new position is out of bounds and needs to be removed
   */
  public boolean checkIfRemove(Position movedPosition, int gridRows, int gridCols,
      int numberSteps) {
    return (movedPosition.getColumn() + getHeight() >= getNumberCols() - 1
        || movedPosition.getRow() + getWidth() >= getNumberRows() - 1 ||
        movedPosition.getColumn() < 0 || movedPosition.getRow() < 0);
  }

  /**
   * @see Movable#isOutOfRange()
   */
  @Override
  public boolean isOutOfRange() {
    return shouldRemove;
  }

}