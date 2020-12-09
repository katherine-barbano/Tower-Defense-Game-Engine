package model.gameComponents.enemyGridComponents.enemies;

import controller.ConfigObjects.EnemyConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import model.gameComponents.Actionable;
import model.gameComponents.Fire;
import model.gameComponents.GameComponent;
import model.gameComponents.Movable;
import model.gameplay.MVCInteraction.API.GameStatusAPI;
import model.gameplay.gameplayResources.Position;

/**@author Priya Rathinavelu
 * This class extends the abstract class and is used to create a single use version of the enemy.
 * It depends on the enemy abstract class and the enemyConfig object which is
 * passed into the super constructor to create the enemy. Most notably, this class also depends on
 * Actionable interface. There will need to be methods
 * overridden from the actionable interface. This is what makes this zombie unique and able to have some
 * sort of action. For this, the enactAction method was written so that nearby states are turned into
 * fire states. For checking the action, this is really determined by the grid, rather than the
 * own features of the enemy itself. After this "explosion" occurs, the enemy is removed from the grid.
 *
 */
public class SingleUseEnemy extends Enemy implements Actionable {

  private final int gridRows;
  private final int gridCols;
  private final int animationSpeed;
  private boolean readyToExplode;
  private boolean exploded;

  /**
   *
   * @param enemyConfiguration enemyConfig object indicating general features about the enemy
   * @param gridRows number of rows in the grid
   * @param gridCols number of columns in the grid
   * @param animationSpeed speed of animation needed for determining size of enemy
   * @param status GameStatusAPI used for updating the status as the enemy updates
   */
  public SingleUseEnemy(EnemyConfig enemyConfiguration, int gridRows, int gridCols,
      int animationSpeed, GameStatusAPI status) {
    super(enemyConfiguration, animationSpeed, status);
    // stepsBeforeNextAction = enemyConfiguration.getRate();
    readyToExplode = false;
    this.gridRows = gridRows;
    this.gridCols = gridCols;
    this.animationSpeed = animationSpeed;
  }


  /**
   * @see Actionable#checkAction() 
   */
  @Override
  public boolean checkAction() {
    return readyToExplode;
  }

  /**
   * @see Actionable#setStepsBeforeNextAction(int) 
  Automatically sets this function to true because the time of explosion is not determined by the
  enemy itself
   */
  @Override
  public void setStepsBeforeNextAction(int rate) {
    readyToExplode = true;
  }

  /**This method is used for actually "exploding the enemy so that nearby states are turned into
   * fire states - the unique action of this enemy
   * @see Actionable#enactAction(Position, GameStatusAPI) 
   */
  @Override
  public Map<Position, GameComponent> enactAction(Position currentPosition,
      GameStatusAPI gameStatus) {
    Map<Position, GameComponent> newSurroundingStates = new HashMap<>();
    int currentRow = currentPosition.getRow();
    int currentCol = currentPosition.getColumn();
    for (int newRow = currentRow - getWidth(); newRow <= currentRow + getWidth();
        newRow = newRow + getWidth()) {
      for (int newCol = currentCol - getWidth(); newCol <= currentCol + getWidth();
          newCol = newCol + getWidth()) {
        if (!(newRow == currentRow && newCol == currentCol) && fireInRange(newRow, newCol)) {
          Position newPosition = new Position(newRow, newCol);
          newSurroundingStates.put(newPosition, new Fire(getAnimationSpeed(), this, Enemy.class));
        }
      }
    }
    exploded = true;
    return newSurroundingStates;
  }

  /**
   *This method is used to get the next position of this enemy but also looks at whether the enemy
   * needs to explode while checking its movement
   * @see Movable#getNextPosition(Position, Set) 
   * */
  @Override
  public Position getNextPosition(Position currentPosition, Set<Position> openStates) {
    if (!readyToExplode) {
      Position newPositionSameDirection = determineNewPosition(currentPosition, getSpeed(),
          currentXDirection,
          currentYDirection);
      if (newPositionSameDirection.getColumn() < 0) {
        setOutOfRange(true);
        return currentPosition;
      }
      if (checkOpenPosition(openStates, newPositionSameDirection)) {
        return newPositionSameDirection;
      } else if (checkJumpedPosition(openStates, newPositionSameDirection)) {
        return getJumpedPosition(newPositionSameDirection);
      } else if (checkOpenPosition(openStates, getTurningPosition(currentPosition, 0, 1))) {
        Position firstTurn = getTurningPosition(currentPosition, 0, 1);
        setIsXDirection(!getIsXDirection());
        return firstTurn;
      } else if (checkOpenPosition(openStates, getTurningPosition(currentPosition, 0, -1))) {
        Position newTurn = getTurningPosition(currentPosition, 0, -1);
        setIsXDirection(!getIsXDirection());
        return newTurn;
      }
    }
    return currentPosition;
  }

  /**
   *This method checks whether the fire is within range
   * @param newRow a new potential row number
   * @param newCol a new potential column number
   * @return a boolean indicating whether the new row and column are valid and within bounds
   */
  private boolean fireInRange(int newRow, int newCol) {
    return newRow >= 0 && newCol >= 0 && (newRow + getHeight()) <= gridRows
        && (newCol + getWidth()) <= gridCols;
  }

  /**
   * @see Movable#isOutOfRange() 
   */
  @Override
  public boolean isOutOfRange() {
    return getHasPassedLine() || exploded;
  }

}
