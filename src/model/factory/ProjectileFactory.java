package model.factory;

import controller.Alerts;
import java.lang.reflect.Constructor;
import model.gameComponents.GameComponent;
import model.gameplay.gameplayResources.ModelException;

/**
 * @author Priya Rathinavelu
 * The purpose of this class is to utilize the factory design pattern to help simplify instantiating
 * different objects, specifically projectile. This class depends on the general Projectile class
 * because its main purpose is to be able to create the different types of projectiles we have
 * implemented. This class can be used by creating an instance of this projectile factory that
 * stores the information specific to a certain type of projectile. Then, the createProjectile method
 * will be most useful for being able to create several instances of that same projectile.
 */

public class ProjectileFactory {

  private final int numRows;
  private final int numCols;
  private final int speed;
  private final int damage;
  private final int xDirectionSpeed;
  private final int yDirectionSpeed;
  private final int animationSpeed;
  private final Class shooterType;
  private final Alerts alerts = new Alerts();

  public ProjectileFactory(int projectileSpeed, int projectileDamage, int numberRowsGrid,
      int numberColsGrid, int
      xDirection, int yDirection, int animationSpeed, Class componentShotFrom) {
    numRows = numberRowsGrid;
    numCols = numberColsGrid;
    speed = projectileSpeed;
    damage = projectileDamage;
    xDirectionSpeed = xDirection;
    yDirectionSpeed = yDirection;
    this.animationSpeed = animationSpeed;
    this.shooterType = componentShotFrom;
  }

  //name = type "Single", width/height - size of the projectile

  /**
   * The purpose of this method is to be able to actually create the projectile object based on the
   * specifications indicated when creating the projectile factory
   * @param type a string representing which type of projectile should be implemented (such as "Single" of "Half")
   * @return a game component, specifically a projectile based on the input parameters
   */
  public GameComponent createProjectile(String type) {
    try {
      //code referenced from https://java2blog.com/invoke-constructor-using-reflection-java/ provided on course website
      // Properties properties = getProperties(name);
      String fullClassName = getClassName(type);
      Class<?> cl = Class.forName(fullClassName);
      Class<?>[] types = {int.class, int.class, int.class, int.class, int.class, int.class,
          int.class, Class.class};
      Constructor<?> cons = cl.getConstructor(types);
      Object[] constructorArguments = {speed, damage, numRows, numCols, xDirectionSpeed,
          yDirectionSpeed, animationSpeed, shooterType};
      return (GameComponent) cons.newInstance(constructorArguments);
    } catch (Exception e) {
      throw new ModelException("Subclass not correct for reflection");
    }
  }

  /**
   * This method is used for getting the specific class name based on the type of projectile neeeded
   * @param type a string representing the projectile type
   * @return a string with the full class path
   */
  public String getClassName(String type) {
    return String.format("model.gameComponents.enemyGridComponents.projectiles.%sProjectile", type);
  }
}
