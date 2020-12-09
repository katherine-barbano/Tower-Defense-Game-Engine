package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import controller.ConfigObjects.Config;
import controller.ConfigObjects.EnemyConfig;
import controller.ConfigObjects.TowerConfig;
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
import model.gameplay.MVCInteraction.concreteModel.GridGameControl;
import model.gameplay.gameplayResources.ModelException;
import model.gameplay.gameplayResources.Position;
import org.junit.jupiter.api.Test;

public class GameControlWithGridTest {

  private void createWave(GameControlAPI game) {
    Properties ep = new Properties();
    try {
      FileReader f = new FileReader("doc/plan/data/simplegame/zombies/BasicZombie.properties");
      ep.load(f);
    } catch (Exception e) {
      assert (false);
    }
    List<Config> configList = new ArrayList<>(Arrays.asList(null, new EnemyConfig(ep), null));
    game.createWaveOfEnemies(configList);
  }

  private List<List<SingleState>> initializeEmptyBoard(int rowSize, int columnSize) {
    List<List<SingleState>> initialBoard = new ArrayList<>();
    for (int row = 0; row < rowSize; row++) {
      List<SingleState> rowList = new ArrayList<>();
      for (int column = 0; column < columnSize; column++) {
        rowList.add(SingleState.EMPTY);
      }
      initialBoard.add(rowList);
    }
    return initialBoard;
  }

  @Test
  void initializeEmptyGameWithGridCheckSizes() {
    List<List<SingleState>> initialBoard = initializeEmptyBoard(5, 3);
    GameControlAPI game = new GridGameControl(initialBoard, 5, 3, new GameStatus(5, 150));
    GameDisplayAPI displayFunctionality = game.getGameDisplayAPI();
    assertEquals(100, displayFunctionality.getEnemyAreaNumberOfRows());
    assertEquals(60, displayFunctionality.getEnemyAreaNumberOfColumns());
    assertEquals(5, displayFunctionality.getPlayerAreaNumberOfRows());
    assertEquals(3, displayFunctionality.getPlayerAreaNumberOfColumns());
  }

  @Test
  void initializeEmptyAndBlockedGameWithGridCheckSizes() {
    List<List<SingleState>> initialBoard = initializeEmptyBoard(5, 3);
    initialBoard.get(0).set(1, SingleState.BLOCKED);
    GameControlAPI game = new GridGameControl(initialBoard, 5, 3, new GameStatus(5, 150));
    GameDisplayAPI displayFunctionality = game.getGameDisplayAPI();
    assertEquals(100, displayFunctionality.getEnemyAreaNumberOfRows());
    assertEquals(60, displayFunctionality.getEnemyAreaNumberOfColumns());
    assertEquals(5, displayFunctionality.getPlayerAreaNumberOfRows());
    assertEquals(3, displayFunctionality.getPlayerAreaNumberOfColumns());
  }

  @Test
  void moveEnemiesOverEmpty() {
    List<List<SingleState>> initialBoard = initializeEmptyBoard(3, 8);
    GameControlAPI game = new GridGameControl(initialBoard, 3, 8, new GameStatus(20, 150));
    createWave(game);
    game.moveComponents();
    game.getGameDisplayAPI().enactFunctionOnEnemyComponents((gameComponent, row, col, health) -> {
      if (row == 20 && col == 158) {
        assertTrue(gameComponent instanceof Enemy);
      } else {
        assertEquals(SingleState.EMPTY, gameComponent);
      }
    });
  }

  @Test
  void enemyMovesOverBlocked() {
    List<List<SingleState>> initialBoard = initializeEmptyBoard(3, 8);
    initialBoard.get(1).set(7, SingleState.BLOCKED);
    GameControlAPI game = new GridGameControl(initialBoard, 3, 8, new GameStatus(20, 150));
    createWave(game);
    game.moveComponents();
    game.getGameDisplayAPI().enactFunctionOnEnemyComponents((gameComponent, row, col, health) -> {
      if (row == 20 && col == 158) {
        assertTrue(gameComponent instanceof Enemy);
      } else {
        assertEquals(SingleState.EMPTY, gameComponent);
      }
    });
  }

