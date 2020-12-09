package model.gameplay.MVCInteraction.concreteModel;

import controller.ConfigObjects.Config;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import model.factory.GameComponentFactory;
import model.factory.TowerComponentFactory;
import model.gameComponents.GameComponent;
import model.gameComponents.SingleState;
import model.gameComponents.playerGridComponents.Tower;
import model.gameplay.MVCInteraction.API.GameDisplayAPI;
import model.gameplay.MVCInteraction.API.GameStatusAPI;
import model.gameplay.gameplayResources.ModelException;
import model.gameplay.gameplayResources.Position;
import model.gameplay.gameplayResources.QuadConsumer;
import model.gameplay.playableArea.PlayableArea;

/***
 * Instance of GameDisplay is maintained in GameControl, which passes in a corresponding enemyArea and playerArea
 * as arguments. Implements the functionality behind all view-model interaction by implementing
 * GameDisplayAPI.
 * 
 * @author Katherine Barbano
 */
public class GameDisplay implements GameDisplayAPI {

  private final GameStatusAPI gameStatus;
  private final GameComponentFactory towerFactory;
  private PlayableArea enemyArea;
  private PlayableArea playerArea;

  /***
   * Stores the enemyArea, playerArea, gameStatus arguments. Creates a tower factory as a private
   * var to use throughout various methods within this class.
   * @param enemyArea PlayableArea
   * @param playerArea PlayableArea
   * @param enemyRowScale number of units in enemy grid corresponding to 1 unit in player grid
   * @param gameStatus GameStatusAPI
   */
  public GameDisplay(PlayableArea enemyArea, PlayableArea playerArea, int enemyRowScale,
      GameStatusAPI gameStatus) {
    this.enemyArea = enemyArea;
    this.playerArea = playerArea;
    this.gameStatus = gameStatus;
    towerFactory = new TowerComponentFactory(getPlayerAreaNumberOfRows() * enemyRowScale,
        getPlayerAreaNumberOfColumns() * enemyRowScale, enemyRowScale, gameStatus);
  }

  protected void setPlayerArea(PlayableArea playerArea) {
    this.playerArea = playerArea;
  }

  protected void setEnemyArea(PlayableArea enemyArea) {
    this.enemyArea = enemyArea;
  }

  /***
   * @see GameDisplayAPI#getEnemyAreaNumberOfRows() 
   */
  @Override
  public int getEnemyAreaNumberOfRows() {
    return enemyArea.getNumberOfRows();
  }

  /***
   * @see GameDisplayAPI#getEnemyAreaNumberOfColumns()
   */
  @Override
  public int getEnemyAreaNumberOfColumns() {
    return enemyArea.getNumberOfColumns();
  }

  /***
   * @see GameDisplayAPI#getPlayerAreaNumberOfRows()
   */
  @Override
  public int getPlayerAreaNumberOfRows() {
    return playerArea.getNumberOfRows();
  }

  /***
   * @see GameDisplayAPI#getPlayerAreaNumberOfColumns()
   */
  @Override
  public int getPlayerAreaNumberOfColumns() {
    return playerArea.getNumberOfColumns();
  }

  /***
   * @see GameDisplayAPI#removePlayerComponent(Position) 
   */
  @Override
  public void removePlayerComponent(Position positionToRemove) {
    GameComponent componentToRemove = playerArea.getGameComponentAtPosition(positionToRemove);
    Class componentClass = componentToRemove.getClass();
    if (!componentClass.isEnum()) {
      playerArea.removeGameComponent(componentToRemove);
    }
  }

  /***
   * @see GameDisplayAPI#getWidthOfEnemyAreaComponent(GameComponent) 
   */
  @Override
  public int getWidthOfEnemyAreaComponent(GameComponent component) {
    return component.getWidth();
  }

  /***
   * @see GameDisplayAPI#getHeightOfEnemyAreaComponent(GameComponent) 
   */
  @Override
  public int getHeightOfEnemyAreaComponent(GameComponent component) {
    return component.getHeight();
  }

  /***
   * @see GameDisplayAPI#enactFunctionOnEnemyComponents(QuadConsumer) 
   */
  @Override
  public void enactFunctionOnEnemyComponents(
      QuadConsumer<GameComponent, Integer, Integer, Double> updateGameComponent) {
    Set<GameComponent> typeToIgnore = new HashSet<>(
        Arrays.asList(SingleState.EMPTY, SingleState.BLOCKED));
    enemyArea.enactFunctionOnAllGridComponentsExcept(updateGameComponent, typeToIgnore);
  }

  /***
   * @see GameDisplayAPI#enactFunctionOnPlayerComponents(QuadConsumer) 
   */
  @Override
  public void enactFunctionOnPlayerComponents(
      QuadConsumer<GameComponent, Integer, Integer, Double> updateGameComponent) {
    playerArea.enactFunctionOnAllGridComponents(updateGameComponent);
  }

  /***
   * @see GameDisplayAPI#getGameStatusAPI() 
   */
  @Override
  public GameStatusAPI getGameStatusAPI() {
    return gameStatus;
  }

  /***
   * @see GameDisplayAPI#createTowerWithoutSun(Config, Position) 
   */
  @Override
  public void createTowerWithoutSun(Config towerProperties, Position position)
      throws ModelException {
    Tower tower = ((Tower) towerFactory.createGameComponent(towerProperties));
    playerArea.setGameComponentAtPosition(position, tower);
  }

  /***
   * @see GameDisplayAPI#createTowerWithSun(Config, Position) 
   */
  @Override
  public void createTowerWithSun(Config towerProperties, Position position) throws ModelException {
    Tower tower = ((Tower) towerFactory.createGameComponent(towerProperties));
    if (gameStatus.validateCost(tower.getMyCost())) {
      if (playerArea.setGameComponentAtPosition(position, tower)) {
        gameStatus.changeSunAmount(-tower.getMyCost());
      }
    }
  }

}
