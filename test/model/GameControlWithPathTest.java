package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import controller.ConfigObjects.Config;
import controller.ConfigObjects.EnemyConfig;
import controller.ExternalAccess.ConfigAPI;
import controller.ExternalAccess.ConfigAccess;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import model.gameComponents.SingleState;
import model.gameComponents.enemyGridComponents.enemies.Enemy;
import model.gameplay.MVCInteraction.API.GameControlAPI;
import model.gameplay.MVCInteraction.API.GameDisplayAPI;
import model.gameplay.MVCInteraction.concreteModel.GameStatus;
import model.gameplay.MVCInteraction.concreteModel.PathGameControl;
import model.gameplay.gameplayResources.ModelException;
import model.gameplay.gameplayResources.Position;
import org.junit.jupiter.api.Test;

public class GameControlWithPathTest {

  private void createWave(GameControlAPI game) {
    Properties ep = new Properties();
    try {
      FileReader f = new FileReader("doc/plan/data/simplegame/zombies/BasicZombie.properties");
      ep.load(f);
    } catch (Exception e) {
      assert (false);
    }
    List<Config> configList = new ArrayList<>(
        Arrays.asList(null, null, new EnemyConfig(ep), null, null, null));
    game.createWaveOfEnemies(configList);
  }

  private List<List<SingleState>> initializeBoardWithPath() {
    List<List<SingleState>> initialBoard = new ArrayList<>();
    for (int row = 0; row < 6; row++) {
      List<SingleState> rowList = new ArrayList<>();
      for (int column = 0; column < 8; column++) {
        rowList.add(SingleState.EMPTY);
      }
      initialBoard.add(rowList);
    }
    initialBoard.get(1).set(0, SingleState.PATH);
    initialBoard.get(1).set(1, SingleState.PATH);
    initialBoard.get(1).set(2, SingleState.PATH);
    initialBoard.get(1).set(3, SingleState.PATH);
    initialBoard.get(2).set(3, SingleState.PATH);
    initialBoard.get(3).set(3, SingleState.PATH);
    initialBoard.get(4).set(3, SingleState.PATH);
    initialBoard.get(4).set(4, SingleState.PATH);
    initialBoard.get(4).set(5, SingleState.PATH);
    initialBoard.get(4).set(6, SingleState.PATH);
    initialBoard.get(3).set(6, SingleState.PATH);
    initialBoard.get(2).set(6, SingleState.PATH);
    initialBoard.get(2).set(7, SingleState.PATH);

    initialBoard.get(3).set(0, SingleState.BLOCKED);
    initialBoard.get(3).set(1, SingleState.BLOCKED);
    initialBoard.get(4).set(0, SingleState.BLOCKED);
    initialBoard.get(4).set(1, SingleState.BLOCKED);
    initialBoard.get(5).set(0, SingleState.BLOCKED);
    initialBoard.get(5).set(1, SingleState.BLOCKED);
    initialBoard.get(0).set(5, SingleState.BLOCKED);
    initialBoard.get(0).set(6, SingleState.BLOCKED);
    initialBoard.get(0).set(7, SingleState.BLOCKED);

    return initialBoard;
  }

  @Test
  void initializeGameWithPathCheckSizes() {
    List<List<SingleState>> initialBoard = initializeBoardWithPath();
    GameControlAPI game = new PathGameControl(initialBoard, 6, 8, new GameStatus(20, 150));
    GameDisplayAPI gameDisplay = game.getGameDisplayAPI();
    assertEquals(120, gameDisplay.getEnemyAreaNumberOfRows());
    assertEquals(160, gameDisplay.getEnemyAreaNumberOfColumns());
    assertEquals(6, gameDisplay.getPlayerAreaNumberOfRows());
    assertEquals(8, gameDisplay.getPlayerAreaNumberOfColumns());
  }

  @Test
  void moveEnemiesOverEmpty() {
    List<List<SingleState>> initialBoard = initializeBoardWithPath();
    GameControlAPI game = new PathGameControl(initialBoard, 6, 8, new GameStatus(20, 150));
    createWave(game);
    game.moveComponents();
    game.getGameDisplayAPI().enactFunctionOnEnemyComponents((gameComponent, row, col, health) -> {
      if (row == 40 && col == 158) {
        assertTrue(gameComponent instanceof Enemy);
      } else {
        assertEquals(SingleState.EMPTY, gameComponent);
      }
    });
  }

  @Test
  void createCollision() {
    List<List<SingleState>> initialBoard = initializeBoardWithPath();
    GameControlAPI game = new PathGameControl(initialBoard, 6, 8, new GameStatus(20, 150));
    GameDisplayAPI gameDisplay = game.getGameDisplayAPI();
    createWave(game);
    ConfigAPI configFile = new ConfigAccess();
    try {
      gameDisplay.createTowerWithSun(configFile.getTowerConfig("Peashooter"), new Position(0, 0));
      game.moveComponents();
      game.moveComponents();
      game.moveComponents();
    } catch (Exception e) {
      assert (false);
    }
  }

