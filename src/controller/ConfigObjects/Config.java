package controller.ConfigObjects;

import java.util.Properties;

/**
 * Represents the configuration from a properties source to be passed into Model to reduce Model's
 * dependecy on a specific type of file.
 *
 * @author Megan Richards
 */
public abstract class Config {

  private final Properties myProperties;
  String type;
  String name;
  int score;

  public Config(Properties configProperties) {
    myProperties = configProperties;
    type = configProperties.getProperty("Type");
    name = configProperties.getProperty("Name");
    score = Integer.parseInt(configProperties.getProperty("Score"));
  }

  public int getHealth() {
    return Integer.parseInt(myProperties.getProperty("Health"));
  }

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public int getDamage() {
    return Integer.parseInt(myProperties.getProperty("Damage"));
  }

  public int getScore() {
    return score;
  }

  public int getProjectileSpeed() {
    return Integer.parseInt(myProperties.getProperty("ProjectileSpeed"));
  }


}
