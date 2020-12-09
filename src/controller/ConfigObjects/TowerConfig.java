package controller.ConfigObjects;

import java.util.Properties;

/**
 * Defines further functionality for properties that are specific to a tower
 *
 * @author Megan Richards
 */
public class TowerConfig extends Config {

  // TODO: 2020-11-02  records ?
  int cost;

  Properties myProperties;

  public TowerConfig(Properties properties) {
    super(properties);
    myProperties = properties;
    cost = Integer.parseInt(properties.getProperty("Cost"));
  }

  public int getSun() {
    return Integer.parseInt(myProperties.getProperty("Sun"));
  }

  public int getCost() {
    return this.cost;
  }

  public int getRate() {
    return Integer.parseInt(myProperties.getProperty("Rate"));
  }

  public String getProjectileType() {
    return myProperties.getProperty("ProjectileType");
  }

  public int getXDirection() {
    return Integer.parseInt(myProperties.getProperty("XDirection"));
  }

  public int getYDirection() {
    return Integer.parseInt(myProperties.getProperty("YDirection"));
  }

  public int getDamage() {
    return Integer.parseInt(myProperties.getProperty("Damage"));
  }

  public int getProjectileSpeed() {
    return Integer.parseInt(myProperties.getProperty("ProjectileSpeed"));
  }
}
