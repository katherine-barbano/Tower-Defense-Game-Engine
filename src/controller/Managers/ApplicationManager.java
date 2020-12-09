package controller.Managers;

import controller.Alerts;
import controller.DataAccess.AppResourceAPI;
import controller.DataAccess.ResourceAccessor;
import controller.Exceptions.GeneralExceptions;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Runs an application with a set up that leads into a game. Shows flexibility in how this could
 * account for any type of game that the Setup and Game managers can handle.
 */
public class ApplicationManager implements AppControl {

  private static final int ANIMATION_SPEED = 50;
  private final Stage stage;
  private final Map<String, String> appResourceMap;
  private final Alerts alerts = new Alerts();
  private AppResourceAPI resources = new ResourceAccessor();
  private boolean gameStarted = false;
  private SetupManager setupManager;
  private GameManager gameManager;

  /**
   * The main controller of the application, running the set up manager and the game manager.
   * @param primaryStage - stage from duke Application
   * @param appPropertiesFileName - file with setUpManager, ResourceAPI, and GameManager.
   */
  public ApplicationManager(Stage primaryStage, String appPropertiesFileName) {
    stage = primaryStage;
    appResourceMap = resources.getAppResources(appPropertiesFileName);
    startSetupManager(appResourceMap.get("SetupManager"));
    String gameManagerName = appResourceMap.get("GameManager");
    resources = getResourceAPI(appResourceMap.get("ResourceAPI"));
    startTimeline();
  }

  //TODO add reflection

  /**
   * creates the game manager object and produces it on the screen
   * @param selections Map of selections for the game to create. Required keys: language, game, playerName.
   */
  private void startGameManager(Map<String, String> selections) {
    try {
      gameManager = new BasicGameManager(selections, stage, this);
    } catch (NullPointerException e) {
      restart();
      alerts.makeAlert(new GeneralExceptions.InvalidResourceAccessException().getMessage());
    } catch (Exception e) {
      restart();
      alerts.makeAlert(e.getMessage());
    }
  }

  //TODO add reflection
  private void startSetupManager(String setUpManagerName) {
    setupManager = new BasicSetupManager(stage);
  }

  /**
   * restarts the application, bringing the user back the set up screen.
   */
  public void restart() {
    this.gameStarted = false;
    startSetupManager(appResourceMap.get("SetupManager"));
  }

  //TODO make this dependent on choice w/reflection
  private AppResourceAPI getResourceAPI(String resourceAccessorName) {
    return new ResourceAccessor();
  }

  private void startTimeline() {
    KeyFrame displayFrame = new KeyFrame(Duration.millis(ANIMATION_SPEED), e -> {
      try {
        step();
      } catch (Exception f) {
        alerts.makeAlert(f.getMessage());
        this.gameStarted = false;
      }
    });
    Timeline simulate = new Timeline();
    simulate.setCycleCount(Timeline.INDEFINITE);
    simulate.getKeyFrames().add(displayFrame);
    simulate.play();
  }

  /**
   * called at each time interval, updates either the game manager or the set up manager.
   */
  public void step() {
    try {
      if (gameStarted) {
        gameManager.update(ANIMATION_SPEED);
        gameManager.checkGameStatus();
      } else if (setupManager.isSelectionComplete()) {
        this.gameStarted = setupManager.isSelectionComplete();
        startGameManager(setupManager.getSelections());
      }
    } catch (Exception e) {
      alerts.makeAlert(e.getMessage());
    }
  }
}
