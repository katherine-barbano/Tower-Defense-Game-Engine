package model.gameplay.MVCInteraction.concreteModel;

import java.util.List;
import model.factory.PlayableAreaFactory;
import model.gameComponents.SingleState;
import model.gameplay.MVCInteraction.API.GameStatusAPI;
import model.gameplay.collision.CollisionDetector;

/***
 * Functionality for a more traditional tower defense setup where enemies walk along a path
 * and towers cannot be placed on the path, rather than the plants vs. zombies-like Grid
 * implementation of the board.
 *
 * @author Katherine Barbano
 */
public class PathGameControl extends GameControl {

  //this magic constant should be refactored out into properties file
  private final int enemyGridUnitsPerPlayerGridUnit = 20;

  private final CollisionDetector collisionDetector;
  private final GameStatusAPI gameStatus;
  private final PlayableAreaFactory playableAreaFactory;
  private final List<List<SingleState>> initialPlayerBoard;
  private final int numberOfRows;
  private final int numberOfColumns;

  /***
   * Calls superclass constructor, and sets values giving info about the player area and enemy area
   * grids in the path implementation specifically.
   *
   * Creates a PlayableAreaFactory to create the path-specific implementation of enemyArea and playerArea.
   * @param initialPlayerBoard
   * @param numberOfRows
   * @param numberOfColumns
   * @param gameStatus
   */
  public PathGameControl(List<List<SingleState>> initialPlayerBoard, int numberOfRows,
      int numberOfColumns, GameStatusAPI gameStatus) {
    super(numberOfRows, numberOfColumns, gameStatus);
    this.gameStatus = gameStatus;
    this.numberOfRows = numberOfRows;
    this.numberOfColumns = numberOfColumns;
    this.initialPlayerBoard = initialPlayerBoard;
    playableAreaFactory = new PlayableAreaFactory();
    setNewPlayableArea(enemyGridUnitsPerPlayerGridUnit);
    collisionDetector = new CollisionDetector();
    updateSetOfActiveCollisions();
  }

  /***
   * For this path implementation, sets the player area to have BLOCKED states only in places
   * specified as BLOCKED in the player grid read in from controller. Enemy area is set to be full of
   * BLOCKED states, except for the corresponding positions for the path read in by the controller. These
   * positions are set to EMPTY in the enemyArea, so that enemies can move on the path only.
   * @param animationSpeed ratio of enemyArea units to a single playerArea unit
   */
  @Override
  protected void setNewPlayableArea(int animationSpeed) {
    setEnemyArea(playableAreaFactory
        .createEnemyPlayableAreaOverlapWithPath(initialPlayerBoard, numberOfRows, numberOfColumns,
            animationSpeed));
    setPlayerArea(playableAreaFactory
        .createPlayerPlayableArea(initialPlayerBoard, numberOfRows, numberOfColumns));
  }

}
