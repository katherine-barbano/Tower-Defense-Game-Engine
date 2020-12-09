package model.gameComponents;

import java.util.HashMap;
import java.util.Map;
import model.gameplay.MVCInteraction.API.GameStatusAPI;
import model.gameplay.gameplayResources.Position;

/**@author Priya Rathinavelu
 * This class is used for creating a "fire state" within the grid. It is currently only used
 * by the exploding tower and the exploding enemy so that when they "explode", nearby states become
 * this "fire" state. This class relies on several other interfaces, specifically Actionable,
 * Removable, and GameComponent. Actionable is used so that the grid can correctly on the methods
 * needed to use this class. Removable is also implemented in this class so that the fire states
 * can actually be removed after a certain number of steps within the game. Lastly, this class
 * relies on the GameComponent interface so that it can be treated as one of the normal components
 * that exist within the grid. Because the grid itself handles a lot of the interactions with
 * fire and other game components, this class does not have too many features of its own. One can
 * use this class by just creating a new instance of fire so that it becomes a game component and can
 * interact/use it from there. It is also important to list which type of object created this
 * fire state so that the grid knows how to handle it.
 */

public class Fire implements Actionable, Removable, GameComponent {

    public static final int DISAPPEAR_RATE = 10;

    private final int myAnimationSpeed;
    private final GameComponent shooter;
    private final Class typeShotFrom;
    private int stepsBeforeNextAction = DISAPPEAR_RATE;
    private int stepsSinceLastAction = 0;

  public Fire(int animationSpeed, GameComponent componentShotFrom, Class<?> typeShotFrom) {
    shooter = componentShotFrom;
    this.typeShotFrom = typeShotFrom;
    myAnimationSpeed = animationSpeed;
  }

  /**This method is used to check whether it is time for this component's action to occur
   * @return a boolean indicating whether the action needs to occur
   */
  @Override
  public boolean checkAction() {
    if (stepsSinceLastAction == stepsBeforeNextAction) {
      this.stepsSinceLastAction = 0;
      return true;
    } else {
      this.stepsSinceLastAction++;
      return false;
    }
  }

  /**
   * @param rate an integer representing the rate that updates the number of steps before the next
   *             action should occur
   */
  @Override
  public void setStepsBeforeNextAction(int rate) {
    stepsBeforeNextAction = rate;
  }

  /**
   * @param currentPosition the current Position of the fire state
   * @param gameStatus GameStatusAPI for updating the game status (explained in analysis)
   * @return an empty map of game components
   */
  @Override
  public Map<Position, GameComponent> enactAction(Position currentPosition,
      GameStatusAPI gameStatus) {
    return new HashMap<>();
  }


  @Override
  public String getName() {
    return "Fire";
  }

  @Override
  public int getWidth() {
    return myAnimationSpeed;
  }

  @Override
  public int getHeight() {
    return myAnimationSpeed;
  }

  public Class<?> getShooterClass() {
    return typeShotFrom;
  }

  public GameComponent getShooterInstance() {
    return shooter;
  }

  @Override
  public boolean isOutOfRange() {
    return stepsSinceLastAction == stepsBeforeNextAction;
  }
}
