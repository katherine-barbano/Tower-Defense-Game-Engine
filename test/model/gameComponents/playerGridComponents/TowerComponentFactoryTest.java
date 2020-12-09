package model.gameComponents.playerGridComponents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import controller.ConfigObjects.TowerConfig;
import java.io.IOException;
import java.util.Properties;
import model.factory.TowerComponentFactory;
import model.gameplay.MVCInteraction.concreteModel.GameStatus;
import org.junit.jupiter.api.Test;

class TowerComponentFactoryTest {

  @Test
  void createSunFlowerFromFactoryTest() {
    TowerComponentFactory testFactory = new TowerComponentFactory(5, 5, 50, new GameStatus(5, 150));
    TowerConfig config = new TowerConfig(getProperties("Sunflower"));
    SunTower sunflower = (SunTower) testFactory.createGameComponent(config);
    assertEquals(50, sunflower.getMyCost());
  }

  @Test
  void createPeaShooterFromFactoryTest() {
    TowerComponentFactory testFactory = new TowerComponentFactory(5, 5, 50, new GameStatus(5, 150));
    TowerConfig config = new TowerConfig(getProperties("Peashooter"));
    ShootTower sunflower = (ShootTower) testFactory.createGameComponent(config);
    assertEquals(100, sunflower.getMyCost());

  }

  @Test
  void createPlantPropertiesNotFound() {
    TowerComponentFactory testFactory = new TowerComponentFactory(50, 50, 50,
        new GameStatus(5, 150));
    assertThrows(NullPointerException.class, () -> new TowerConfig(getProperties("Invalid")));
  }


  public String getFilePath(String name) {
    return String.format("resources/towers/%s.properties", name);
  }

  private Properties getProperties(String name) {
    String propertiesFilePath = getFilePath(name);
    Properties properties = new Properties();
    try {
      properties
          .load(TowerComponentFactoryTest.class.getClassLoader()
              .getResourceAsStream(propertiesFilePath));
      return properties;
    } catch (NullPointerException | IOException e) {
    }
    return null;
  }


}