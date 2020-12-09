package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import controller.ConfigObjects.EnemyConfig;
import controller.ConfigObjects.TowerConfig;
import controller.ExternalAccess.ConfigAPI;
import controller.ExternalAccess.ConfigAccess;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import model.gameComponents.GameComponent;
import model.gameComponents.SingleState;
import model.gameComponents.enemyGridComponents.enemies.Enemy;
import model.gameComponents.enemyGridComponents.enemies.ForwardEnemy;
import model.gameComponents.enemyGridComponents.projectiles.Projectile;
import model.gameComponents.enemyGridComponents.projectiles.SingleProjectile;
import model.gameComponents.playerGridComponents.ShootTower;
import model.gameComponents.playerGridComponents.Tower;
import model.gameplay.MVCInteraction.concreteModel.GameStatus;
import model.gameplay.gameplayResources.ModelException;
import model.gameplay.gameplayResources.Position;
import model.gameplay.playableArea.IIterator;
import model.gameplay.playableArea.PlayableArea;
import model.gameplay.playableArea.PlayableAreaData;
import org.junit.jupiter.api.Test;

public class PlayableAreaTest {

  @Test
  void createPlayableAreaData() {
    PlayableArea playableArea = new PlayableAreaData(4, 6);
    assertEquals(4, playableArea.getNumberOfRows());
    assertEquals(6, playableArea.getNumberOfColumns());
  }

  @Test
  void createPlayableAreaDataNegativeSize() {
    PlayableArea playableArea = new PlayableAreaData(-1, 0);
    assertEquals(-1, playableArea.getNumberOfRows());
    assertEquals(0, playableArea.getNumberOfColumns());
  }

  @Test
  void initializeGameComponentDataFromBoardDifferentSizes() {
    PlayableArea playableArea = new PlayableAreaData(2, 3);
    List<List<GameComponent>> newBoard = new ArrayList<>();
    for (int row = 0; row < 2; row++) {
      List<GameComponent> rowList = new ArrayList<>();
      for (int column = 0; column < 2; column++) {
        rowList.add(SingleState.BLOCKED);
      }
      newBoard.add(rowList);
    }
    assertThrows(ModelException.class,
        () -> playableArea.initializeGameComponentDataFromBoard(newBoard));
  }

  @Test
  void initializeGameComponentDataFromBoardSameSizes() {
    PlayableArea playableArea = new PlayableAreaData(2, 3);
    List<List<GameComponent>> newBoard = new ArrayList<>();
    for (int row = 0; row < 2; row++) {
      List<GameComponent> rowList = new ArrayList<>();
      for (int column = 0; column < 3; column++) {
        rowList.add(SingleState.BLOCKED);
      }
      newBoard.add(rowList);
    }
    playableArea.initializeGameComponentDataFromBoard(newBoard);
    IIterator<GameComponent> areaIterator = playableArea.createTypeIterator(SingleState.class);
    for (int index = 0; index < 6; index++) {
      assertEquals(areaIterator.next(), SingleState.BLOCKED);
    }
    assertFalse(areaIterator.hasNext());
  }

  @Test
  void getRowRangeToRightTest() {
    PlayableArea playableArea = new PlayableAreaData(2, 5);
    playableArea.setGameComponentAtPosition(new Position(1, 1), getNewTower());
    playableArea.setGameComponentAtPosition(new Position(1, 4), getNewEnemy());
    Map<Position, GameComponent> range = playableArea
        .getRowRangeToRight(new Position(1, 1), Enemy.class);
  }

  @Test
  void getRowRangeToLeftTest() {

  }

  private GameComponent getNewTower() {
    try {
      ConfigAPI configFile = new ConfigAccess();
      return new ShootTower((TowerConfig) configFile.getTowerConfig("Peashooter"), 50, 50, 50);
    } catch (Exception e) {
      assert (false);
    }
    return null;
  }

  private GameComponent getNewEnemy() {
    try {
      Properties ep = new Properties();
      FileReader f = new FileReader("doc/plan/data/simplegame/zombies/BasicZombie.properties");
      ep.load(f);

      return new ForwardEnemy(new EnemyConfig(ep), 30, 30, 50, new GameStatus(20, 20));

    } catch (Exception e) {
      assert (false);
    }
    return null;
  }


  @Test
  void setGameComponentAtPositionEmptyComponent() {
    PlayableArea playableArea = new PlayableAreaData(2, 3);
    Position position = new Position(1, 2);
    GameComponent projectile = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    playableArea.setGameComponentAtPosition(position, projectile);
    GameComponent retrieved = playableArea.getGameComponentAtPosition(position);
    assertEquals(projectile, retrieved);
  }

