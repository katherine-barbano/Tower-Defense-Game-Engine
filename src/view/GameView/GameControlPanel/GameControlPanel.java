package view.GameView.GameControlPanel;

import controller.GamePlay.Timing;
import controller.Managers.AppControl;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import view.GameView.PlantSelection.PlantHandler;

/**
 * This represents all of the GameControlComponents together in one HBox
 *
 * @authors heather grune and alex chao
 */
public class GameControlPanel extends HBox {

  public GameControlPanel(Map<String, Image> images, double height,
      PlantHandler plantCreationHandler, Timing timing, AppControl appControl) {

    GameControlComponent myDiggingTool = new DiggingTool(images, height, plantCreationHandler);
    this.getChildren().add(myDiggingTool);
    myDiggingTool.setId("Shovel");

    GameControlComponent myHomeButton = new HomeButton(images, height, appControl);
    this.getChildren().add(myHomeButton);
    myHomeButton.setId("HomeButton");

    GameControlComponent myPlayPauseButton = new PlayPauseButton(images, height, timing);
    this.getChildren().add(myPlayPauseButton);
    myPlayPauseButton.setId("Pause");

    this.setPrefHeight(height);
    this.setId("game-control-panel");
    this.getStyleClass().add("game-view-bar");
  }

}
