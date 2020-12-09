package controller.ConfigObjects;

import java.util.Properties;

/**
 * Defines further functionality for properties that are specific to an enemy
 *
 * @author Megan Richards
 */
public class EnemyConfig extends Config {

  private final Properties myProperties;
  int speed;
  int damage;
  int rowSize;
  int colSize;

  public EnemyConfig(Properties properties) {
    super(properties);
    myProperties = properties;
    speed = Integer.parseInt(properties.getProperty("Speed"));
    damage = Integer.parseInt(properties.getProperty("Damage"));
  }

  public int getRowSize() {
    return rowSize;
  }

  public void setRowSize(int gridRows) {
    rowSize = gridRows;
  }

  public int getColSize() {
    return colSize;
  }

  public void setColSize(int gridColumns) {
    colSize = gridColumns;
  }

  public int getSpeed() {
    return speed;
  }

  public int getDamage() {
    return damage;
  }


  public int getRate() {
    return Integer.parseInt(myProperties.getProperty("Rate"));
  }

  public int getXDirection() {
    return Integer.parseInt(myProperties.getProperty("XDirection"));
  }

  public int getYDirection() {
    return Integer.parseInt(myProperties.getProperty("YDirection"));
  }

  public String getProjectileType() {
    return myProperties.getProperty("ProjectileType");
  }
}
