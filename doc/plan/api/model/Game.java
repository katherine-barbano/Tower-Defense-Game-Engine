/***
 * API is the top level of Model. Communicates between the Model and the Controller. Controller
 * may pass some of the info obtained from this API directly into the API for GameView.
 */
public interface Game {

  /***
   * Returns the remaining health of an enemy object
   * @param enemyPosition Position class is contained in controller, and keeps track of where the enemy is in the EnemyGrid
   */
  public abstract getEnemyHealth(Position enemyPosition);

  /***
   * Returns the remaining health of a tower object
   * @param towerPosition Position class keeps track of where tower is in the PlayerGrid
   */
  public abstract getTowerHealth(Position towerPosition);

  /***
   * Returns the Class type corresponding to the given position in EnemyGrid.
   * Returning the Class of an enemy subclass type being used allows the view to
   * instantiate the specific image needed to
   * represent that enemy type using reflection.
   * Throws an exception if position is out of bounds.
   * @param position Position object
   * @return Class type of an enemy subclass
   */
  public abstract Class getEnemyGridComponentTypeAtPosition(Position position);

  /***
   * Returns the Class type corresponding to the given position in PlayerGrid.
   * Returning the Class of an PlayerGridComponent subclass type being used allows the view to
   * instantiate the specific image needed to
   * represent that PlayerGridComponent type using reflection.
   * Throws an exception if position is out of bounds.
   * @param position Position object
   * @return Class type of an enemy subclass
   */
  public abstract Class getPlayerGridComponentTypeAtPosition(Position position);

  /***
   * Return size 2 list of row, col of EnemyGrid size
   * @return List<int>
   */
  public abstract List<int> getEnemyGridSize();

  /***
   * Return size 2 list of row, col of PlayerGrid size
   * @return List<int>
   */
  public abstract List<int> getPlayerGridSize();

  /***
   * Used in controller to iterate over enemy grid
   * @return set of all positions in enemy grid
   */
  public Set<Position> getAllPositionsInEnemyGrid();

  /***
   * Used in controller to iterate over player grid
   * @return set of all positions in player grid
   */
  public Set<Position> getAllPositionsInPlayerGrid();

  /***
   * Returns integer value of sun remaining
   * @return int
   */
  public abstract int getSunAmount();

  /***
   * Sets the amount of sun remaining, passing in the elapsedTime from the controller
   * @param elapsedTime passed in from controller as double
   */
  public abstract void setSunAmount(double elapsedTime);

  /***
   * Returns the amount of points
   * @return int value of points currently gained
   */
  public abstract int getPoints();

  /***
   * Returns amount of time left. Throws exception if the GameStatus object keeping track of time
   * does not exist.
   * @return int
   */
  public abstract int getRemainingTime();

  /***
   * Sets amount of time left. Throws exception if the GameStatus object keeping track of time
   * does not exist.
   * @param elapsedTime passed in from controller
   */
  public abstract void setRemainingTime(double elapsedTime);

  /***
   * Creates a tower of some specified type in specified position within PlayerGrid.
   * This is called either when initially intializing the Game from controller's initial
   * data files, or when the controller's listeners are notified that the user has clicked
   * in the view to build a new tower.
   *
   * Throws exception if position does not exist, or if
   * position occupied by another tower already, or if position is blocked, or if not enough
   * sun exists to build a tower.
   * @param towerType Class of the tower subclass to create using reflection
   * @param position Position object in PlayerGrid
   */
  public abstract void createTower(Class towerType, Position position) throws ModelException;

  /***
   * Creates an enemy of some specified type in specified position within EnemyGrid.
   * This is called either when initially intializing the Game from controller's initial
   * data files, or after every step when the controller sends in the next column of enemies.
   *
   * Throws exception if position does not exist.
   * @param enemyType Class of the enemy subclass to create using reflection
   * @param position Position object in EnemyGrid
   */
  public abstract void createEnemy(Class enemyType, Position position);

  /***
   * Controller calls this every step to determine if any enemy has moved past the leftmost column.
   * This is a losing condition for many game variations.
   * @return true if an enemy has moved past the leftmost column in the EnemyGrid
   */
  public abstract boolean enemyHasMovedPastLeftBorder();
}