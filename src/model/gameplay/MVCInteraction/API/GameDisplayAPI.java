package model.gameplay.MVCInteraction.API;

import controller.ConfigObjects.Config;
import model.gameComponents.GameComponent;
import model.gameplay.gameplayResources.ModelException;
import model.gameplay.gameplayResources.Position;
import model.gameplay.gameplayResources.QuadConsumer;

/***
 * We decided to separate out GameDisplayAPI from GameControlAPI so that the view would not
 * have access to model-controller communication methods that view would not need to use.
 * In addition, having a way for GameControlAPI to return an associated GameDisplayAPI is essential
 * because otherwise, the same Model would not be connected between the controller and view.
 * If controller just instantiated a separate GameControlAPI and GameDisplayAPI on its own,
 * the view would display a different model from the one the controller was stepping through.
 * The service that GameDisplayAPI performs is that it facilitates model-view communication.
 * This includes helper methods about the sizing of the enemy and player areas, as well as methods to
 * enact a function on the enemy components, and the player components separately (which lets the
 * view display those GameComponents without needing to expose its implementation of the GameComponents
 * to the view). These methods
 * hide implementation details of the enemy and player areas in model. This allows model
 * to use the same type of structure to store enemy and player data, but have the view display
 * the data differently for both. In addition, there are methods that allow view to enact the
 * results of its user input event handlers within model, like creating a tower.
 *  Hiding this implementation detail allows towers
 * to be created as different GameComponent objects in model using different information in the Config file.
 * GameDisplay also has a method to return an associated GameStatusAPI.
 *
 * @author Katherine Barbano
 */
public interface GameDisplayAPI {

  /***
   * Retrieves the GameStatusAPI associated with this instance of GameDisplayAPI.
   * A few View classes only need to use status-related methods, and do not need access to the entire GameDisplayAPI,
   * so being able to pass only those methods into view allows the view to follow Interface Segregation
   * Principle more closely.
   * @return GameStatusAPI
   */
  GameStatusAPI getGameStatusAPI();

  /***
   * Helper method the view uses in the bank/Panel implementation of plantSelection. Creates a tower
   * in the specified Position of the playerArea. Deducts the amount of sun that the tower costs
   * from the status.
   * @param towerProperties Config file read in from controller
   * @param position Position in which the tower should be placed
   * @throws ModelException if the player should not be allowed to create a tower in that position
   */
  void createTowerWithSun(Config towerProperties, Position position) throws ModelException;

  /***
   * Helper method the view uses in the Queue implementation of plantSelection. Creates a tower
   * in the specified Position of the playerArea. Does not deduct any sun from status.
   * @param towerProperties Config file read in from controller
   * @param position Position in which the tower should be placed
   * @throws ModelException if the player should not be allowed to create a tower in that position
   */
  void createTowerWithoutSun(Config towerProperties, Position position) throws ModelException;

  /***
   * Returns how many rows a component in the enemy area should take up. View uses this
   * to adjust the height of that GameComponent's image
   * @param component GameComponent in enemyArea
   * @return height
   */
  int getHeightOfEnemyAreaComponent(GameComponent component);

  /***
   * Returns how many columns a component in the enemy area should take up. View uses this
   * to adjust the width of that GameComponent's image
   * @param component GameComponent in enemyArea
   * @return width
   */
  int getWidthOfEnemyAreaComponent(GameComponent component);

  /***
   * Removes a single tower at the given position if a tower exists there by setting the
   * state of the playerArea to empty at that position. Used by view to implement the
   * functionality of the digging tool.
   * @param positionToRemove Position in playerArea that should be set to empty
   */
  void removePlayerComponent(Position positionToRemove);

  /***
   * Returns the number of rows in the enemy area instance. Used so view can create a new corresponding
   * EnemyGridView that reflects the structure of the model.
   * @return int
   */
  int getEnemyAreaNumberOfRows();

  /***
   * Returns the number of columns in the enemy area instance. Used so view can create a new corresponding
   * EnemyGridView that reflects the structure of the model.
   * @return int
   */
  int getEnemyAreaNumberOfColumns();

  /***
   * Returns the number of rows in the player area instance. Used so view can create a new corresponding
   * PlayerGridView that reflects the structure of the model.
   * @return int
   */
  int getPlayerAreaNumberOfRows();

  /***
   * Returns the number of columns in the player area instance. Used so view can create a new corresponding
   * PlayerGridView that reflects the structure of the model.
   * @return int
   */
  int getPlayerAreaNumberOfColumns();

  /***
   * Calls accept for the QuadConsumer argument, which specifies an action to be enacted on all
   * components in the enemyArea. View uses this to display all GameComponents in the enemyArea
   * through the use of a lambda function.
   * @param updateGameComponent QuadConsumer with GameComponent, row, column, health arguments
   */
  void enactFunctionOnEnemyComponents(
      QuadConsumer<GameComponent, Integer, Integer, Double> updateGameComponent);

  /***
   * Calls accept for the QuadConsumer argument, which specifies an action to be enacted on all
   * components in the playerArea. View uses this to display all GameComponents in the playerArea
   * through the use of a lambda function.
   * @param updateGameComponent QuadConsumer with GameComponent, row, column, health arguments
   */
  void enactFunctionOnPlayerComponents(
      QuadConsumer<GameComponent, Integer, Integer, Double> updateGameComponent);
}
