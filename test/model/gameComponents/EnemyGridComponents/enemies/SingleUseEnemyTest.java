package model.gameComponents.EnemyGridComponents.enemies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import controller.ConfigObjects.EnemyConfig;
import controller.DataAccess.ResourceAccessor;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import model.gameComponents.Fire;
import model.gameComponents.GameComponent;
import model.gameComponents.enemyGridComponents.enemies.SingleUseEnemy;
import model.gameplay.MVCInteraction.concreteModel.GameStatus;
import model.gameplay.gameplayResources.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SingleUseEnemyTest {

  ResourceAccessor resources = new ResourceAccessor();
  EnemyConfig myEnemyConfig;
  int animationSpeed = 20;
  int gridRows = 20;
  int gridCols = 20;
  GameStatus testStatus = new GameStatus(20, 10);

  @BeforeEach
  public void initializeEnemyConfig() throws IOException {
    FileReader f = new FileReader(resources.getEnemyPropertiesFilePath("BasicEnemy"));
    Properties ep = new Properties();
    ep.load(f);
    myEnemyConfig = new EnemyConfig(ep);
  }


  @Test
  void bombEnemyEnactFunction() {
    SingleUseEnemy bombZombie = new SingleUseEnemy(myEnemyConfig, gridRows, gridCols,
        animationSpeed, testStatus);
    Position currentPosition = new Position(5, 5);
    GameStatus gameStat = new GameStatus(10, 150);
    Map<Position, GameComponent> newStates = bombZombie.enactAction(currentPosition, gameStat);
    //each of the states should be of class fire state
    for (Position p : newStates.keySet()) {
      assertEquals(Fire.class, newStates.get(p).getClass());
    }

  }


  @Test
  void bombEnemyEnactFunctionOnCorrectCells() {
    SingleUseEnemy bombZombie = new SingleUseEnemy(myEnemyConfig, gridRows, gridCols,
        animationSpeed, testStatus);
    Position currentPosition = new Position(5, 5);
    GameStatus gameStat = new GameStatus(10, 150);
    Map<Position, GameComponent> newStates = bombZombie.enactAction(currentPosition, gameStat);
    List<Position> possibleCells = new ArrayList<>();
    possibleCells.add(new Position(4, 4));
    possibleCells.add(new Position(4, 5));
    possibleCells.add(new Position(4, 6));
    possibleCells.add(new Position(5, 4));
    possibleCells.add(new Position(5, 6));
    possibleCells.add(new Position(6, 5));
    possibleCells.add(new Position(6, 4));
    possibleCells.add(new Position(6, 6));
    //enact function is working
    assertTrue(possibleCells.containsAll(newStates.keySet()));

  }

}
