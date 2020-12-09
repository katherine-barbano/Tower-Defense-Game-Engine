package controller.GamePlay;

import com.opencsv.exceptions.CsvException;
import controller.DataAccess.ResourceAccessor;
import controller.DataAccess.towerDefenseResourceAPI;
import controller.Exceptions.GeneralExceptions.NullNameException;
import controller.Exceptions.ReaderExceptions.IncorrectCSVFormat;
import controller.Managers.AppControl;
import controller.Managers.ConditionsManager;
import controller.Managers.LevelManager;
import controller.Managers.TowerDefenseManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.util.Duration;
import model.gameplay.MVCInteraction.API.GameControlAPI;
import model.gameplay.MVCInteraction.API.GameStatusAPI;
import view.GameView.GameView;

/**
 * Represents a Level in a game. Creates both the front end and back end for the level and handles
 * their interactions. Depends on GameView, Game, and Condition and TowerDefenseManager
 *
 * @author Megan Richards
 */
public class Level implements Timing {

  public static final int SCENE_WIDTH = 1000;
  public static final int SCENE_HEIGHT = 500;

  private final towerDefenseResourceAPI resources = new ResourceAccessor();
  private final LevelManager levelManager;
  private final ConditionsManager conditionsManager;
  private final Map<String, String> levelProperties;
  private final AppControl appControl;
  private GameControlAPI game;
  private GameStatusAPI gameStatus;
  private GameView levelView;
  private boolean gameActive = true;

  /**
   * creates and manages the level, including the view, model, and conditions managers.
   * @param levelPropertiesFilePath - file path to the level properties
   * @param appControl - app control object used to restart the game
   * @throws IncorrectCSVFormat
   * @throws IOException
   * @throws CsvException
   * @throws NullNameException
   */
  public Level(String levelPropertiesFilePath,
      AppControl appControl)
      throws IncorrectCSVFormat, IOException, CsvException, NullNameException {
    Map<String, String> levelProperties = resources.getLevelProperties(levelPropertiesFilePath);
    conditionsManager = new ConditionsManager(levelProperties.get("WinConditions").split(","),
        levelProperties.get("LossConditions").split(","));
    levelManager = new TowerDefenseManager(levelProperties);
    this.levelProperties = levelProperties;
    this.appControl = appControl;
    initializeLevel();
  }

  private void initializeLevel() throws FileNotFoundException {
    game = levelManager.getGame();
    gameStatus = levelManager.getGameStatus();
    levelView = makeLevelView();
  }

  /**
   * gets the current front end scene
   * @return
   */
  public Scene getLevelScene() {
    return levelView.setupScene(SCENE_HEIGHT, SCENE_WIDTH);
  }

  /**
   * pauses or unpauses the game
   */
  @Override
  public void togglePause() {
    gameActive = !gameActive;
  }

  /**
   * if the game is active, updates the level frontend and backend
   * @param animationSpeed
   */
  public void update(Duration animationSpeed) {
    if (gameActive) {
      levelManager.update(animationSpeed);
      levelView.step();
    }
  }

  private GameView makeLevelView()
      throws FileNotFoundException {
    Map<String, Image> imageFileNames = resources
        .getComponentImageMap(levelProperties.get("Theme"));
    String cssFullPath = resources.getCSS(levelProperties.get("Theme"));
    Map<String, Double> componentSizes = resources.getComponentSizing();
    Map<String, Image> plantImages = resources
        .getPlantImageMap(levelProperties.get("Plants").split(","), levelProperties.get("Theme"));
    return new GameView(game.getGameDisplayAPI(), imageFileNames, cssFullPath, componentSizes,
        plantImages, this, levelProperties.get("PlantSelection"),
        levelProperties.get("StatusDisplay").split(","), appControl);
  }

  /**
   * resets the current level
   */
  @Override
  public void reset() {
    levelManager.reset();
    update(Duration.millis(1));
    togglePause();
  }

  /**
   * checks if the level is won
   * @return true if level is won, else otherwise
   */
  public boolean checkLevelWon() {
    return conditionsManager.checkLevelWon(game, game.getGameDisplayAPI().getGameStatusAPI());
  }

  /**
   * checks if the level is lost
   * @return true if the level is lose, else otherwise
   */
  public boolean checkLevelLost() {
    return conditionsManager.checkLevelLost(game, game.getGameDisplayAPI().getGameStatusAPI());
  }

  /**
   * used by the manager to get the status of the current level.
   * @return gamestatusAPI
   */
  public GameStatusAPI getGameStatusAPI() {
    return this.gameStatus;
  }
}

