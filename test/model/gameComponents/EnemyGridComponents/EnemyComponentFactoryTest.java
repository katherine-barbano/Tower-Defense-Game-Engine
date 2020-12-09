package model.gameComponents.EnemyGridComponents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import controller.ConfigObjects.EnemyConfig;
import controller.DataAccess.ResourceAccessor;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import model.factory.EnemyComponentFactory;
import model.gameComponents.enemyGridComponents.enemies.ForwardEnemy;
import model.gameComponents.enemyGridComponents.enemies.ShootEnemy;
import model.gameComponents.enemyGridComponents.enemies.SingleUseEnemy;
import model.gameplay.MVCInteraction.concreteModel.GameStatus;
import org.junit.jupiter.api.Test;

public class EnemyComponentFactoryTest {

  ResourceAccessor resources = new ResourceAccessor();

  @Test
  void createForwardEnemyUsingFactoryTest() throws IOException {
    EnemyComponentFactory testFactory = new EnemyComponentFactory(5, 5, 50, new GameStatus(20,

        20));
    FileReader f = new FileReader(resources.getEnemyPropertiesFilePath("BasicEnemy"));
    Properties ep = new Properties();
    ep.load(f);
    EnemyConfig enemyConfig = new EnemyConfig(ep);
    ForwardEnemy basicZombie = (ForwardEnemy) testFactory.createGameComponent(enemyConfig);
    //Starting health
    assertEquals(50, basicZombie.getHealth());
  }


  @Test
  void createBombEnemyThroughFactoryTest() throws IOException {
    EnemyComponentFactory testFactory = new EnemyComponentFactory(5, 5, 50, new GameStatus(20,

        20));
    FileReader f = new FileReader(resources.getEnemyPropertiesFilePath("BombEnemy"));
    Properties ep = new Properties();
    ep.load(f);
    EnemyConfig enemyConfig = new EnemyConfig(ep);
    SingleUseEnemy bombZombie = (SingleUseEnemy) testFactory.createGameComponent(enemyConfig);
    //Starting health
    assertEquals(200, bombZombie.getHealth());
  }

  @Test
  void createShooterEnemyThroughFactoryTest() throws IOException {
    EnemyComponentFactory testFactory = new EnemyComponentFactory(5, 5, 50, new GameStatus(20,
        20));
    FileReader f = new FileReader(resources.getEnemyPropertiesFilePath("ShooterEnemy"));
    Properties ep = new Properties();
    ep.load(f);
    EnemyConfig enemyConfig = new EnemyConfig(ep);
    ShootEnemy shootEnemy = (ShootEnemy) testFactory.createGameComponent(enemyConfig);
    //Starting health
    assertEquals(200, shootEnemy.getHealth());
  }


  @Test
  void invalidEnemyNameTest() throws IOException {

    assertThrows(NullPointerException.class, () -> new EnemyConfig(getProperties("invalid")));
  }

  public String getFilePath(String name) {
    return String.format("resources/enemies/%sEnemy.properties", name);
  }

  private Properties getProperties(String name) {
    String propertiesFilePath = getFilePath(name);
    Properties properties = new Properties();
    try {
      properties
          .load(EnemyComponentFactoryTest.class.getClassLoader()
              .getResourceAsStream(propertiesFilePath));
      return properties;
    } catch (NullPointerException | IOException e) {
    }
    return null;
  }
}
