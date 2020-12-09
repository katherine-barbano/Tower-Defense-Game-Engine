package model.gameComponents.EnemyGridComponents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import model.factory.ProjectileFactory;
import model.gameComponents.enemyGridComponents.enemies.Enemy;
import model.gameComponents.enemyGridComponents.projectiles.Projectile;
import model.gameComponents.enemyGridComponents.projectiles.SingleProjectile;
import model.gameComponents.playerGridComponents.Tower;
import model.gameplay.gameplayResources.ModelException;
import org.junit.jupiter.api.Test;

public class ProjectileFactoryTest {

  @Test
  void testSingleProjectileFactoryCreation() {
    int speed = 5;
    int damage = 10;
    int numCols = 50;
    int numRows = 50;
    int xDirection = 1;
    int yDirection = 1;
    ProjectileFactory myFactory = new ProjectileFactory(speed, damage, numRows, numCols,
        xDirection, yDirection, 50, Tower.class);
    Projectile myProjectile = (Projectile) myFactory.createProjectile("Single");
    assertEquals(damage, myProjectile.getDamage());
    assertEquals(speed, myProjectile.getSpeed());
    assertEquals(numCols, myProjectile.getNumberCols());
    assertEquals(numRows, myProjectile.getNumberRows());
  }

  @Test
  void testProjectileFactoryCreation() {
    int speed = 5;
    int damage = 10;
    int numCols = 50;
    int numRows = 50;
    int xDirection = 1;
    int yDirection = 1;
    ProjectileFactory myFactory = new ProjectileFactory(speed, damage, numRows, numCols,
        xDirection, yDirection, 50, Enemy.class);
    Projectile myProjectile = (Projectile) myFactory.createProjectile("ShortRange");
    assertEquals(damage, myProjectile.getDamage());
    assertEquals(speed, myProjectile.getSpeed());
    assertEquals(numCols, myProjectile.getNumberCols());
    assertEquals(numRows, myProjectile.getNumberRows());
  }

  @Test
  void testInvalidProjectileCreationFromFactory() {
    int speed = 5;
    int damage = 10;
    int numCols = 50;
    int numRows = 50;
    int xDirection = 1;
    int yDirection = 1;
    ProjectileFactory myFactory = new ProjectileFactory(speed, damage, numRows, numCols,
        xDirection, yDirection, 50, Tower.class);
    assertThrows(ModelException.class, () ->  myFactory.createProjectile("Invalid"));
  }

}