  @Test
  void createCollision() {
    List<List<SingleState>> initialBoard = initializeEmptyBoard(3, 8);
    GameControlAPI game = new GridGameControl(initialBoard, 3, 8, new GameStatus(20, 150));
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
    List<List<SingleState>> initialBoard = initializeEmptyBoard(3, 8);
    GameControlAPI game = new GridGameControl(initialBoard, 3, 8, new GameStatus(20, 150));
    createWave(game);
    game.moveComponents();
    game.getGameDisplayAPI().enactFunctionOnEnemyComponents((gameComponent, row, col, health) -> {
      if (row == 20 && col == 158) {
        assertTrue(gameComponent instanceof Enemy);
      } else {
        assertEquals(SingleState.EMPTY, gameComponent);
      }
    });
  }

  @Test
  void createWaveOfEnemiesTooSmall() {
    List<List<SingleState>> initialBoard = initializeEmptyBoard(3, 8);
    GameControlAPI game = new GridGameControl(initialBoard, 3, 8, new GameStatus(20, 150));
    List<Config> configList = new ArrayList<>(Arrays.asList(null, null));
    assertThrows(ModelException.class,
        () -> game.createWaveOfEnemies(configList));
  }

  @Test
  void createWaveOfEnemiesTooBig() {
    List<List<SingleState>> initialBoard = initializeEmptyBoard(3, 8);
    GameControlAPI game = new GridGameControl(initialBoard, 3, 8, new GameStatus(20, 150));
    List<Config> configList = new ArrayList<>(Arrays.asList(null, null, null, null));
    assertThrows(ModelException.class,
        () -> game.createWaveOfEnemies(configList));
  }

  @Test
  void removeComponentFromGameAPIPassSingleStatePosition() {
    List<List<SingleState>> initialBoard = initializeEmptyBoard(3, 8);
    GameControlAPI game = new GridGameControl(initialBoard, 3, 8, new GameStatus(20, 150));
    GameDisplayAPI gameDisplay = game.getGameDisplayAPI();
    gameDisplay.removePlayerComponent(new Position(2, 2));
    gameDisplay.createTowerWithSun(getNewTowerConfig(), new Position(2, 2));
  }

  @Test
  void removeComponentFromGameAPIPassTower() {
    List<List<SingleState>> initialBoard = initializeEmptyBoard(3, 8);
    GameControlAPI game = new GridGameControl(initialBoard, 3, 8, new GameStatus(20, 150));
    GameDisplayAPI gameDisplay = game.getGameDisplayAPI();
    gameDisplay.createTowerWithSun(getNewTowerConfig(), new Position(2, 2));
    gameDisplay.removePlayerComponent(new Position(2, 2));
    for (int i = 0; i < 50; i++) {
      game.moveComponents();
    }
    gameDisplay.createTowerWithSun(getNewTowerConfig(), new Position(2, 2));
  }

  @Test
  void noEnemiesExistTrue() {
    List<List<SingleState>> initialBoard = initializeEmptyBoard(3, 8);
    GameControlAPI game = new GridGameControl(initialBoard, 3, 8, new GameStatus(20, 150));
    assertTrue(game.noEnemiesExist());
  }

  @Test
  void noEnemiesExistFalse() {
    List<List<SingleState>> initialBoard = initializeEmptyBoard(3, 8);
    GameControlAPI game = new GridGameControl(initialBoard, 3, 8, new GameStatus(20, 150));
    createWave(game);
    assertFalse(game.noEnemiesExist());
  }

  private TowerConfig getNewTowerConfig() {
    try {
      ConfigAPI configFile = new ConfigAccess();
      return (TowerConfig) configFile.getTowerConfig("Sunflower");
    } catch (Exception e) {
      assert (false);
    }
    return null;
  }

}
