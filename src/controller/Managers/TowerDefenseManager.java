package controller.Managers;

import com.opencsv.exceptions.CsvException;
import controller.DataAccess.Reader;
import controller.DataAccess.ResourceAccessor;
import controller.DataAccess.towerDefenseResourceAPI;
import controller.Exceptions.GeneralExceptions.NullNameException;
import controller.Exceptions.ReaderExceptions.IncorrectCSVFormat;
import controller.GamePlay.WaveGenerator;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import javafx.util.Duration;
import model.factory.GameFactory;
import model.gameComponents.SingleState;
import model.gameplay.MVCInteraction.API.GameControlAPI;
import model.gameplay.MVCInteraction.API.GameStatusAPI;
import model.gameplay.MVCInteraction.concreteModel.GameStatus;

/**
 * Implements the LevelManager to set the specific functionality required for a tower defense game.
 * Handles the updating in the back end by moving its components and creating enemies.
 * Uses WaveGenerator to create the enemies and instantiates the GameStatus and Game
 */
public class TowerDefenseManager implements LevelManager {

  private final towerDefenseResourceAPI resources = new ResourceAccessor();
  private final GameFactory gameFactory = new GameFactory();
  private final GameStatusAPI gameStatus;
  private final int initialMovementSpeed;
  private final Reader reader = new Reader();
  private final Map<String, String> levelProperties;
  private final List<List<SingleState>> initialGrid;
  private final WaveGenerator waveGenerator;
  private GameControlAPI game;

  /**
   * runs the level operations for the backend objects for towerDefense
   * @param levelProperties - properties of a level
   * @throws IOException
   * @throws CsvException
   * @throws IncorrectCSVFormat
   * @throws NullNameException
   */
  public TowerDefenseManager(Map<String, String> levelProperties)
      throws IOException, CsvException, IncorrectCSVFormat, NullNameException {
    this.levelProperties = levelProperties;
    initialGrid = readInitialBoard(levelProperties);
    // TODO: 2020-11-16 get game movement speed from somewhere
    int gameMovementSpeed = 20;
    gameStatus = new GameStatus(gameMovementSpeed,
        Integer.parseInt(levelProperties.get("InitialSun")));
    initializeGame();
    initialMovementSpeed = gameStatus.getAnimationSpeed();
    waveGenerator = createWaveGenerator(levelProperties.get("WaveType"),
        readEnemyGrid(levelProperties));
  }

  private void initializeGame() {
    game = gameFactory.createGame(levelProperties.get("GridType"), initialGrid, initialGrid.size(),
        initialGrid.get(0).size(), gameStatus);
  }

  private List<List<String>> readEnemyGrid(Map<String, String> levelProperties)
      throws IOException, CsvException, IncorrectCSVFormat, NullNameException {
    String zombieBoardCSVPath = levelProperties.get("WavesCSV");
    Map<String, String> enemiesToClassNames = resources
        .getEnemyNameMap(levelProperties.get("EnemyMap"));
    //TODO add check
    return reader
        .getEnemies(resources.getWaveCSV(zombieBoardCSVPath), enemiesToClassNames);
  }

  @Override
  public void reset() {
    game.reset();
    gameStatus.reset();
    waveGenerator.reset();
  }

  @Override
  public GameStatusAPI getGameStatus() {
    return game.getGameDisplayAPI().getGameStatusAPI();
  }

  private List<List<SingleState>> readInitialBoard(Map<String, String> levelProperties)
      throws IOException, CsvException {
    String initialBoardCSVFileName = resources.getGridCSV(levelProperties.get("GridCSV"));
    return reader.getInitialGrid(initialBoardCSVFileName);
  }

  /**
   * makes wave of enemies and moves the components
   * @param animationSpeed - animation for the backend
   */
  @Override
  public void update(Duration animationSpeed) {
    waveGenerator.updateEnemyWaves(animationSpeed);
    moveComponents();
  }

  @Override
  public GameControlAPI getGame() {
    return this.game;
  }


  private void moveComponents() {
    int movementInterval = 0;
    while (movementInterval < gameStatus.getAnimationSpeed()) {
      game.moveComponents();
      movementInterval += initialMovementSpeed;
    }
  }

  private WaveGenerator createWaveGenerator(String type, List<List<String>> enemiesInDatFile) {
    Class<?> generator;
    WaveGenerator waveGenerator = null;
    try {
      generator = Class.forName("controller.GamePlay." + type + "WaveGenerator");
      waveGenerator = (WaveGenerator) generator
          .getConstructor(List.class, GameControlAPI.class, GameStatusAPI.class)
          .newInstance(enemiesInDatFile, game, gameStatus);
    } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return waveGenerator;
  }
}
