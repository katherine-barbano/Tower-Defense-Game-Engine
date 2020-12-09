package model.gameComponents.EnemyGridComponents.projectiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import model.gameComponents.enemyGridComponents.projectiles.Projectile;
import model.gameComponents.enemyGridComponents.projectiles.ShortRangeProjectile;
import model.gameComponents.playerGridComponents.Tower;
import model.gameplay.gameplayResources.Position;
import org.junit.jupiter.api.Test;

class ShortRangeProjectileTest {

  @Test
  void createHalfProjectileTest() {
    int speed = 5;
    int damage = 10;
    int row = 10;
    int col = 10;
    int xDir = 0;
    int yDir = 1;
    Projectile projectile = new ShortRangeProjectile(speed, damage, row, col, xDir, yDir, 50,
        Tower.class);
    assertEquals(speed, projectile.getSpeed());
    assertEquals(damage, projectile.getDamage());
    assertEquals(row, projectile.getNumberRows());
    assertEquals(col, projectile.getNumberCols());
  }

  @Test
  void halfProjectileMovementTest() {
    int speed = 1;
    int damage = 10;
    int row = 10;
    int col = 10;
    int xDir = 1;
    int yDir = 0;
    Projectile projectile = new ShortRangeProjectile(speed, damage, row, col, xDir, yDir, 50,
        Tower.class);
    Set<Position> openStates = new HashSet<>();
    openStates.add(new Position(2, 3));
    openStates.add(new Position(2, 4));
    assertEquals(new Position(2, 2), projectile.getNextPosition(new Position(2, 2), openStates));
  }
}