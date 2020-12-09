package model.gameComponents.EnemyGridComponents.enemies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import controller.ConfigObjects.EnemyConfig;
import controller.DataAccess.ResourceAccessor;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import model.gameComponents.enemyGridComponents.enemies.Enemy;
import model.gameComponents.enemyGridComponents.enemies.ForwardEnemy;
import model.gameplay.MVCInteraction.concreteModel.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ForwardEnemyTest {

  ResourceAccessor resources = new ResourceAccessor();
  EnemyConfig enemyConfig;
  int animationSpeed = 20;
  int gridRows = 20;
  int gridCols = 20;
  GameStatus testStatus = new GameStatus(20, 10);


  @BeforeEach
  public void initializeEnemyConfig() throws IOException {
    FileReader f = new FileReader(resources.getEnemyPropertiesFilePath("BasicEnemy"));
    Properties ep = new Properties();
    ep.load(f);
    enemyConfig = new EnemyConfig(ep);
  }


  @Test
  void createForwardZombieTest() {
    //The basic zombie properties file currently calls for a creation of a forward zombie
    Enemy basicZombie = new ForwardEnemy(enemyConfig, gridRows, gridCols, animationSpeed,
        testStatus);
    //test initial creation, where it should be alive
    assertTrue(basicZombie.isAlive());
    assertFalse(basicZombie.isOutOfRange());
  }

  @Test
  void forwardZombieMeasurementTest() {
    Enemy basicZombie = new ForwardEnemy(enemyConfig, gridRows, gridCols, animationSpeed,
        testStatus);
    assertEquals(animationSpeed, basicZombie.getHeight());
    assertEquals((animationSpeed / 4) * 3, basicZombie.getWidth());
  }

  @Test
  void forwardZombieEnemyPropertiesTest() {
    Enemy basicEnemy = new ForwardEnemy(enemyConfig, gridRows, gridCols, animationSpeed,
        testStatus);
    assertEquals("Enemy", basicEnemy.toString());
    assertEquals("BasicEnemy", basicEnemy.getName());
  }


}
