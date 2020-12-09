package model.gameComponents.enemyGridComponents.enemies;

import controller.ConfigObjects.EnemyConfig;
import java.util.Set;
import model.gameComponents.Damager;
import model.gameComponents.GameComponent;
import model.gameComponents.Health;
import model.gameComponents.Movable;
import model.gameplay.MVCInteraction.API.GameStatusAPI;
import model.gameplay.gameplayResources.Position;

/**@author Priya Rathinavelu
 * The purpose of this class is to abstractly define the enemy objects within the game, specifically
 * the different zombies that can be created for each level. Given the way zombies are created in this
 * game, the way this enemy class works is to create different types of enemies that have distinct
 * actions. Aspects of the zombie like speed, damage, and health, can easily be changed and so are
 * not used to distinguish heavily between enemies. This class assumes that each zombie should be
 * different based on what
 * should happen as its special action. This is what makes each version of the enemy unique. This class
 * also assumes that each zombie follows the same movement pattern and dies based on its health being
 * lower than zero. This class depends heavily on the enemyConfig class within the controller in order
 * to properly create the correct zombie. The enemyConfig object being passed into the constructor (
 * explained below) is used to extract the necessary information for creating the general features
 * of the enemy. This abstract class also depends on the Health, Damager, and Movable interfaces.
 * This means that any new methods introduced in those interfaces will also need to be updated within
 * this class. An example of how to use this class can be found within our enemy subclasses.
 * Specifically, to use this abstract Enemy class, a new type of enemy subclass can be created. If the
 * enemy wants some special action to occur at a certain point, then it must implement the actionable
 * interface to override those methods to create some sort of action within the game. The useful
 * thing about this class is how compatiable it can be with the various interfaces.
 */
public abstract class Enemy implements Health, Damager, Movable {

  public static final int DENOMINATOR_WIDTH = 4;
  public static final int NUMERATOR_WIDTH = 3;

  private final int speed;
  private final int damage;
  private final int rate = 0;

  private final int animationSpeed;
  private final int startingHealth;
  private final String name;
  private final int enemyScoreValue;
  int currentXDirection = -1;
  int currentYDirection = 0;
  private int currentHealth;
  private boolean isAlive = true;
  private boolean hasPassedLine = false;
  private boolean isXDirection = true;
  private GameStatusAPI myGameStatus;

  //This constructor was meant to be deleted. It can be deleted now without causing
  //any issues because no other parts of the code use this constructor.
  public Enemy(EnemyConfig enemyConfiguration, int animationSpeed) {
    speed = enemyConfiguration.getSpeed();
    damage = enemyConfiguration.getDamage();
    enemyScoreValue = enemyConfiguration.getScore();
    setInitialHealth(enemyConfiguration.getHealth());
    startingHealth = currentHealth;
    name = enemyConfiguration.getName();
    this.animationSpeed = animationSpeed;
  }

  /*
  This is the main constructor used to create an enemy. It takes in an enemyConfig object and is
  able to extract all of the needed information from it, such as its score, health, and damage.
  There is also an animation speed passed in which is used for determining the general size of the
  zombie. Lastly, a GameStatusAPI interface is passed into this constructor so that the status,
  specifically the score, can be updated when a zombie dies.
   */
  public Enemy(EnemyConfig enemyConfiguration, int animationSpeed, GameStatusAPI myStatus) {
    speed = enemyConfiguration.getSpeed();
    damage = enemyConfiguration.getDamage();
    enemyScoreValue = enemyConfiguration.getScore();
    setInitialHealth(enemyConfiguration.getHealth());
    startingHealth = currentHealth;
    name = enemyConfiguration.getName();
    this.animationSpeed = animationSpeed;
    myGameStatus = myStatus;
  }


  /**
   * @see Health#getScore()
   */
  @Override
  public int getScore() {
    return enemyScoreValue;
  }

