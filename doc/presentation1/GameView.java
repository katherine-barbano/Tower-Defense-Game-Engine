/***
 * API in view that communicates between the controller and view. This deals with the playable
 * area of the game: that is, everything in the GridPanel, the HUD, plant construction.
 */
public interface GameView {

  /***
   * This method called in every step by the controller. Event listeners in the view return true if a tower
   * was created in the view.
   * @return true if tower was created this step, false if tower was not created this step
   */
  public abstract boolean getTowerWasCreated();

  /***
   * Returns the type of tower the player is currently creating. Passes to controller to use in
   * createTower method in Model API.
   * @return Class type of tower that is being created
   */
  public abstract Class getTowerTypeToCreate();

  /***
   * Gets the position that the player placed a new tower in the view. Passes to controller to use in
   * createTower method in Model API.
   * @return Position object in PlayerGrid
   */
  public abstract Position getNewTowerPlacementPosition();

  /***
   * Called every step by controller to determine whether the game is paused by the user.
   * @return true if game is paused
   */
  public abstract boolean getGamePauseStatus();

  /***
   * Controller passes in info for the bank and queue in the view to use.
   *
   * We need to think about how to make this flexible for the queue. Should we
   * actually pass in a Queue directly?
   * @param towerTypeAndCost maps class type of a tower to what its corresponding cost to by is in the bank
   */
  public abstract setAvailableTowerTypesAndCost(Map<Class,int> towerTypeAndCost);

  /***
   * Used in the "flag" variation of the game, where killing enemies gives the player
   * some point value.
   * @param pointValue int of number of points enemy will give you if it is killed
   * @param Position position of Enemy in EnemyGrid
   */
  public abstract void setPointValueGradientOfEnemy(int pointValue, Position position);

  /***
   * Displays in the HUD the amount of sun the player has. Controller first calls the method in Model API
   * to get sun remaining from model, then passes the result into here as an arg to display it.
   * @param sunAmount Int of amount of sun remaining for player
   */
  public abstract void setSunRemainingDisplay(int sunAmount);

  /***
   * Displays in the HUD the time remaining. Controller first calls the method in Model API
   * to get time remaining from model, then passes the result into here as an arg to display it.
   * @param timeRemaining int from model
   */
  public abstract void setTimeRemainingDisplay(int timeRemaining);

  /***
   * Sets the total accumulated score to be displayed in the HUD. Controller first calls the method in Model API
   * to get total points from model, then passes the result into here as an arg to display it.
   * @param totalAccumulatedScore int from model.
   */
  public abstract void setPointDisplay(int totalAccumulatedScore);

  /***
   * Displays whether the game was lost or won. Called by controller when the entire game (not just the level) is lost or won.
   * @param playerWonGame true if the player won, false if the player lost
   */
  public abstract void displayGameEnding(boolean playerWonGame);

  /***
   * Updates the display with the enemy of a specified subclass type at a certain position
   * Controller calls the method, which gets enemyType and position by calling methods in ModelAPI
   * @param enemyType subclass type of the enemy at that position
   * @param position Position object
   */
  public abstract void updateEnemyDisplay(Class enemyType, Position position);

  /***
   * Displays a balloon notification popup at the edge of the screen for a few seconds about
   * an exception that was thrown in the controller.
   * @param message String to show the message for the notification
   */
  public abstract void displayControllerExceptionAsPopup(String message);
}