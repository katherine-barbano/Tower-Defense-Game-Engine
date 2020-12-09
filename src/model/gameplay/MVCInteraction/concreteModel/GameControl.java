package model.gameplay.MVCInteraction.concreteModel;

import controller.ConfigObjects.Config;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import model.factory.EnemyComponentFactory;
import model.factory.GameComponentFactory;
import model.gameComponents.Actionable;
import model.gameComponents.Fire;
import model.gameComponents.GameComponent;
import model.gameComponents.Health;
import model.gameComponents.Movable;
import model.gameComponents.Removable;
import model.gameComponents.Shooter;
import model.gameComponents.SingleState;
import model.gameComponents.enemyGridComponents.enemies.Enemy;
import model.gameComponents.enemyGridComponents.enemies.ShootEnemy;
import model.gameComponents.enemyGridComponents.projectiles.Projectile;
import model.gameComponents.playerGridComponents.Tower;
import model.gameplay.MVCInteraction.API.GameControlAPI;
import model.gameplay.MVCInteraction.API.GameDisplayAPI;
import model.gameplay.MVCInteraction.API.GameStatusAPI;
import model.gameplay.collision.Collision;
import model.gameplay.collision.CollisionDetector;
import model.gameplay.gameplayResources.Position;
import model.gameplay.playableArea.IIterator;
import model.gameplay.playableArea.PlayableArea;
import model.gameplay.playableArea.PlayableAreaData;

/***
 * Contains most functionality to implement the GameControlAPI. This class contains
 * two instances of PlayableArea: enemyArea, which stores components that need to animate
 * and interact primarily with enemy components; and playerArea, which stores towers or other
 * components that need to interact with the user input in the view. The number of rows/columns
 * in enemyArea is equal to the number of rows/columns in playerArea multiplied by a factor of 20,
 * so that these areas can be overlayed ontop of each other in the view. The tiny rows/columns in enemyArea
 * allows the movement functionality of Movable GameComponents to appear smooth and not jumpy when moving to
 * the next position on each step.
 *
 * This class maintains an active set of Collisions and detects them with a collisionDetector object. It also
 * instantiates and can return its own GameDisplayAPI.
 *
 * This class abstracts out how the enemy and player areas are initialized, so that
 * they can be initialized differently for the path and the grid implementations.
 *
 * @author Katherine Barbano
 */
public abstract class GameControl implements GameControlAPI {

  public static final String MODEL_RESOURCE_PATH = "resources/Model";
  private final GameStatusAPI gameStatus;
  private final GameDisplayAPI gameDisplay;
  private final int enemyRowScale = 20;
  private final CollisionDetector collisionDetector;
  private GameComponentFactory enemyFactory;
  private PlayableArea enemyArea;
  private PlayableArea playerArea;
  private Set<Collision> activeCollisions;

  /***
   * Constructor for GameControl. Initializes enemyFactory and collisionDetector to private var to be used multiple times throughout code.
   * GameStatusAPI should be passed in from Controller, since Controller needs to read in the initial sun amount.
   *
   * Initializes enemyArea and playerArea temporarily before being reset to its path/grid specific implementation
   * by calling reset, where the method to set up these areas is overrided in subclasses.
   * @param numberOfRows int
   * @param numberOfColumns int
   * @param gameStatus gameStatus with initial sun set
   */
  public GameControl(int numberOfRows, int numberOfColumns, GameStatusAPI gameStatus) {
    enemyArea = new PlayableAreaData(numberOfRows, numberOfColumns);
    playerArea = new PlayableAreaData(numberOfRows, numberOfColumns);
    this.gameStatus = gameStatus;
    this.gameDisplay = new GameDisplay(enemyArea, playerArea, enemyRowScale, gameStatus);
    enemyFactory = new EnemyComponentFactory(gameDisplay.getEnemyAreaNumberOfRows(),
        gameDisplay.getEnemyAreaNumberOfColumns(), enemyRowScale, gameStatus);
    collisionDetector = new CollisionDetector();
  }

  /***
   * @see GameControlAPI#getGameDisplayAPI()
   */
  @Override
  public GameDisplayAPI getGameDisplayAPI() {
    return gameDisplay;
  }

