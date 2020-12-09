package view.GameView.StatusDisplay;

import controller.Alerts;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**
 * Represents the different statuses of the game that the user can see on screen as they are playing
 * the game. StatusBar dynamically creates and adds StatusDisplayComponents specified by each
 * level. Used by the GameView
 * @author Heather Grune (hlg20) and Alex Chao (ac590)
 */
public class StatusBar extends HBox {

  private final List<StatusDisplayComponent> myStatuses = new ArrayList<>();
  private final Alerts alerts = new Alerts();

  public StatusBar(GameStatusAPI game, Map<String, Image> images, double height,
      String[] statuses) {
    for (String statusType : statuses) {
      StatusDisplayComponent statusDisplay = createStatusDisplay(statusType, game, images, height);
      this.getChildren().add(statusDisplay);
      myStatuses.add(statusDisplay);
    }
    this.setAlignment(Pos.CENTER_LEFT);
    this.setPrefHeight(height);
    this.setId("status-bar");
    this.getStyleClass().add("game-view-bar");
  }

  /**
   * Calls update on each of the status bar components
   */
  public void update() {
    for (StatusDisplayComponent status : myStatuses) {
      status.update();
    }
  }

  private StatusDisplayComponent createStatusDisplay(String type, GameStatusAPI game,
      Map<String, Image> images, double height) {
    Class<?> selection;
    StatusDisplayComponent statusDisplay = null;
    try {
      selection = Class.forName("view.GameView.StatusDisplay." + type);
      statusDisplay = (StatusDisplayComponent) selection
          .getConstructor(Map.class, double.class, GameStatusAPI.class)
          .newInstance(images, height, game);
    } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      alerts.makeAlert(e.getMessage());
    }
    return statusDisplay;
  }
}
