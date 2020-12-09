package controller.ConfigObjects;

import controller.DataAccess.towerDefenseResourceAPI;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * Factory that creates EnemyConfig objects
 *
 * @author Megan Richards
 */
public class EnemyConfigFactory {

  private final towerDefenseResourceAPI resources;

  public EnemyConfigFactory(towerDefenseResourceAPI resourceAPI) {
    resources = resourceAPI;
  }

  public Config makeConfig(String zombie)
      throws NoSuchMethodException, IOException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
    if (zombie.equals("0")) {
      return null;
    } else {
      Class<?> a = Class.forName(resources.getEnemyConfigName("EnemyConfig"));
      Constructor<?> c = a.getConstructor(Properties.class);
      FileReader f = new FileReader(resources.getEnemyPropertiesFilePath(zombie));
      Properties ep = new Properties();
      ep.load(f);
      return (Config) c.newInstance(ep);
    }
  }

}
