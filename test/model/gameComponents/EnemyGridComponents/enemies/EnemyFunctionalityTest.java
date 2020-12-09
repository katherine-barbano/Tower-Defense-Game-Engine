package model.gameComponents.EnemyGridComponents.enemies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import controller.ConfigObjects.EnemyConfig;
import controller.DataAccess.ResourceAccessor;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import model.gameComponents.enemyGridComponents.enemies.Enemy;
import model.gameComponents.enemyGridComponents.enemies.ForwardEnemy;
import model.gameComponents.enemyGridComponents.projectiles.Projectile;
import model.gameComponents.enemyGridComponents.projectiles.SingleProjectile;
import model.gameplay.MVCInteraction.concreteModel.GameStatus;
import model.gameplay.gameplayResources.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EnemyFunctionalityTest {

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
  void testHealthPercentage() {
    Enemy basicZombie = new ForwardEnemy(enemyConfig, gridRows, gridCols, animationSpeed,
        testStatus);
    assertEquals(1.0, basicZombie.getPercentageHealth());
  }

  @Test
  void testHealthDropAfterDamage() {
    Enemy basicZombie = new ForwardEnemy(enemyConfig, gridRows, gridCols, animationSpeed,
        testStatus);
    int startingHealth = basicZombie.getHealth();
    Projectile proj = new SingleProjectile(20, 10, 10, 10, 10
        - 1, 0, 20, Enemy.class);
    basicZombie.updateHealth(proj);
    assertTrue(basicZombie.getHealth() < startingHealth);
  }

  @Test
  void testHealthPercentageLessThanOne() {
    Enemy basicZombie = new ForwardEnemy(enemyConfig, gridRows, gridCols, animationSpeed,
        testStatus);
    double startingHealthPercentage = basicZombie.getPercentageHealth();
    Projectile proj = new SingleProjectile(20, 10, 10, 10, 10
        - 1, 0, 20, Enemy.class);
    basicZombie.updateHealth(proj);
    assertTrue(basicZombie.getPercentageHealth() < startingHealthPercentage);
  }

  @Test
  void zombiePathOneDirectionMovementTest() throws IOException {
    ForwardEnemy basicZombie = new ForwardEnemy(enemyConfig, gridRows, gridCols, animationSpeed,
        testStatus);
    Position current = new Position(15, 15);
    Set<Position> openSpots = new HashSet<>();
    openSpots.add(new Position(15, 14));
    assertEquals(new Position(15, 14), basicZombie.getNextPosition(current, openSpots));
  }

  @Test
  void zombiePathTurnMovement() throws IOException {
    ForwardEnemy basicZombie = new ForwardEnemy(enemyConfig, gridRows, gridCols, animationSpeed,
        testStatus);
    Position current = new Position(15, 15);
    Set<Position> openSpots = new HashSet<>();
    //should only contain states that would not keep the same direction of the zombie
    openSpots.add(new Position(3, 18));
    openSpots.add(new Position(14, 15));
    openSpots.add(new Position(15, 15));
    openSpots.add(new Position(15, 16));
    assertEquals(new Position(14, 15), basicZombie.getNextPosition(current, openSpots));
  }

  @Test
  void zombiePathTwoTurnMovement() {
    ForwardEnemy basicZombie = new ForwardEnemy(enemyConfig, gridRows, gridCols, animationSpeed,
        testStatus);
    Position current = new Position(1, 4);
    Set<Position> openSpots = new HashSet<>();
    openSpots.add(new Position(1, 3));
    openSpots.add(new Position(1, 2));
    openSpots.add(new Position(2, 2));
    openSpots.add(new Position(3, 2));
    openSpots.add(new Position(3, 1));
    openSpots.add(new Position(3, 0));
    Position first = basicZombie.getNextPosition(current, openSpots);
    assertEquals(new Position(1, 3), first);
    Position second = basicZombie.getNextPosition(first, openSpots);
    assertEquals(new Position(1, 2), second);
    Position third = basicZombie.getNextPosition(second, openSpots);
    assertEquals(new Position(2, 2), third);
    Position fourth = basicZombie.getNextPosition(third, openSpots);
    assertEquals(new Position(3, 2), fourth);
    Position fifth = basicZombie.getNextPosition(fourth, openSpots);
    assertEquals(new Position(3, 1), fifth);
    Position sixth = basicZombie.getNextPosition(fifth, openSpots);
    assertEquals(new Position(3, 0), sixth);
  }
}
