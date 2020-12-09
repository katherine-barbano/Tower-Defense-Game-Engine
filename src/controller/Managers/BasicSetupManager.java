package controller.Managers;

import controller.Customization.TowerDefenseCustomGameHandler;
import controller.Customization.TowerDefenseCustomLevelComponentHandler;
import controller.DataAccess.ResourceAccessor;
import controller.DataAccess.setupResourceAPI;
import controller.Exceptions.GeneralExceptions.NoSelectionException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import view.Splashscreen.FirstSplashscreen.FirstSplashscreen;
import view.Splashscreen.FirstSplashscreenAPI;
import view.Splashscreen.SetupScreenAPI;
import view.Splashscreen.setupScreen.SetupScreen;

/**
 * Handles setting up the game, including customization and game select
 *
 * @author Megan Richards
 */
public class BasicSetupManager implements SetupManager {

  private static final int SCENE_WIDTH = 1000;
  private static final int SCENE_HEIGHT = 500;
  private final setupResourceAPI resources;
  private final Map<String, String> gameSelections = new HashMap<>();
  private final Stage stage;
  private final Scene myScene;
  private boolean isSetUpComplete;

  /**
   * runs the selection screens for the game set up
   * @param primaryStage
   */
  public BasicSetupManager(Stage primaryStage) {
    stage = primaryStage;
    resources = new ResourceAccessor();
    FirstSplashscreenAPI splashscreen = new FirstSplashscreen(
        resources.getSplashScreenProperties());
    myScene = splashscreen.setupScene(SCENE_HEIGHT, SCENE_WIDTH);
    stage.setScene(myScene);
    splashscreen.getSetUpButton().setOnAction(e -> {
      try {
        moveToSetUpScreen(splashscreen);
      } catch (Exception f) {
        makeAlert(new NoSelectionException().getMessage());
      }
    });
    stage.show();
  }

  private void moveToSetUpScreen(FirstSplashscreenAPI splashscreen) {
    gameSelections.put("language", splashscreen.getLanguageSelection());
    SetupScreenAPI setUpScreen = new SetupScreen(
        resources.getSetupPropertiesMap(gameSelections.get("language")),
        resources.getSetupOptionsMap(gameSelections.get("language")),
        new TowerDefenseCustomGameHandler(),
        new TowerDefenseCustomLevelComponentHandler());
    stage.setScene(setUpScreen.setupScene(SCENE_HEIGHT, SCENE_WIDTH));
    setUpScreen.getStartButton().setOnAction((event -> {
      try {
        addToSelections("game", setUpScreen.getGameSelection());
        addToSelections("playerName", setUpScreen.getPlayerName());
        this.isSetUpComplete = true;
      } catch (NullPointerException e) {
        makeAlert(new NoSelectionException().getMessage());
      }
    }
    ));
  }

  private void makeAlert(String message) {
    Alert a = new Alert(Alert.AlertType.NONE);
    ButtonType close = new ButtonType("Ok", ButtonBar.ButtonData.CANCEL_CLOSE);
    a.getButtonTypes().addAll(close);
    a.setContentText(message);
    a.show();
  }

  private void addToSelections(String key, String selection) {
    gameSelections.put(key, selection);
  }

  /**
   * getter for the game selections
   * @return
   */
  @Override
  public Map<String, String> getSelections() {
    return this.gameSelections;
  }

  /**
   * getter for the scene selections
   * @return
   */
  public Scene getMyScene() {
    return myScene;
  }

  /**
   * returns if the selections are complete and the app is able to start a game.
   * @return
   */
  @Override
  public boolean isSelectionComplete() {
    return isSetUpComplete;
  }


}