  @Test
  void setGameComponentAtPositionNonEmptyComponent() {
    PlayableArea playableArea = new PlayableAreaData(2, 3);
    Position position = new Position(0, 0);
    playableArea.setGameComponentAtPosition(position, new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class));
    assertFalse(
        playableArea.setGameComponentAtPosition(position, new SingleProjectile(10, 10, 10, 10,
            1, 0, 20, Tower.class)));
  }

  @Test
  void setGameComponentAtPositionIndexOutOfRange() {
    PlayableArea playableArea = new PlayableAreaData(2, 3);
    Position position = new Position(-1, 2);
    assertThrows(ModelException.class,
        () -> playableArea.setGameComponentAtPosition(position, new SingleProjectile(10, 10, 10, 10,
            1, 0, 20, Tower.class)));

    Position position2 = new Position(0, 3);
    assertThrows(ModelException.class,
        () -> playableArea
            .setGameComponentAtPosition(position2, new SingleProjectile(10, 10, 10, 10,
                1, 0, 20, Tower.class)));
  }

  @Test
  void getGameComponentAtPositionExists() {
    PlayableArea playableArea = new PlayableAreaData(2, 3);
    Position position = new Position(1, 2);
    GameComponent projectile = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    playableArea.setGameComponentAtPosition(position, projectile);
    Position position2 = new Position(1, 2);
    GameComponent retrieved = playableArea.getGameComponentAtPosition(position2);
    assertEquals(retrieved, projectile);
  }

  @Test
  void getGameComponentAtDifferentPosition() {
    PlayableArea playableArea = new PlayableAreaData(2, 3);
    Position position = new Position(1, 2);
    GameComponent projectile = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    playableArea.setGameComponentAtPosition(position, projectile);
    Position position2 = new Position(1, 1);
    GameComponent retrieved = playableArea.getGameComponentAtPosition(position2);
    assertEquals(retrieved, SingleState.EMPTY);
  }

  @Test
  void getPositionOfGameComponentExists() {
    PlayableArea playableArea = new PlayableAreaData(2, 3);
    Position position = new Position(1, 2);
    GameComponent projectile = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    playableArea.setGameComponentAtPosition(position, projectile);
    Position retrieved = playableArea.getPositionOfGameComponent(projectile);
    assertEquals(position, retrieved);
  }

  @Test
  void getPositionOfGameComponentDoesNotExist() {
    PlayableArea playableArea = new PlayableAreaData(2, 3);
    GameComponent projectile = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    assertThrows(ModelException.class,
        () -> playableArea.getPositionOfGameComponent(projectile));
  }

  @Test
  void getPositionsOfAllEmptySpotsMixedEmptyFull() {
    PlayableArea playableArea = new PlayableAreaData(2, 3);
    for (int row = 0; row < 2; row++) {
      for (int column = 0; column < 2; column++) {
        Position position = new Position(row, column);
        GameComponent projectile = new SingleProjectile(10, 10, 10, 10,
            1, 0, 20, Tower.class);
        playableArea.setGameComponentAtPosition(position, projectile);
      }
    }
    Position position = new Position(1, 2);
    GameComponent projectile = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    playableArea.setGameComponentAtPosition(position, projectile);
    Set<Position> emptySpots = playableArea.getPositionsOfAllEmptySpots();
    assertEquals(1, emptySpots.size());
    boolean containsRowAndColumn = false;
    for (Position positionInEmpty : emptySpots) {
      int row = positionInEmpty.getRow();
      int column = positionInEmpty.getColumn();
      if (row == 0 && column == 0) {
        containsRowAndColumn = true;
      }
    }
    assertFalse(containsRowAndColumn);
  }

  @Test
  void getPositionsOfAllEmptySpotsNoEmpty() {
    PlayableArea playableArea = new PlayableAreaData(2, 3);
    for (int row = 0; row < 2; row++) {
      for (int column = 0; column < 3; column++) {
        Position position = new Position(row, column);
        GameComponent projectile = new SingleProjectile(10, 10, 10, 10,
            1, 0, 20, Tower.class);
        playableArea.setGameComponentAtPosition(position, projectile);
      }
    }
    Set<Position> emptySpots = playableArea.getPositionsOfAllEmptySpots();
    assertEquals(0, emptySpots.size());
  }

  @Test
  void removeGameComponentExists() {
    PlayableArea playableArea = new PlayableAreaData(2, 3);
    Position position = new Position(1, 2);
    GameComponent projectile = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    playableArea.setGameComponentAtPosition(position, projectile);
    playableArea.removeGameComponent(projectile);
    GameComponent retrieved = playableArea.getGameComponentAtPosition(position);
    assertEquals(SingleState.EMPTY, retrieved);
  }

  @Test
  void removeGameComponentThatDoesNotExist() {
    PlayableArea playableArea = new PlayableAreaData(2, 3);
    Position position = new Position(1, 2);
    GameComponent projectile = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    assertThrows(ModelException.class,
        () -> playableArea.removeGameComponent(projectile));
  }

  @Test
  void getNumberOfRows() {
    PlayableArea playableArea = new PlayableAreaData(3, 8);
    assertEquals(3, playableArea.getNumberOfRows());
  }

  @Test
  void getNumberOfColumns() {
    PlayableArea playableArea = new PlayableAreaData(3, 8);
    assertEquals(8, playableArea.getNumberOfColumns());
  }

  @Test
  void enactFunctionOnAllGridComponents() {
    PlayableArea playableArea = new PlayableAreaData(2, 3);
    List<GameComponent> visited = new ArrayList<>();
    playableArea.enactFunctionOnAllGridComponents((gameComponent, row, col, health) -> {
      visited.add(gameComponent);
    });
    List<GameComponent> expected = new ArrayList<>();
    for (int index = 0; index < 6; index++) {
      expected.add(SingleState.EMPTY);
    }
    assertEquals(expected, visited);
  }

  @Test
  void enactFunctionOnNonEmptyGridComponents() {
    PlayableArea playableArea = new PlayableAreaData(2, 3);
    GameComponent projectile1 = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    Position position1 = new Position(0, 0);
    playableArea.setGameComponentAtPosition(position1, projectile1);
    List<GameComponent> visited = new ArrayList<>();
    playableArea.enactFunctionOnAllGridComponents((gameComponent, row, col, health) -> {
      visited.add(gameComponent);
    });
    List<GameComponent> expected = new ArrayList<>();
    expected.add(projectile1);
    for (int index = 1; index < 6; index++) {
      expected.add(SingleState.EMPTY);
    }
    assertEquals(expected, visited);
  }

  @Test
  void setLastColumnOfGameComponentsListCorrectSize() {
    GameComponent projectile1 = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    GameComponent projectile2 = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    GameComponent projectile3 = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    Position position1 = new Position(0, 3);
    Position position2 = new Position(1, 3);
    Position position3 = new Position(2, 3);
    List<GameComponent> gameComponentsToAdd = new ArrayList<>(
        Arrays.asList(projectile1, projectile2, projectile3));
    PlayableArea playableArea = new PlayableAreaData(3, 4);
    playableArea.setLastColumnOfGameComponents(gameComponentsToAdd);
    GameComponent retrieved1 = playableArea.getGameComponentAtPosition(position1);
    GameComponent retrieved2 = playableArea.getGameComponentAtPosition(position2);
    GameComponent retrieved3 = playableArea.getGameComponentAtPosition(position3);
    assertEquals(projectile1, retrieved1);
    assertEquals(projectile2, retrieved2);
    assertEquals(projectile3, retrieved3);
  }

  @Test
  void setLastColumnOfGameComponentsListTooBig() {
    GameComponent projectile1 = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    GameComponent projectile2 = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    GameComponent projectile3 = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    List<GameComponent> gameComponentsToAdd = new ArrayList<>(
        Arrays.asList(projectile1, projectile2, projectile3));
    PlayableArea playableArea = new PlayableAreaData(2, 4);
    assertThrows(ModelException.class,
        () -> playableArea.setLastColumnOfGameComponents(gameComponentsToAdd));
  }

  @Test
  void setLastColumnOfGameComponentsListTooSmall() {
    GameComponent projectile1 = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    GameComponent projectile2 = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    GameComponent projectile3 = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    List<GameComponent> gameComponentsToAdd = new ArrayList<>(
        Arrays.asList(projectile1, projectile2, projectile3));
    PlayableArea playableArea = new PlayableAreaData(4, 4);
    assertThrows(ModelException.class,
        () -> playableArea.setLastColumnOfGameComponents(gameComponentsToAdd));
  }

  @Test
  void createTypeIteratorEmptyDataChooseProjectile() {
    PlayableArea playableArea = new PlayableAreaData(2, 3);
    IIterator<GameComponent> typeIterator = playableArea.createTypeIterator(Projectile.class);
    int count = 0;
    while (typeIterator.hasNext()) {
      count++;
    }
    assertEquals(0, count);
  }

  @Test
  void createTypeIteratorNonEmptyData() {
    PlayableArea playableArea = new PlayableAreaData(2, 3);
    GameComponent projectile1 = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    GameComponent projectile2 = new SingleProjectile(10, 10, 10, 10,
        1, 0, 20, Tower.class);
    playableArea
        .setLastColumnOfGameComponents(new ArrayList<>(Arrays.asList(projectile1, projectile2)));
    IIterator<GameComponent> typeIterator = playableArea.createTypeIterator(Projectile.class);
    assertEquals(projectile1, typeIterator.next());
    assertEquals(projectile2, typeIterator.next());
    assertFalse(typeIterator.hasNext());
  }
}

