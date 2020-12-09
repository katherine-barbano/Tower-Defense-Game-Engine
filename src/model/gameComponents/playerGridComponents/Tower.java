package model.gameComponents.playerGridComponents;

import controller.ConfigObjects.TowerConfig;
import model.gameComponents.Actionable;
import model.gameComponents.Damager;
import model.gameComponents.Health;

/**
 * A tower is something that the player plants that performs actions that are helpful to the player
 * Its subclasses define more specific functionality on what the actions are
 * @author alex chao
 */
public abstract class Tower implements Health, Actionable {

  private final int myCost;
  private final String myName;
  private final int animationSpeed;
  private final int startingHealth;
  private final int myScoreValue;
  private int myHealth;
  private int stepsSinceLastAction = 0;
  private int stepsBeforeNextAction;
  private boolean isAlive;

  public Tower(TowerConfig towerConfig, int animationSpeed) {
    this.myCost = towerConfig.getCost();
    setStepsBeforeNextAction(towerConfig.getRate());
    setInitialHealth(towerConfig.getHealth());
    startingHealth = myHealth;
    myName = towerConfig.getName();
    isAlive = true;
    this.animationSpeed = animationSpeed;
    myScoreValue = towerConfig.getScore();
  }


  @Override
  public void setStepsBeforeNextAction(int rate) {
    stepsBeforeNextAction = rate;
  }

  public String getName() {
    return myName;
  }

  public int getAnimationSpeed() {
    return animationSpeed;
  }

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

  @Override
  public void setInitialHealth(int health) {
    myHealth = health;
  }

  @Override
  public void updateHealth(Damager enemy) {
    myHealth -= enemy.getDamage();
    if (myHealth < 0) {
      isAlive = false;
    }
  }

  @Override
  public int getHealth() {
    return myHealth;
  }

  @Override
  public boolean isAlive() {
    return isAlive;
  }

  public int getMyCost() {
    return myCost;
  }

  @Override
  public String toString() {
    return "Tower";
  }

  @Override
  public int getScore() {
    return myScoreValue;
  }

  @Override
  public double getPercentageHealth() {
    return ((double) myHealth / startingHealth);
  }

  @Override
  public int getWidth() {
    return 1;
  }

  @Override
  public int getHeight() {
    return 1;
  }
}
