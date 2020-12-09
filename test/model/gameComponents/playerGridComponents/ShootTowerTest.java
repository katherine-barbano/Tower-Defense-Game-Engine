package model.gameComponents.playerGridComponents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import controller.ConfigObjects.TowerConfig;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import model.gameComponents.GameComponent;
import model.gameComponents.enemyGridComponents.projectiles.SingleProjectile;
import model.gameplay.MVCInteraction.concreteModel.GameStatus;
import model.gameplay.gameplayResources.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShootTowerTest {

  TowerConfig towerConfig;
  int animationSpeed = 20;
  int gridRows = 20;
  int gridCols = 20;


  @BeforeEach
  public void initializeEnemyConfig() throws IOException {
    FileReader f = new FileReader(getFilePath("Peashooter"));
    Properties ep = new Properties();
    ep.load(f);
    towerConfig = new TowerConfig(ep);
  }

  @Test
  void createPeashooterTowerTest() {
    Tower peashooterTower = new ShootTower(towerConfig, gridRows, gridCols, animationSpeed);
    //test initial creation, where it should be alive
    assertTrue(peashooterTower.isAlive());
    assertEquals(100, peashooterTower.getHealth());
    assertEquals(100, peashooterTower.getMyCost());
  }

  @Test
  void createPeashooterTowerBasicActionsTest() {
    Tower peashooterTower = new ShootTower(towerConfig, gridRows, gridCols, animationSpeed);
    int currentRandomXPosition = 5;
    int currentRandomYPosition = 5;
    Map<Position, GameComponent> output = peashooterTower
        .enactAction(new Position(currentRandomXPosition,
            currentRandomYPosition), new GameStatus(animationSpeed, 150));
    for (Position p : output.keySet()) {
      assertEquals(SingleProjectile.class, output.get(p).getClass());
    }
  }


  public String getFilePath(String name) {
    return String.format("src/resources/towers/%s.properties", name);
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