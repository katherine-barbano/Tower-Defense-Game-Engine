package model.gameComponents.EnemyGridComponents.projectiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import model.gameComponents.enemyGridComponents.projectiles.Projectile;
import model.gameComponents.enemyGridComponents.projectiles.SingleProjectile;
import model.gameComponents.playerGridComponents.Tower;
import model.gameplay.gameplayResources.Position;
import org.junit.jupiter.api.Test;

public class SingleProjectileTest {

  @Test
  void createProjectileTest() {
    int speed = 5;
    int damage = 10;
    int row = 10;
    int col = 10;
    int xDir = 0;
    int yDir = 1;
    Projectile projectile = new SingleProjectile(speed, damage, row, col, xDir, yDir, 50,
        Tower.class);
    assertEquals(speed, projectile.getSpeed());
    assertEquals(damage, projectile.getDamage());
    assertEquals(row, projectile.getNumberRows());
    assertEquals(col, projectile.getNumberCols());
  }


  @Test
  void projectileNotMovePositionTest() {
    int speed = 5;
    int damage = 10;
    int row = 10;
    int col = 10;
    int xDir = 0;
    int yDir = 1;
    Set<Position> openStates = new HashSet<>();
    Projectile projectile = new SingleProjectile(speed, damage, row, col, xDir, yDir, 50,
        Tower.class);
    Position currentPos = new Position(10, 10);
    //shouldn't move because no open states around
    Position nextPos = projectile.getNextPosition(currentPos, openStates);
    assertEquals(currentPos.getRow(), nextPos.getRow());
    assertEquals(nextPos.getColumn(), currentPos.getColumn());
  }

  @Test
  void projectileMoveAlongRowSuccessfullyTest() {
    int speed = 2;
    int damage = 10;
    int row = 10;
    int col = 10;
    int xDir = 0;
    int yDir = 1;
    Set<Position> openStates = new HashSet<>();
    Projectile projectile = new SingleProjectile(speed, damage, row, col, xDir, yDir, 1,
        Tower.class);
    Position currentPos = new Position(5, 5);
    Position possibleState1 = new Position(7, 5);
    Position possibleState2 = new Position(9, 4);
    openStates.add(possibleState1);
    openStates.add(possibleState2);
    Position nextPos = projectile.getNextPosition(currentPos, openStates);
    assertEquals(currentPos.getColumn(), nextPos.getColumn());
    //check row has increased
    assertEquals(currentPos.getRow() + speed, nextPos.getRow());
  }

  @Test
  void projectileMoveAlongColumnSuccessfullyTest() {
    int speed = 2;
    int damage = 10;
    int row = 10;
    int col = 10;
    int xDir = 1;
    int yDir = 0;
    Set<Position> openStates = new HashSet<>();
    Projectile projectile = new SingleProjectile(speed, damage, row, col, xDir, yDir, 1,
        Tower.class);
    Position currentPos = new Position(5, 5);
    Position possibleState1 = new Position(5, 7);
    Position possibleState2 = new Position(9, 4);
    openStates.add(possibleState1);
    openStates.add(possibleState2);
    Position nextPos = projectile.getNextPosition(currentPos, openStates);
    assertEquals(currentPos.getColumn() + speed, nextPos.getColumn());
  }

  @Test
  void nextPositionNotOpenTest() {
    int speed = 2;
    int damage = 10;
    int row = 10;
    int col = 10;
    int xDir = 1;
    int yDir = 1;
    Set<Position> openStates = new HashSet<>();
    Projectile projectile = new SingleProjectile(speed, damage, row, col, xDir, yDir, 50,
        Tower.class);
    Position currentPos = new Position(5, 5);
    Position possibleState1 = new Position(5, 8);
    Position possibleState2 = new Position(7, 4);
    Position possibleState3 = new Position(4, 7);
    Position possibleState4 = new Position(8, 5);
    openStates.add(possibleState1);
    openStates.add(possibleState2);
    openStates.add(possibleState3);
    openStates.add(possibleState4);
    Position nextPos = projectile.getNextPosition(currentPos, openStates);
    //should not move because next position not within open states
    assertEquals(currentPos.getRow(), nextPos.getRow());
    assertEquals(nextPos.getColumn(), currentPos.getColumn());
  }

  @Test
  void nextPositionOutOfBounds() {
    int speed = 2;
    int damage = 10;
    int row = 10;
    int col = 10;
    int xDir = 1;
    int yDir = 1;
    Set<Position> openStates = new HashSet<>();
    Projectile projectile = new SingleProjectile(speed, damage, row, col, xDir, yDir, 20,
        Tower.class);
    //initially in range
    assertFalse(projectile.isOutOfRange());
    Position currentPos = new Position(7, 9);
    Position possibleState1 = new Position(5, 8);
    Position possibleState2 = new Position(7, 4);
    openStates.add(possibleState1);
    openStates.add(possibleState2);
    projectile.getNextPosition(currentPos, openStates);
    //after moving, projectile's out of range should be true
    assertTrue(projectile.isOutOfRange());
  }


}
