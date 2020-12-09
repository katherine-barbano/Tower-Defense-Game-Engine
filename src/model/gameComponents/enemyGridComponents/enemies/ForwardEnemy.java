package model.gameComponents.enemyGridComponents.enemies;

import controller.ConfigObjects.EnemyConfig;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**@author Priya Rathinavelu
 * This class extends the abstract class and is used to create the most basic version of the enemy.
 * It does not have any special actions. It assumes that there are no special powers for this
 * enemy implementation. It depends on the enemy abstract class and the enemyConfig object which is
 * passed into the super constructor to create the enemy. An example of how to use this class would
 * be to create a new zombie properties file, and for "Type", list "Forward". This means that any
 * enemies that are created based on that new properties file will have the general properties of this
 * forward zombie.
 *
 */
public class ForwardEnemy extends Enemy {

  /**
   *
   * @param enemyConfig Config object storing information about the enemy from the properties file
   * @param gridRows number of grid rows (not used, explained in analysis)
   * @param gridCols number of grid cols (not used, explained in analysis)
   * @param animationSpeed speed of the animation, used for determining size of the enemy
   * @param status a GameStatusAPI needed for updating the status as the enemy changes
   */
  public ForwardEnemy(EnemyConfig enemyConfig, int gridRows, int gridCols, int animationSpeed,
      GameStatusAPI
          status) {
    super(enemyConfig, animationSpeed, status);
  }

}




