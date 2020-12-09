package model.gameComponents.EnemyGridComponents.enemies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import controller.ConfigObjects.EnemyConfig;
import controller.ConfigObjects.TowerConfig;
import controller.DataAccess.ResourceAccessor;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import model.gameComponents.GameComponent;
import model.gameComponents.enemyGridComponents.enemies.Enemy;
import model.gameComponents.enemyGridComponents.enemies.ShootEnemy;
import model.gameComponents.enemyGridComponents.projectiles.SingleProjectile;
import model.gameComponents.playerGridComponents.ShootTower;
import model.gameplay.MVCInteraction.concreteModel.GameStatus;
import model.gameplay.gameplayResources.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShootEnemyTest {

  ResourceAccessor resources = new ResourceAccessor();
  EnemyConfig enemyConfig;
  TowerConfig towerConfig;
  int animationSpeed = 20;
  int gridRows = 20;
  int gridCols = 20;
  GameStatus testStatus = new GameStatus(20, 10);

  @BeforeEach
  public void initializeEnemyConfig() throws IOException {
    FileReader f = new FileReader(resources.getEnemyPropertiesFilePath("ShooterEnemy"));
    Properties ep = new Properties();
    ep.load(f);
    enemyConfig = new EnemyConfig(ep);

    FileReader towerReader = new FileReader("src/resources/towers/Peashooter.properties");
    Properties towerProperties = new Properties();
    towerProperties.load(towerReader);
    towerConfig = new TowerConfig(towerProperties);

  }


  @Test
  void createShooterZombieTest() {
    //The shoot zombie properties file currently calls for a creation of a shooter zombie
    Enemy shootZombie = new ShootEnemy(enemyConfig, gridRows, gridCols, animationSpeed, testStatus);
    //test initial creation, where it should be alive
    assertTrue(shootZombie.isAlive());
    assertFalse(shootZombie.isOutOfRange());
  }

  @Test
  void shooterZombieBasicFunctionalityTest() {
    Enemy shootZombie = new ShootEnemy(enemyConfig, gridRows, gridCols, animationSpeed, testStatus);
    assertEquals(5, shootZombie.getDamage());
    assertEquals("ShooterEnemy", shootZombie.getName());
    assertEquals(5, shootZombie.getScore());
    assertEquals(animationSpeed * 3 / 4, shootZombie.getWidth());
    assertEquals(animationSpeed, shootZombie.getHeight());
  }

  @Test
  void shootZombieEnactActionTest() {
    ShootEnemy shooterZombie = new ShootEnemy(enemyConfig, gridRows, gridCols, animationSpeed,
        testStatus);
    int currentRandomXPosition = 5;
    int currentRandomYPosition = 5;
    //not able to perform action initially
    assertFalse(shooterZombie.checkAction());
    Map<Position, GameComponent> range = new HashMap<>();
    range.put(new Position(5, 7), new ShootTower(towerConfig, 20, 20, 20));
    shooterZombie.setRange(range);
    shooterZombie.setStepsBeforeNextAction(1);
    assertTrue(shooterZombie.checkAction());
    Map<Position, GameComponent> newComponent = new HashMap<>();
    Position nextPosition = new Position(5, 4);
    newComponent.put(nextPosition,
        new SingleProjectile(enemyConfig.getProjectileSpeed(), enemyConfig.getDamage(),
            gridRows, gridCols, enemyConfig.getXDirection(), enemyConfig.getYDirection(),
            animationSpeed, Enemy.class));
    Map<Position, GameComponent> expectedComponent = shooterZombie
        .enactAction(new Position(currentRandomXPosition,
            currentRandomYPosition), new GameStatus(animationSpeed, 10));
    for (Position p : expectedComponent.keySet()) {
      assertEquals(SingleProjectile.class, expectedComponent.get(p).getClass());
    }

  }

}
