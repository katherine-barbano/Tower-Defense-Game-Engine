package model.gameComponents.enemyGridComponents.enemies;

import controller.ConfigObjects.EnemyConfig;
import java.util.HashMap;
import java.util.Map;
import model.factory.ProjectileFactory;
import model.gameComponents.Actionable;
import model.gameComponents.GameComponent;
import model.gameComponents.Shooter;
import model.gameplay.MVCInteraction.API.GameStatusAPI;
import model.gameplay.gameplayResources.Position;

/**@author Priya Rathinavelu
 * This class extends the abstract class and is used to create a shooter version of the enemy.
 * It assumes that the information about projectile type, direction, and speed will be within the
 * enemyConfig object. It depends on the enemy abstract class and the enemyConfig object which is
 * passed into the super constructor to create the enemy. Most notably, this class also depends on
 * both the Shooter and Actionable interfaces. With the shooter interface,
 * methods for setting the range will need to be overridden (this method is needed so that the enemy
 * only shoots when something is in its path). Similarly, there will need to be methods
 * overridden from the actionable interface. This is what makes this zombie unique and able to have some
 * sort of action. For this, the enactAction method was written so that a projectile would be created
 * and shot. Lastly, this class relies on the projectileFactory class and creates an instance of it
 * so that it can easily create projectiles without constantly having to reinitializing each time.
 * An example of how to use this class would
 * be to create a new zombie properties file, and for "Type", list "Shoot". This means that any
 * enemies that are created based on that new properties file will have the general properties of this
 * Shoot zombie. Within the grid, enactAction can be called so that the zombie's action occurs.
 *
 */
public class ShootEnemy extends Enemy implements Shooter, Actionable {

  private final ProjectileFactory myProjectileFactory;
  private final String myProjectileType;
  private final int myXDirection;
  private final int myYDirection;
  private int stepsBeforeNextAction;
  private int stepsSinceLastAction = 0;
  private Map<Position, GameComponent> rangeOfPositions;


  /**
   *
   * @param enemyConfiguration enemyConfig object storing the enemy's general information
   * @param gridRows number of grid rows (used for creating the projectileFactory)
   * @param gridCols number of grid cols (used for creating the projectileFactory)
   * @param animationSpeed speed of animation used for determining size
   * @param status GameStatusAPI used for updating the status as the enemy updates
   * Each shootEnemy object has its own instance of projectileFactory, used for creating the projectiles
   */
  public ShootEnemy(EnemyConfig enemyConfiguration, int gridRows, int gridCols,
      int animationSpeed, GameStatusAPI status) {
    super(enemyConfiguration, animationSpeed, status);
    int damage = enemyConfiguration.getDamage();
    myXDirection = enemyConfiguration.getXDirection();
    myYDirection = enemyConfiguration.getYDirection();
    int projectileSpeed = enemyConfiguration.getProjectileSpeed();
    setStepsBeforeNextAction(enemyConfiguration.getRate());
    myProjectileType = enemyConfiguration.getProjectileType();
    myProjectileFactory = new ProjectileFactory(projectileSpeed, damage, gridRows, gridCols,
        myXDirection,
        myYDirection, animationSpeed, Enemy.class);

  }

  /**
   * @see Actionable#setStepsBeforeNextAction(int) 
   */
  @Override
  public void setStepsBeforeNextAction(int rate) {
    stepsBeforeNextAction = rate;
  }


  /**
   *This method is used for knowing whether an action should actually occur - checks if the
   * requirements for shooting in this case have been met
   * @see Actionable#checkAction() 
   */
  @Override
  public boolean checkAction() {
    if ((stepsSinceLastAction >= stepsBeforeNextAction) && getShootingActive()) {
      this.stepsSinceLastAction = 0;
      return true;
    } else {
      this.stepsSinceLastAction++;
      return false;
    }
  }

  /**This method is used in the grid to actually create the action of the enemy - in this case, it
   * will actually show the enemy creating the projectile
   * @see Actionable#enactAction(Position, GameStatusAPI) 
   */
  public Map<Position, GameComponent> enactAction(Position currentPosition,
      GameStatusAPI gameStatus) {
    Map<Position, GameComponent> newComponents = new HashMap<>();
    if (currentPosition.getColumn() + (getWidth() * myXDirection) + myXDirection > 0) {
      Position nextPosition = new Position(currentPosition.getRow() + myYDirection,
          currentPosition.getColumn() + (getWidth() * myXDirection) + myXDirection);
      GameComponent myProj = myProjectileFactory.createProjectile(myProjectileType);
      newComponents.put(nextPosition, myProj);
    }
    return newComponents;
  }

  /**
   *This method checks whether there are any towers within the direct range of the enemy to know
   * whether projectiles should be shot
   * @return a boolean indicating whether the range of Positions of the enemy contains any Tower objects
   */
  private boolean getShootingActive() {
    if (rangeOfPositions != null) {
      for (Position currentPosition : rangeOfPositions.keySet()) {
        if (rangeOfPositions.get(currentPosition).toString().equals("Tower")) {
          return true;
        }
      }
    }
    return false;
  }


  /**
   * @see Shooter#setRange(Map) 
   */
  @Override
  public void setRange(Map<Position, GameComponent> range) {
    rangeOfPositions = range;
  }

  /**
   * @see Shooter#getXDirection() 
   */
  @Override
  public int getXDirection() {
    return myXDirection;
  }

  /**
   * @see Shooter#getYDirection() 
   */
  @Override
  public int getYDirection() {
    return myYDirection;
  }


}
