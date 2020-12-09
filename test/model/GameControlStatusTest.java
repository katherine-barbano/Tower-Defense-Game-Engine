package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import controller.ExternalAccess.ConfigAPI;
import controller.ExternalAccess.ConfigAccess;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.factory.TowerComponentFactory;
import model.gameComponents.SingleState;
import model.gameComponents.playerGridComponents.Tower;
import model.gameplay.MVCInteraction.API.GameDisplayAPI;
import model.gameplay.MVCInteraction.API.GameStatusAPI;
import model.gameplay.MVCInteraction.concreteModel.GameStatus;
import model.gameplay.MVCInteraction.concreteModel.GridGameControl;
import model.gameplay.gameplayResources.Position;
import org.junit.jupiter.api.Test;

class GameControlStatusTest {

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
  void notEnoughSunForPlant() throws IOException {
    List<List<SingleState>> initialBoard = initializeEmptyBoard(3, 8);
    GameDisplayAPI game = new GridGameControl(initialBoard, 3, 8, new GameStatus(5, 150))
        .getGameDisplayAPI();
    GameStatusAPI gameStatus = game.getGameStatusAPI();
    ConfigAPI configFile = new ConfigAccess();
    gameStatus.reset();
    game.createTowerWithSun(configFile.getTowerConfig("Peashooter"), new Position(1, 0));
  }

  @Test
  void sunFlowerIncreasesSun() throws IOException {
    GameStatus gameStatus = new GameStatus(100, 0);
    ConfigAPI configFile = new ConfigAccess();
    TowerComponentFactory towerComponentFactory = new TowerComponentFactory(5, 5, 50,
        gameStatus);
    Tower sunflower = towerComponentFactory
        .createGameComponent(configFile.getTowerConfig("Sunflower"));
    assertFalse(gameStatus.validateCost(1));
    sunflower.enactAction(new Position(0, 0), gameStatus);
    assertTrue(gameStatus.validateCost(1));
  }
}