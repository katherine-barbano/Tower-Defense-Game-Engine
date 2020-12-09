package model.gameplay.MVCInteraction.API;

import controller.ConfigObjects.Config;
import java.util.List;

/***
 * The service GameControlAPI provides is to provide the Controller
 *  with all methods it needs to know about the current state of the Model, so that
 *   it facilitates all Model-Controller communication. It has helper methods about the current state of the model
 * for the win/loss conditions in Controller to easily call. It also
 * has the method to moveComponents, which is the method controller calls every step to update
 * the model. This API is also able to hide the implementation detail of creating a wave of enemies from a List of Config files,
 * where Config files are the objects that controller passes around with enemy data
 * from properties files. By hiding this implementation detail, it allows Enemies to be
 * instantiated differently for a path or a grid. Finally, GameControlAPI has a method to
 * return a GameDisplayAPI. This method is called by controller to pass to the view as its
 * method of communicating with the model.
 *
 * @author Katherine Barbano
 */
public interface GameControlAPI {

  /***
   * This method returns the GameDisplayAPI object associated with the current GameControlAPI.
   * Controller should call this method and pass in the returned GameDisplayAPI into the view
   * instead of having Controller create its own GameDisplayAPI.
   * @return GameDisplayAPI object
   */
  GameDisplayAPI getGameDisplayAPI();

  /***
   * Controller calls this every step to update components by 1 move. This is the functionality involved in this:
   *
   *   1. Update the ranges of all shooter GameComponents (which allows
   *   enemy shooters to only shoot when there is a tower in range, and vice versa).
   *
   *   2. Place new GameComponents produced from all Actionables, including Projectiles that towers or enemies
   *   are now ready to place, or Fire components for SingleUse components that are ready to explode.
   *
   *   3. All components that are out of range (e.g. off the boundaries of the grid) are removed.
   *
   *   4. A method is called for all collisions that enacts the result of each collision in the grid.
   *
   *   5. All movable objects in the PlayableArea determine their next position based on the empty
   *   positions in the grid, and move there if that position is valid.
   *
   *   6. The set of active collisions is updated by the collision detector for the next step.
   *
   *   7. All game status values are updated by
   *   calling gameStatus.update.
   */
  void moveComponents();

  /***
   * Creates a wave of enemies in the last column of the grid, including empty positions.
   *
   * Assumes that controller has already read in all of the data from properties files into the
   * Config objects. The Config files should be of subclass EnemyConfig specifically.
   * @param enemyWaveName List of Config files
   */
  void createWaveOfEnemies(List<Config> enemyWaveName);

  /***
   * Tells whether an enemy has made it completely across the PlayableArea. Controller should
   * use this as a helper in the win/lose conditions.
   * @return true if an enemy has crossed the left side of the screen
   */
  boolean singleEnemyIsOutOfRange();

  /***
   * Tells whether all enemies have been eliminated in the PlayableArea. Controller should
   * use this as a helper in the win/lose conditions
   * @return true if all enemies are eliminated
   */
  boolean noEnemiesExist();

  /***
   * Returns the number of rows in the enemy's Playable Area instance per a single row
   * in the player's Playable Area (which is equal to the ratio for columns as well). Controller
   * uses this to dictate the animation speed, since more enemy rows/columns corresponds to
   * finer and smoother animation, but a slower runtime and less efficiency. Making this value
   * too high might produce lag when running main.
   * @return integer
   */
  int getEnemyAreaSpacesPerPlayerAreaSpace();

  /***
   * Resets all game statuses back to their initial states. For levels with certain
   * initial sun, this will reset the sun back to the initial sun, but points and time will
   * reset back to 0.
   */
  void reset();
}