  protected int getAnimationSpeed() {
    return animationSpeed;
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   * @see Health#setInitialHealth(int)
   */
  @Override
  public void setInitialHealth(int health) {
    currentHealth = health;
  }

  /**
   *
   * @see Movable#getNextPosition(Position, Set)
   *
   * This method is more complicated because the general movement policy is based on the current
   * direction of the enemy as well. This was updated because of the grid path implementation of the
   * game, where zombies need to be able to move in all directions.
   */
  @Override
  public Position getNextPosition(Position currentPosition, Set<Position> openStates) {
    Position newPositionSameDirection = determineNewPosition(currentPosition, this.speed,
        currentXDirection,
        currentYDirection);
    if (newPositionSameDirection.getColumn() < 0) {
      this.hasPassedLine = true;
      return currentPosition;
    }
    if (checkOpenPosition(openStates, newPositionSameDirection)) {
      return newPositionSameDirection;
    } else if (checkJumpedPosition(openStates, newPositionSameDirection)) {
      return getJumpedPosition(newPositionSameDirection);
    } else if (checkOpenPosition(openStates, getTurningPosition(currentPosition, 0, 1))) {
      Position firstTurn = getTurningPosition(currentPosition, 0, 1);
      isXDirection = !isXDirection;
      return firstTurn;
    } else if (checkOpenPosition(openStates, getTurningPosition(currentPosition, 0, -1))) {
      Position newTurn = getTurningPosition(currentPosition, 0, -1);
      isXDirection = !isXDirection;
      return newTurn;
    }
    return currentPosition;
  }

  /**
   *This method checks whether a certain position is valid
   * @param openStates set of open states that the enemy can move to
   * @param newPositionSameDirection a potential Position that can be moved to
   * @return a boolean indicating whether that potential position is valid and within the open states
   */
  protected boolean checkOpenPosition(Set<Position> openStates, Position newPositionSameDirection) {
    for (Position possible : openStates) {
      if (possible.equals(newPositionSameDirection)) {
        return true;
      }
    }
    return false;
  }

  /**
   *This method checks if the "jumped" position based on a given position is valid
   * @param openStates set of open states the enemy can move to
   * @param newPositionSameDirection a position that can be moved to
   * @return whether the "jumped" position of the zombie is a valid position
   */
  protected boolean checkJumpedPosition(Set<Position> openStates,
      Position newPositionSameDirection) {
    Position newPositionJumped = getJumpedPosition(newPositionSameDirection);
    return checkOpenPosition(openStates, newPositionJumped);
  }

  /**
   *This method creates the "jumped" position (a position slightly moved over)
   * @param newPositionSameDirection the current Position of the enemy
   * @return an updated position that moves the enemy one enemy grid length over
   */
  protected Position getJumpedPosition(Position newPositionSameDirection) {
    return new Position(newPositionSameDirection.getRow() + 1,
        newPositionSameDirection.getColumn() + 1);
  }

  /**
   *This method gets the turning position of the enemy based on its current x and y direction
   * @param currentPosition The current position of the enemy
   * @param startingXDirection The current x direction of the enemy
   * @param startingYDirection The current y direction of the enemy
   * @return an updated position that "turns" the enemy's direction
   */
  protected Position getTurningPosition(Position currentPosition, int startingXDirection,
      int startingYDirection) {
    if (isXDirection) {
      Position newYDirection = determineNewPosition(currentPosition, this.speed, startingXDirection,
          startingYDirection);
      currentXDirection = startingXDirection;
      currentYDirection = startingYDirection;
      return newYDirection;
    }
    Position newXDirection = determineNewPosition(currentPosition, this.speed, startingYDirection,
        startingXDirection);
    currentXDirection = startingYDirection;
    currentYDirection = startingXDirection;
    return newXDirection;
  }

  /**
   * THIS METHOD WAS MEANT TO BE REMOVED
   */
  protected Position getFirstTurningPosition(Position currentPosition) {
    if (isXDirection) {
      Position newYDirection = determineNewPosition(currentPosition, this.speed, 0, 1);
      currentXDirection = 0;
      currentYDirection = 1;
      return newYDirection;
    }
    Position newXDirection = determineNewPosition(currentPosition, this.speed, 1, 0);
    currentXDirection = 1;
    currentYDirection = 0;
    return newXDirection;
  }

  /**
   * THIS METHOD WAS MEANT TO BE REMOVED
   */
  protected Position getSecondTurningPosition(Position currentPosition) {
    if (isXDirection) {
      Position newYDirection = determineNewPosition(currentPosition, this.speed, 0, -1);
      currentXDirection = 0;
      currentYDirection = -1;
      return newYDirection;
    }
    Position newXDirection = determineNewPosition(currentPosition, this.speed, -1, 0);
    currentXDirection = -1;
    currentYDirection = 0;
    return newXDirection;
  }

  /**This method updates the health of the enemy
   * @see Health#updateHealth(Damager)
   */
  @Override
  public void updateHealth(Damager projectile) {
    currentHealth -= projectile.getDamage();
    if (currentHealth < 0) {
      myGameStatus.updatePoints(enemyScoreValue);
      isAlive = false;
    }
  }

  /**
   * @see Health#getHealth() ()
   */
  @Override
  public int getHealth() {
    return currentHealth;
  }

  /**
   *@see Health#getPercentageHealth()
   */
  @Override
  public double getPercentageHealth() {
    return ((double) currentHealth / startingHealth);
  }
  @Override
  public String toString() {
    return "Enemy";
  }

  /**This method is able to create a new position based on the current position, speed, and direction
   * @param currentPosition Current position of the enemy
   * @param speed speed of the enemy - how many grid column lengths it should move each step
   * @param xDirection - current x direction of the enemy
   * @param yDirection - current y direction of the enemy
   * @return the moved position of the enemy based on direction and speed
   */
  protected Position determineNewPosition(Position currentPosition, int speed, int xDirection,
      int yDirection) {
    int newCol = currentPosition.getColumn() + xDirection * speed;
    int newRow = currentPosition.getRow() + yDirection * speed;
    Position movedPosition = new Position(newRow, newCol);
    return movedPosition;
  }

  public int getRate() {
    return rate;
  }

  /**
   * @see Health#isAlive()
   */
  @Override
  public boolean isAlive() {
    return isAlive;
  }

  public void setIsAlive(boolean newAliveState) {
    isAlive = newAliveState;
  }

  /**
   * @see Movable#isOutOfRange()
   */
  @Override
  public boolean isOutOfRange() {
    return getHasPassedLine();
  }

  protected void setOutOfRange(boolean value) {
    hasPassedLine = value;
  }

  /**
   * @see Damager#getDamage()
   */
  @Override
  public int getDamage() {
    return damage;
  }

  /**
   * @see GameComponent#getWidth()
   */
  public int getWidth() {
    return (getAnimationSpeed() / DENOMINATOR_WIDTH) * NUMERATOR_WIDTH;
  }

  /**
   * @see GameComponent#getHeight()
   * */
  public int getHeight() {
    return getAnimationSpeed();
  }

  protected int getSpeed() {
    return speed;
  }

  protected boolean getIsXDirection() {
    return isXDirection;
  }

  protected void setIsXDirection(boolean dir) {
    isXDirection = dir;
  }

  protected boolean getHasPassedLine() {
    return hasPassedLine;
  }

}