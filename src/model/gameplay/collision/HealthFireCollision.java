package model.gameplay.collision;

import model.gameComponents.Fire;
import model.gameComponents.GameComponent;
import model.gameComponents.Movable;
import model.gameplay.gameplayResources.ModelException;
import model.gameplay.playableArea.PlayableArea;

/***
 * Describes the results of a collision between a Health component (ie an enemy or a tower) and
 * a Fire component
 *
 * @author Katherine Barbano
 */
public class HealthFireCollision implements Collision {

  private final PlayableArea healthArea;
  private final PlayableArea fireArea;
  private final GameComponent healthComponent;
  private final GameComponent fire;

  /***
   * Instantiated by CollisionFactory so that the first component is the Health, and the
   * second component is the Fire.
   * @param healthComponent Health
   * @param fire Fire
   * @param healthArea PlayableArea that Health is contained in
   * @param fireArea PlayableArea that Fire is contained in
   */
  public HealthFireCollision(GameComponent healthComponent, GameComponent fire,
      PlayableArea healthArea, PlayableArea fireArea) {
    this.healthComponent = healthComponent;
    this.fire = fire;
    this.healthArea = healthArea;
    this.fireArea = fireArea;
  }

  /***
   * If the fire colliding with a diff health type from shooter, remove the collided health
   * If the fire colliding with same INSTANCE of shooter and checkAction true, remove the fire and health component
   */
  @Override
  public void updateCollidedGameComponents() {
    try {
      if(collideDifferentType() || collideSameInstance()) {
        healthArea.removeGameComponent(healthComponent);
      }
    } catch (ModelException e) {
      //do nothing. multiple enemies have collided with fire, and another collision has already removed the fire in the same step.
    }
  }

  private boolean collideDifferentType() {
    return !((Fire)fire).getShooterClass().isInstance(healthComponent);
  }

  private boolean collideSameInstance() {
    return ((Fire)fire).getShooterInstance() == healthComponent && ((Movable)fire).isOutOfRange();
  }
}
