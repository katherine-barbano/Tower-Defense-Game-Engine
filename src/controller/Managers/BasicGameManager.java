package controller.Managers;

import com.opencsv.exceptions.CsvException;
import controller.DataAccess.AppResourceAPI;
import controller.DataAccess.ResourceAccessor;
import controller.DataAccess.Writer;
import controller.Exceptions.GeneralExceptions.NullNameException;
import controller.Exceptions.ReaderExceptions.IncorrectCSVFormat;
import controller.GamePlay.Level;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.gameplay.MVCInteraction.API.GameStatusAPI;
import view.WinLoseScreens.GameWonScreen;
import view.WinLoseScreens.LevelLostScreen;
import view.WinLoseScreens.LevelScreen;
import view.WinLoseScreens.LevelWinScreen;

/**
 * Handles the running of a game without knowing what type of game is being run. Handles the transition
 * between the levels and creates new levels based off of a properties file.
 *
 * @author Megan Richards
 */
public class BasicGameManager implements GameManager {

  public static final String LANGUAGE_KEY = "language";
  private final AppResourceAPI resourceAPI = new ResourceAccessor();
  private final Map<Integer, String> levelPropertyFileNames;

  private final Writer writer = new Writer(resourceAPI.getResourcePathMap());

  private final Map<String, String> resourcesMap;
  private final Map<String, String> gameSelections;
  private final Map<String, String> gameScores = new HashMap<>();
  private final AppControl appControl;
  private final Stage stage;
  private Level currentLevel;
  private int levelIndex = 0;
  private boolean screenGenerated = false;

  /**
   * controls the game operations/workflow and conditions for switching levels.
   * @param selections - game selections
   * @param primaryStage - stage to display the game on
   * @param appControl - control of the app used to
   * @throws IncorrectCSVFormat
   * @throws IOException
   * @throws CsvException
   * @throws NullNameException
   */
  public BasicGameManager(Map<String, String> selections, Stage primaryStage, AppControl appControl)
      throws IncorrectCSVFormat, IOException, CsvException, NullNameException {
    stage = primaryStage;
    gameSelections = selections;
    resourcesMap = new ResourceAccessor().getSetupPropertiesMap(gameSelections.get(LANGUAGE_KEY));
    levelPropertyFileNames = getLevelFileNamesFromGameProperties(selections.get("game"));
    currentLevel = new Level(levelPropertyFileNames.get(0), appControl);
    stage.setScene(currentLevel.getLevelScene());
    this.appControl = appControl;
  }

  /**
   * updates the game and checks the status of the game
   * @param animationSpeed - speed used to animate the backend movement
   */
  @Override
  public void update(int animationSpeed) {
    currentLevel.update(Duration.millis(animationSpeed));
    checkGameStatus();
  }

  /**
   * checks if the current level is won/lost and produces win/loss screens
   */
  @Override
  public void checkGameStatus() {
    if (currentLevel.checkLevelLost() && !screenGenerated) {
      updateHighScore(currentLevel.getGameStatusAPI());
      currentLevel.reset();
      displayLevelLostScreen();
    } else if (currentLevel.checkLevelWon() && !screenGenerated) {
      if (levelIndex == levelPropertyFileNames.size()) {
        winGame();
      } else {
        displayLevelWonScreen();
      }
    }
  }

  private void winGame() {
    //gameActive = false;
    // produce win screen
    new GameWonScreen(resourcesMap);
    screenGenerated = true;
  }

  private Map<Integer, String> getLevelFileNamesFromGameProperties(String gameName) {
    ResourceAccessor resources = new ResourceAccessor();
    return resources.getLevelPropertyFileNamesForGame(gameName);
  }

  private void displayLevelLostScreen() {
    LevelScreen levelLostScreen = new LevelLostScreen(resourcesMap);
    screenGenerated = true;
    EventHandler<MouseEvent> eventHandler = event -> {
      levelLostScreen.close();
      screenGenerated = false;
      currentLevel.togglePause();
    };
    levelLostScreen.setButtonEventHandler(eventHandler);
  }

  private void displayLevelWonScreen() {
    LevelScreen levelWonScreen = new LevelWinScreen(resourcesMap);
    levelWonScreen.setTitle("LevelWon");
    screenGenerated = true;
    EventHandler<MouseEvent> eventHandler = event -> {
      try {
        moveToNextLevel();
        levelWonScreen.close();
        screenGenerated = false;
      } catch (IncorrectCSVFormat | IOException | CsvException | NullNameException incorrectCSVFormat) {
        incorrectCSVFormat.printStackTrace();
      }
    };
    levelWonScreen.setButtonEventHandler(eventHandler);
  }

  private void moveToNextLevel()
      throws IncorrectCSVFormat, IOException, CsvException, NullNameException {
    levelIndex++;
    if (levelIndex >= levelPropertyFileNames.size()) {
      writeNewHighScore();
      winGame();
    } else {
      updateHighScore(currentLevel.getGameStatusAPI());
      makeNewLevel();
    }
  }

  private void writeNewHighScore() throws IOException {
    String gameName = gameSelections.get("game");
    Map<String, String> highScore = resourceAPI.getGameStatus(gameName);
    Map<String, String> currentScore = currentLevel.getGameStatusAPI().getStatus();
    if (Integer.parseInt(highScore.get("points")) > Integer.parseInt(currentScore.get("points"))) {
      writer.writeHighScore(gameName, currentScore, gameSelections.get("playerName"));
    }
    if (Integer.parseInt(highScore.get("timeElapsed")) > Integer
        .parseInt(currentScore.get("timeElapsed"))) {
      writer.writeHighScore(gameName, currentScore, gameSelections.get("playerName"));

    }
  }

  private void updateHighScore(GameStatusAPI gameStatusAPI) {
    Map<String, String> status = gameStatusAPI.getStatus();

    for (String metric : status.keySet()) {
      gameScores.put(metric, status.get(metric));
    }
  }

  /**
   * initializes a new level of the game
   * @throws IncorrectCSVFormat
   * @throws IOException
   * @throws CsvException
   * @throws NullNameException
   */
  public void makeNewLevel()
      throws IncorrectCSVFormat, IOException, CsvException, NullNameException {
    currentLevel = new Level(levelPropertyFileNames.get(levelIndex), appControl);
    stage.setScene(currentLevel.getLevelScene());
  }
}
