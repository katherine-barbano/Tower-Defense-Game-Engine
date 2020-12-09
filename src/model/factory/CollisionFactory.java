package model.factory;

import java.lang.reflect.Constructor;
import java.util.ResourceBundle;
import model.gameComponents.GameComponent;
import model.gameplay.MVCInteraction.concreteModel.GameControl;
import model.gameplay.collision.Collision;
import model.gameplay.gameplayResources.ModelException;
import model.gameplay.playableArea.PlayableArea;

/***
 * Uses reflection to create any subclass of collision. Uses factory design pattern.
 *
 * @author Katherine Barbano
 */
public class CollisionFactory {

  public static final String INVALID_COLLISION_VARIATION_EXCEPTION = "invalidCollisionVariationException";
  public static final String COLLISION_CLASS_NAME_PREFIX = "collisionClassNamePrefix";
  public static final String COLLISION_CLASS_NAME_SUFFIX = "collisionClassNameSuffix";

  private final ResourceBundle modelProperties;
  private final String invalidMessage;

  /***
   * Retrieve exception messages to use in this class.
   */
  public CollisionFactory() {
    modelProperties = ResourceBundle.getBundle(GameControl.MODEL_RESOURCE_PATH);
    invalidMessage = modelProperties.getString(INVALID_COLLISION_VARIATION_EXCEPTION);
  }

  /***
   * Tries to instantiate a subclass where the first object is first in the class name, and then
   * where the first object is second in the class name. If neither of these work, the Collision
   * subclass does not exist and an exception is thrown.
   * @param object1Type Class of first object
   * @param object2Type Class of second object
   * @param collidedComponent1 GameComponent first object
   * @param collidedComponent2 GameComponent second object
   * @param collidedComponent1Area Playable area of first object for constructor of Collision
   * @param collidedComponent2Area Playable area of second object for constructor of Collision
   * @return
   */
  public Collision createCollision(Class<?> object1Type, Class<?> object2Type,
      GameComponent collidedComponent1,
      GameComponent collidedComponent2, PlayableArea collidedComponent1Area,
      PlayableArea collidedComponent2Area) {
    try {
      String fullClassName = formatInputString(object1Type, object2Type);
      return applyReflection(fullClassName, collidedComponent1, collidedComponent2,
          collidedComponent1Area, collidedComponent2Area);
    } catch (ModelException e) {
      String fullClassName = formatInputString(object2Type, object1Type);
      return applyReflection(fullClassName, collidedComponent2, collidedComponent1,
          collidedComponent1Area, collidedComponent2Area);
    }
  }

  private Collision applyReflection(String fullClassName, GameComponent collidedComponent1,
      GameComponent collidedComponent2, PlayableArea collidedComponent1Area,
      PlayableArea collidedComponent2Area) {
    try {
      //code referenced from https://java2blog.com/invoke-constructor-using-reflection-java/ provided on course website
      Class<?> cl = Class.forName(fullClassName);
      Class<?>[] type = {GameComponent.class, GameComponent.class, PlayableArea.class,
          PlayableArea.class};
      Constructor<?> cons = cl.getConstructor(type);
      Object[] constructorArguments = {collidedComponent1, collidedComponent2,
          collidedComponent1Area, collidedComponent2Area};
      return (Collision) cons.newInstance(constructorArguments);
    } catch (Exception e) {
      throw new ModelException(invalidMessage, e);
    }
  }

  private String formatInputString(Class<?> object1Type, Class<?> object2Type) {
    int indexOfPeriod1 = object1Type.toString().lastIndexOf(".");
    int indexOfPeriod2 = object2Type.toString().lastIndexOf(".");
    String firstObject = object1Type.toString().substring(indexOfPeriod1 + 1);
    String secondObject = object2Type.toString().substring(indexOfPeriod2 + 1);
    String collisionClassNamePrefix = modelProperties.getString(COLLISION_CLASS_NAME_PREFIX);
    String collisionClassNameSuffix = modelProperties.getString(COLLISION_CLASS_NAME_SUFFIX);
    return String.format("%s%s%s%s", collisionClassNamePrefix, firstObject, secondObject,
        collisionClassNameSuffix);
  }
}
