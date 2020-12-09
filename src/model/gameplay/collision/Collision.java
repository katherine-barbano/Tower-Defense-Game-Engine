package model.gameplay.collision;

/***
 * Represents a single collision between two GameComponents. Subclass names should be of the format
 * [GameComponent1][GameComponent2]Collision.java. This class describes the result of what should
 * happen in the PlayableAreas of these two objects, and to these two objects themselves, as a result
 * of the collision.
 *
 * @author Katherine Barbano
 */
public interface Collision {

  /***
   * Call this to enact the result of the collision. This will modify the GameComponents involved
   * in the Collision or the PlayableArea of either of the components themselves so that the collision
   * has some effect.
   */
  void updateCollidedGameComponents();
}
