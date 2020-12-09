package model.gameplay.MVCInteraction.concreteModel;

import java.util.List;
import model.factory.PlayableAreaFactory;
import model.gameComponents.SingleState;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/***
 * Functionality for the plants vs. zombies-like Grid implementation of the board, where
 * towers can be placed anywhere and enemies can move in any row, rather than
 * a more traditional tower defense setup where enemies walk along a path.
 *
 * @author Katherine Barbano
 */
public class GridGameControl extends GameControl {

  private final List<List<SingleState>> initialPlayerBoard;
  private final PlayableAreaFactory playableAreaFactory;
  private final int numRows;
  private final int numCols;
  private final int enemyAreaColumnsPerPlayerAreaColumn;

  /***
   * Calls superclass constructor, and sets values giving info about the player area and enemy area
   * grids in the grid implementation specifically.
   *
   * Creates a PlayableAreaFactory to create the grid-specific implementation of enemyArea and playerArea.
   * @param initialPlayerBoard Read in from controller
   * @param numberOfRows int
   * @param numberOfColumns int
   * @param gameStatus GameStatusAPI
   */
  public GridGameControl(List<List<SingleState>> initialPlayerBoard, int numberOfRows,
      int numberOfColumns, GameStatusAPI gameStatus) {
    super(numberOfRows, numberOfColumns, gameStatus);
    numRows = numberOfRows;
    numCols = numberOfColumns;
    this.initialPlayerBoard = initialPlayerBoard;

    //this magic constant should be refactored out into properties file
    enemyAreaColumnsPerPlayerAreaColumn = 20;

    playableAreaFactory = new PlayableAreaFactory();
    setNewPlayableArea(gameStatus.getAnimationSpeed());
    updateSetOfActiveCollisions();
  }

  /***
   * For this grid implementation, sets the player area to have BLOCKED states only in places
   * specified as BLOCKED in the player grid read in from controller. Enemy area is completely filled
   * with EMPTY states.
   * @param animationSpeed ratio of enemyArea units to a single playerArea unit
   */
  @Override
  protected void setNewPlayableArea(int animationSpeed) {
    setEnemyArea(playableAreaFactory

        .createEnemyPlayableAreaOverlapNoPath(numRows, numCols,
            enemyAreaColumnsPerPlayerAreaColumn));

    setPlayerArea(playableAreaFactory
        .createPlayerPlayableArea(initialPlayerBoard, numRows, numCols));
  }
}