package controller.GamePlay;

import controller.Managers.ApplicationManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Starts the program
 */
public class Main extends Application {

  @Override
  public void start(Stage primaryStage) {
    new ApplicationManager(primaryStage, "defaultApplication");
  }
}