  /***
   * @see GameControlAPI#getEnemyAreaSpacesPerPlayerAreaSpace()
   */
  @Override
  public int getEnemyAreaSpacesPerPlayerAreaSpace() {
    return enemyRowScale;
  }

  protected GameComponentFactory<Enemy> getEnemyFactory() {
    return enemyFactory;
  }

  protected PlayableArea getEnemyArea() {
    return enemyArea;
  }

  protected void setEnemyArea(PlayableArea playableArea) {
    enemyArea = playableArea;
    ((GameDisplay) gameDisplay).setEnemyArea(enemyArea);
    enemyFactory = new EnemyComponentFactory(gameDisplay.getEnemyAreaNumberOfRows(),
        gameDisplay.getEnemyAreaNumberOfColumns(), gameStatus.getAnimationSpeed(), gameStatus);
  }

  protected PlayableArea getPlayerArea() {
    return playerArea;
  }

  protected void setPlayerArea(PlayableArea playableArea) {
    playerArea = playableArea;
    ((GameDisplay) gameDisplay).setPlayerArea(playerArea);
  }

  protected ResourceBundle getModelProperties() {
    return ResourceBundle.getBundle(MODEL_RESOURCE_PATH);
  }

  /***
   * @see GameControlAPI#noEnemiesExist()
   */
  @Override
  public boolean noEnemiesExist() {
    IIterator<Enemy> enemyIterator = enemyArea.createTypeIterator(Enemy.class);
    return !enemyIterator.hasNext();
  }

  /***
   * @see GameControlAPI#reset()
   */
  @Override
  public void reset() {
    setNewPlayableArea(enemyRowScale);
    gameStatus.reset();
  }

  /***
   * Sets the enemyArea and playerArea to have the initial states needed by the subclass implementation.
   * This includes having BLOCKED states where GameComponents should not be able to move, and EMPTY
   * states where GameComponents should be able to move.
   * @param animationSpeed ratio of enemyArea units to a single playerArea unit
   */
  protected abstract void setNewPlayableArea(int animationSpeed);

  @Override
  public void createWaveOfEnemies(List<Config> enemyWaveName) {
    List<GameComponent> enemies = new ArrayList<>();
    int verticalSpaceBetweenEnemies = getEnemyAreaSpacesPerPlayerAreaSpace();
    int lastColumn = gameDisplay.getEnemyAreaNumberOfColumns() - 1;
    for (int index = 0; index < enemyWaveName.size(); index++) {
      Config newCell = enemyWaveName.get(index);
      Position positionInColumn = new Position(verticalSpaceBetweenEnemies * index, lastColumn);
      if (newCell == null
          || getEnemyArea().getGameComponentAtPosition(positionInColumn) != SingleState.EMPTY) {
        enemies.add(SingleState.EMPTY);
      } else {
        enemies.add(
            getEnemyFactory().createGameComponent(newCell));
      }
      for (int inBetween = 0; inBetween < verticalSpaceBetweenEnemies - 1; inBetween++) {
        enemies.add(SingleState.EMPTY);
      }
    }
    getEnemyArea().setLastColumnOfGameComponents(enemies);
  }


  private void updateShooterRanges() {
    updateShooterRangesPlayerArea();
    updateShooterRangesEnemyArea();
  }

  private void updateShooterRangesEnemyArea() {
    IIterator<Shooter> shooterIterator = enemyArea.createTypeIterator(Shooter.class);
    while (shooterIterator.hasNext()) {
      Shooter next = shooterIterator.next();
      Position currentPosition = enemyArea.getPositionOfGameComponent(next);
      int newRow = currentPosition.getRow() / getEnemyAreaSpacesPerPlayerAreaSpace();
      int newColumn = currentPosition.getColumn() / getEnemyAreaSpacesPerPlayerAreaSpace();

      Position playerGridPosition = new Position(newRow, newColumn);
      Map<Position, GameComponent> range = getRangeFromDirection(playerArea, Health.class,
          playerGridPosition, next.getXDirection(), next.getYDirection());
      next.setRange(range);
    }
  }