  @Test
  void createWaveOfEnemiesCorrectSize() {
    List<List<SingleState>> initialBoard = initializeBoardWithPath();
    GameControlAPI game = new PathGameControl(initialBoard, 6, 8, new GameStatus(20, 150));
    createWave(game);
    game.getGameDisplayAPI().enactFunctionOnEnemyComponents((gameComponent, row, col, health) -> {
      if (row == 40 && col == 159) {
        assertTrue(gameComponent instanceof Enemy);
      } else {
        assertEquals(SingleState.EMPTY, gameComponent);
      }
    });
  }

  @Test
  void createWaveOfEnemiesLastSpotInGrid() {
    List<List<SingleState>> initialBoard = initializeBoardWithPath();
    initialBoard.get(5).set(7, SingleState.PATH);
    GameControlAPI game = new PathGameControl(initialBoard, 6, 8, new GameStatus(20, 150));
    Properties ep = new Properties();
    try {
      FileReader f = new FileReader("doc/plan/data/simplegame/zombies/BasicZombie.properties");
      ep.load(f);
    } catch (Exception e) {
      assert (false);
    }
    List<Config> configList = new ArrayList<>(
        Arrays.asList(null, null, null, null, null, new EnemyConfig(ep)));
    game.createWaveOfEnemies(configList);
    game.getGameDisplayAPI().enactFunctionOnEnemyComponents((gameComponent, row, col, health) -> {
      if (row == 100 && col == 159) {
        assertTrue(gameComponent instanceof Enemy);
      } else {
        assertEquals(SingleState.EMPTY, gameComponent);
      }
    });
  }

  @Test
  void createWaveOfEnemiesFirstSpotInGrid() {
    List<List<SingleState>> initialBoard = initializeBoardWithPath();
    initialBoard.get(0).set(7, SingleState.PATH);
    GameControlAPI game = new PathGameControl(initialBoard, 6, 8, new GameStatus(20, 150));
    Properties ep = new Properties();
    try {
      FileReader f = new FileReader("doc/plan/data/simplegame/zombies/BasicZombie.properties");
      ep.load(f);
    } catch (Exception e) {
      assert (false);
    }
    List<Config> configList = new ArrayList<>(
        Arrays.asList(new EnemyConfig(ep), null, null, null, null, null));
    game.createWaveOfEnemies(configList);
    game.getGameDisplayAPI().enactFunctionOnEnemyComponents((gameComponent, row, col, health) -> {
      if (row == 0 && col == 159) {
        assertTrue(gameComponent instanceof Enemy);
      } else {
        assertEquals(SingleState.EMPTY, gameComponent);
      }
    });
  }

  @Test
  void createWaveOfEnemiesIgnoreNonPathMatch() {
    List<List<SingleState>> initialBoard = initializeBoardWithPath();
    GameControlAPI game = new PathGameControl(initialBoard, 6, 8, new GameStatus(20, 150));
    Properties ep = new Properties();
    try {
      FileReader f = new FileReader("doc/plan/data/simplegame/zombies/BasicZombie.properties");
      ep.load(f);
    } catch (Exception e) {
      assert (false);
    }
    List<Config> configList = new ArrayList<>(
        Arrays.asList(null, new EnemyConfig(ep), null, null, null, null));
    game.getGameDisplayAPI().enactFunctionOnEnemyComponents((gameComponent, row, col, health) -> {
      if (row == 5 && col == 39) {
        assertTrue(gameComponent instanceof Enemy);
      } else {
        assertEquals(SingleState.EMPTY, gameComponent);
      }
    });
  }

  @Test
  void createWaveOfEnemiesTooSmall() {
    List<List<SingleState>> initialBoard = initializeBoardWithPath();
    GameControlAPI game = new PathGameControl(initialBoard, 6, 8, new GameStatus(20, 150));
    List<Config> configList = new ArrayList<>(Arrays.asList(null, null));
    assertThrows(ModelException.class,
        () -> game.createWaveOfEnemies(configList));
  }

  @Test
  void createWaveOfEnemiesTooBig() {
    List<List<SingleState>> initialBoard = initializeBoardWithPath();
    GameControlAPI game = new PathGameControl(initialBoard, 6, 8, new GameStatus(20, 150));
    List<Config> configList = new ArrayList<>(Arrays.asList(null, null, null, null));
    assertThrows(ModelException.class,
        () -> game.createWaveOfEnemies(configList));
  }



  @Test
  void projectileCreationRate() throws IOException {
    /*List<List<SingleState>> initialBoard = initializeBoardWithPath();
    GameControlAPI game = new PathGame(initialBoard, 6, 8, new GameStatus(50));
    GameDisplayAPI gameDisplay = game.getGameDisplayAPI();
    ConfigAPI configFile = new ConfigAccess();
    gameDisplay.createTower(configFile.getTowerConfig("Peashooter"), new Position(2, 2));
    for (int i = 0; i <= 9; i++) {
      game.placeNewComponentsFromTowers();
    }
    IIterator<Projectile> iterator = game.getIteratorForProjectiles();
    assertFalse(iterator.hasNext());
    game.placeNewComponentsFromTowers();
    iterator = game.getIteratorForProjectiles();
    assertTrue(iterator.hasNext());
    iterator.next();
    assertFalse(iterator.hasNext());*/
  }
}
