package model.gameComponents.playerGridComponents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import controller.ConfigObjects.TowerConfig;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import model.gameComponents.GameComponent;
import model.gameplay.MVCInteraction.concreteModel.GameStatus;
import model.gameplay.gameplayResources.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SunTowerTest {

  TowerConfig towerConfig;
  int animationSpeed = 20;
  int gridRows = 20;
  int gridCols = 20;


  @BeforeEach
  public void initializeEnemyConfig() throws IOException {
    FileReader f = new FileReader(getFilePath("Sunflower"));
    Properties ep = new Properties();
    ep.load(f);
    towerConfig = new TowerConfig(ep);
  }

  @Test
  void createTowerTest() throws IOException {
    Tower sunflowerTower = new SunTower(towerConfig, gridRows, gridCols, animationSpeed);
    //test initial creation, where it should be alive
    assertTrue(sunflowerTower.isAlive());
    assertEquals(75, sunflowerTower.getHealth());
    assertEquals(50, sunflowerTower.getMyCost());
  }

  @Test
  void createPeashooterTowerBasicActionsTest() {
    Tower sunflowerTower = new SunTower(towerConfig, gridRows, gridCols, animationSpeed);
    int currentRandomXPosition = 5;
    int currentRandomYPosition = 5;
    Position currPos = new Position(currentRandomXPosition, currentRandomYPosition);
    Map<Position, GameComponent> output = new HashMap<>();
    assertEquals(output, sunflowerTower.enactAction(currPos, new GameStatus(20, 20)));
  }


  public String getFilePath(String name) {
    return String.format("src/resources/towers/%s.properties", name);
  }

  private Properties getProperties(String name) throws IOException {
    String propertiesFilePath = getFilePath(name);
    Properties properties = new Properties();
    FileInputStream fileInputStream = new FileInputStream(propertiesFilePath);
    properties.load(fileInputStream);
    return properties;
  }

}