  private void updateShooterRangesPlayerArea() {
    PlayableArea playerArea = getPlayerArea();
    IIterator<Shooter> shooterIterator = playerArea.createTypeIterator(Shooter.class);
    while (shooterIterator.hasNext()) {
      Shooter next = shooterIterator.next();
      Position currentPosition = playerArea.getPositionOfGameComponent(next);
      int newRow = currentPosition.getRow() * getEnemyAreaSpacesPerPlayerAreaSpace();
      int newColumn = currentPosition.getColumn() * getEnemyAreaSpacesPerPlayerAreaSpace();
      Position enemyGridPosition = new Position(newRow, newColumn);
      Map<Position, GameComponent> range = getRangeFromDirection(enemyArea, Movable.class,
          enemyGridPosition, next.getXDirection(), next.getYDirection());
      next.setRange(range);
    }
  }

  private Map<Position, GameComponent> getRangeFromDirection(PlayableArea area, Class target,
      Position position, int xDirection, int yDirection) {
    Map<Position, GameComponent> range = new HashMap<>();
    if (xDirection == 1) {
      range.putAll(area.getRowRangeToRight(position, target));
    } else if (xDirection == -1) {
      range.putAll(area.getRowRangeToLeft(target, position));
    } else if (yDirection == -1) {
      range.putAll(area.getColumnRangeUp(position, target));
    } else if (yDirection == 1) {
      range.putAll(area.getColumnRangeDown(position, target));
    }
    return range;
  }

  /***
   * @see GameControlAPI#moveComponents()
   */
  @Override
  public void moveComponents() {
    updateShooterRanges();
    placeNewComponentsFromComponents();
    removeOutOfRangeComponentsBothGrids();
    updateCollidedGameComponents();
    moveEnemyAreaGameComponents();
    updateSetOfActiveCollisions();
    gameStatus.update();
  }

  private void removeOutOfRangeComponentsBothGrids() {
    removeOutOfRangeComponents(enemyArea);
    removeOutOfRangeComponents(playerArea);
  }

  private void removeOutOfRangeComponents(PlayableArea area) {
    IIterator<Removable> removableIterator = area.createTypeIterator(Removable.class);
    while (removableIterator.hasNext()) {
      Removable next = removableIterator.next();
      if (next.isOutOfRange()) {
        area.removeGameComponent(next);
      }
    }
  }

  private void moveEnemyAreaGameComponents() {

    IIterator<Movable> movableIterator = enemyArea.createTypeIterator(Movable.class);
    Set<Position> validPositions = enemyArea.getPositionsOfAllEmptySpots();
    Map<GameComponent, Position> nextPositionsOfMovedComponents = new HashMap<>();
    while (movableIterator.hasNext()) {
      Movable next = movableIterator.next();
      Position currentPosition = enemyArea.getPositionOfGameComponent(next);
      Position nextPosition = (next).getNextPosition(currentPosition, validPositions);
      if (!nextPosition.equals(currentPosition)) {
        nextPositionsOfMovedComponents.put(next, nextPosition);
      }
    }
    for (GameComponent component : nextPositionsOfMovedComponents.keySet()) {
      Position nextPosition = nextPositionsOfMovedComponents.get(component);
      enemyArea.removeGameComponent(component);
      enemyArea.setGameComponentAtPosition(nextPosition, component);
    }
  }

  private void updateCollidedGameComponents() {
    for (Collision collision : activeCollisions) {
      collision.updateCollidedGameComponents();
    }
  }

  protected void setActiveCollisions(Set<Collision> activeCollisions) {
    this.activeCollisions = activeCollisions;
  }

  /***
   * @see GameControlAPI#singleEnemyIsOutOfRange()
   */
  @Override
  public boolean singleEnemyIsOutOfRange() {
    IIterator<Enemy> enemyIterator = enemyArea.createTypeIterator(Enemy.class);
    while (enemyIterator.hasNext()) {
      Enemy enemy = enemyIterator.next();
      if (enemy.isOutOfRange()) {
        return true;
      }
    }
    return false;

  }

  private void placeNewComponentsFromComponents() {
    placeNewComponentsFromSingleArea(enemyArea, 1);
    placeNewComponentsFromSingleArea(playerArea, getEnemyAreaSpacesPerPlayerAreaSpace());
  }

