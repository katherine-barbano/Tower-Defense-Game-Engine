package model.gameplay.playableArea;

import java.util.List;
import java.util.Map;
import java.util.Set;
import model.gameComponents.GameComponent;
import model.gameplay.gameplayResources.Position;
import model.gameplay.gameplayResources.QuadConsumer;

/***
 * The PlayableArea interface is intended
 * to act as an API between the GameComponent data stored in a grid in the lower level Model
 * and the higher level model operations in GameControl that interact more closely with View and Controller.
 * PlayableArea interface is implemented by a concrete PlayableAreaData that uses a List<List<GameComponent>>
 * as the data structure to store GameComponents. The higher level model modules like GameControl
 * reference the PlayableArea interface rather than the concrete subclass.
 * This effectively separates out the List<List<GameComponent>> data structure out so no upper level Model modules that
 * reference PlayableArea, like GameControl, can have access to the data structure implementation itself.
 * Instead, PlayableArea has helper methods to manipulate the data without providing access
 * to the data structure itself
 *
 * With this design, it would even be possible to extend PlayableArea
 * into another subclass to implement the data structure differently. One design I considered
 * implementing was having another concrete implementation of PlayableArea
 * with a Map instead or a List of Cell objects (where Cell objects contain GameComponents)
 * to make the class more efficient, which would work
 * because the higher GameControl class only depends on the PlayableArea
 * interface, not the lower level subclass implementation of the
 * data storage itself. Therefore, the use of the PlayableArea interface to separate out
 * the lower level model from the higher level model demonstrates the Dependency Inversion
 * Principle by removing any higher level GameControl dependency on lower level data structure implementations.
 *
 * @author Katherine Barbano
 */
public interface PlayableArea {

  /***
   * Sets the GameComponent data that is being stored to correspond to the initialBoard, which was
   * produced from PlayableAreaFactory.
   * @param initialBoard List<List<GameComponent>>
   */
  void initializeGameComponentDataFromBoard(List<List<GameComponent>> initialBoard);

  /***
   * Creates an Iterator of a specific subclass (or superclass type). This iterator returns the
   * next value in the game component data that is of the type specified by tClass and <T>. For example, if
   * the type is Shooter, all components returned by this IIterator will be Shooters. All other components
   * will be ignored by the iterator.
   * @param tClass target type to iterate through
   * @param <T> target type to iterate through
   * @return T casted to tClass
   */
  <T> IIterator<T> createTypeIterator(Class tClass);

  /***
   * Retrieves the positions and game components in the spots to the right of the given start position.
   * Stops the range being returned at a target stop type.
   * @param startPosition Where to start iterating from
   * @param stopType Class at which to stop iterating through the range (usually an enemy or tower)
   * @return Map of Position, GameComponent
   */
  Map<Position, GameComponent> getRowRangeToRight(Position startPosition, Class stopType);

  /***
   * Retrieves the positions and game components in the spots to the left of the given start position.
   * Stops the range being returned at a target stop type.
   * @param startPosition Where to start iterating from
   * @param stopType Class at which to stop iterating through the range (usually an enemy or tower)
   * @return Map of Position, GameComponent
   */
  Map<Position, GameComponent> getRowRangeToLeft(Class stopType, Position startPosition);

  /***
   * Retrieves the positions and game components in the spots above the given start position.
   * Stops the range being returned at a target stop type.
   * @param position Where to start iterating from
   * @param target Class at which to stop iterating through the range (usually an enemy or tower)
   * @return Map of Position, GameComponent
   */
  Map<Position, GameComponent> getColumnRangeUp(Position position, Class target);

  /***
   * Retrieves the positions and game components in the spots below the given start position.
   * Stops the range being returned at a target stop type.
   * @param position Where to start iterating from
   * @param target Class at which to stop iterating through the range (usually an enemy or tower)
   * @return Map of Position, GameComponent
   */
  Map<Position, GameComponent> getColumnRangeDown(Position position, Class target);

  /***
   * Only allows you to set at that position if that position is empty. Returns false if blocked
   * or another game component in that position.
   * @param position Position to set the new GameComponent at
   * @param newGameComponent Replacement GameComponent at that position
   * @return true if the GameComponent in the position being set was originally empty, and the position was set to your new component
   */
  boolean setGameComponentAtPosition(Position position, GameComponent newGameComponent);

  /***
   * Removes a target GameComponent from the PlayableArea.
   * @param gameComponentToDelete GameComponent
   */
  void removeGameComponent(GameComponent gameComponentToDelete);

  /***
   * Returns the GameComponent at a specified position.
   * @param position Position
   * @return GameComponent
   */
  GameComponent getGameComponentAtPosition(Position position);

  /***
   * Returns the Position of a specific INSTANCE (not type) of a GameComponent object.
   * @param gameComponent GameComponent
   * @return Position
   */
  Position getPositionOfGameComponent(GameComponent gameComponent);

  /***
   * Returns a set of positions that are empty in the playable area, which can be passed into
   * Movables so that they can determine their potential next position.
   * @return Set of Positions
   */
  Set<Position> getPositionsOfAllEmptySpots();

  /***
   * Calls accept for the QuadConsumer argument, which specifies an action to be enacted on all
   * components in the PlayableArea.
   * @param updateGameComponent QuadConsumer with GameComponent, row, column, health arguments
   */
  void enactFunctionOnAllGridComponents(
      QuadConsumer<GameComponent, Integer, Integer, Double> updateGameComponent);

  /***
   * Calls accept for the QuadConsumer argument, which specifies an action to be enacted on all
   * components in the PlayableArea. Calls accept only for any GameComponents that are not present
   * in the typeToIgnore argument
   * @param updateGameComponent QuadConsumer with GameComponent, row, column, health arguments
   * @param typeToIgnore Set of GameComponents to ignore and not enact accept on
   */
  void enactFunctionOnAllGridComponentsExcept(
      QuadConsumer<GameComponent, Integer, Integer, Double> updateGameComponent,
      Set<GameComponent> typeToIgnore);

  /***
   * Sets the entire last column of the game component data. Use this when reading in a new
   * wave of enemies.
   * @param gameComponentsToAdd List of GameComponents to set the last column to.
   */
  void setLastColumnOfGameComponents(List<GameComponent> gameComponentsToAdd);

  /***
   * Retrieve number of rows in the PlayableArea.
   * @return int
   */
  int getNumberOfRows();

  /***
   * Retrieve number of columns in the PlayableArea.
   * @return int
   */
  int getNumberOfColumns();

  /***
   * Determine whether a specific GameComponent INSTANCE (not type) is in the PlayableArea.
   * @param component target GameComponent
   * @return true if target instance is in PlayableArea
   */
  boolean contains(GameComponent component);

}
