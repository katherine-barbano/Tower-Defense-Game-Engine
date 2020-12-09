package model.gameComponents.EnemyGridComponents.projectiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import controller.ConfigObjects.EnemyConfig;
import controller.DataAccess.ResourceAccessor;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import model.gameComponents.Fire;
import model.gameComponents.enemyGridComponents.enemies.Enemy;
import model.gameComponents.enemyGridComponents.enemies.SingleUseEnemy;
import model.gameplay.MVCInteraction.concreteModel.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FireStateTest {

  ResourceAccessor resources = new ResourceAccessor();
  EnemyConfig myEnemyConfig;
  int gridRows = 20;
  int gridCols = 20;

  @BeforeEach
  public void initializeEnemyConfig() throws IOException {
    FileReader f = new FileReader(resources.getEnemyPropertiesFilePath("BasicEnemy"));
    Properties ep = new Properties();
    ep.load(f);
    myEnemyConfig = new EnemyConfig(ep);
  }

  @Test
  void fireStateInitialCreationTest() throws ClassNotFoundException {
    int animationSpeed = 20;
    SingleUseEnemy bombZombie = new SingleUseEnemy(myEnemyConfig, gridRows, gridCols, 20,
        new GameStatus(20, 50));
    Fire basicFire = new Fire(animationSpeed, bombZombie, Enemy.class);
    //basic set up of fire state
    assertEquals(Enemy.class, basicFire.getShooterClass());
    assertEquals(animationSpeed, basicFire.getHeight());
    assertEquals(animationSpeed, basicFire.getWidth());
  }

}