  private void placeNewComponentsFromSingleArea(PlayableArea area, int multiplyFactor) {
    IIterator<Actionable> actionableIterator = area.createTypeIterator(Actionable.class);
    while (actionableIterator.hasNext()) {
      enactSingleComponentAction(actionableIterator.next(), area, multiplyFactor);
    }
  }


  private void enactSingleComponentAction(Actionable actionable, PlayableArea area,
      int multiplyFactor) {
    if (actionable instanceof ShootEnemy) {
      area.contains(actionable);
    }
    if (actionable.checkAction() && area.contains(actionable)) {
      Position currentPosition = area.getPositionOfGameComponent(actionable);
      Map<Position, GameComponent> componentsToAdd = actionable
          .enactAction(currentPosition, gameStatus);
      addNewComponentsToEnemyArea(currentPosition, componentsToAdd, multiplyFactor);
    }
  }

  private void addNewComponentsToEnemyArea(Position centerPosition,
      Map<Position, GameComponent> componentsToAdd, int multiplyFactor) {
    int centerPositionRow = centerPosition.getRow() * multiplyFactor;
    int centerPositionColumn = centerPosition.getColumn() * multiplyFactor;
    for (Position nextPosition : componentsToAdd.keySet()) {
      int proposedColumnOfComponentToAdd = nextPosition.getColumn() * multiplyFactor;
      int proposedRowOfComponentToAdd = nextPosition.getRow() * multiplyFactor;
      int rowOfComponentToAdd =
          proposedRowOfComponentToAdd + getRelativeDirection(centerPositionRow,
              proposedRowOfComponentToAdd);
      int columnOfComponentToAdd =
          proposedColumnOfComponentToAdd + getRelativeDirection(centerPositionColumn,
              proposedColumnOfComponentToAdd);
      setGameComponentWithoutRelativeDirection(rowOfComponentToAdd, columnOfComponentToAdd,
          componentsToAdd, nextPosition,
          getRelativeDirection(centerPositionRow, proposedRowOfComponentToAdd),
          getRelativeDirection(centerPositionColumn, proposedColumnOfComponentToAdd));
    }
  }

  private void setGameComponentWithoutRelativeDirection(int rowOfComponentToAdd, int columnOfComponentToAdd, Map<Position, GameComponent> componentsToAdd, Position nextPosition, int relativeDirectionRow, int relativeDirectionColumn) {
    Position positionOfComponentToAdd = new Position(rowOfComponentToAdd - relativeDirectionRow, columnOfComponentToAdd - relativeDirectionColumn);
    getEnemyArea().setGameComponentAtPosition(positionOfComponentToAdd, componentsToAdd.get(nextPosition));
  }

  private int getRelativeDirection(int centerPositionRow, int proposedRow) {
    if (centerPositionRow < proposedRow) {
      return -1;
    } else {
      return 1;
    }
  }

  protected void updateSetOfActiveCollisions() {
    Set<Collision> activeCollisions = new HashSet<>();

    Set<Collision> towerEnemyCollisions = collisionDetector
        .createCollisions(Tower.class, Enemy.class, getPlayerArea(), getEnemyArea());
    Set<Collision> enemyProjectileCollisions = collisionDetector
        .createCollisions(Health.class, Projectile.class, getEnemyArea(), getEnemyArea());
    Set<Collision> towerProjectileCollisions = collisionDetector
        .createCollisions(Health.class, Projectile.class, getPlayerArea(), getEnemyArea());
    Set<Collision> projectileProjectileCollisions = collisionDetector
        .createCollisions(Projectile.class, Projectile.class, getEnemyArea(), getEnemyArea());
    Set<Collision> enemyFireCollisions = collisionDetector
        .createCollisions(Health.class, Fire.class, getEnemyArea(), getEnemyArea());
    Set<Collision> towerFireCollisions = collisionDetector
        .createCollisions(Health.class, Fire.class, getPlayerArea(), getEnemyArea());

    activeCollisions.addAll(towerEnemyCollisions);
    activeCollisions.addAll(enemyProjectileCollisions);
    activeCollisions.addAll(towerProjectileCollisions);
    activeCollisions.addAll(projectileProjectileCollisions);
    activeCollisions.addAll(enemyFireCollisions);
    activeCollisions.addAll(towerFireCollisions);
    setActiveCollisions(activeCollisions);
  }
}
