package view.WinLoseScreens;

import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The level screen abstract class defines the API which is implemented by all winning and losing
 * screens.
 * @author Heather Grune (hlg20)
 */
public abstract class LevelScreen extends Stage {

  public static final int LEVEL_SCREEN_SIZE = 200;
  protected Scene myScene;
  protected VBox myRoot;
  protected Text myMessage;
  protected Button myControlButton;

  public LevelScreen(Map<String, String> text) {
    String className = this.getClass().getSimpleName();
    this.myRoot = new VBox();
    this.myRoot.getStyleClass().add("splash-screen");

    this.myMessage = new Text(text.get(className));
    this.myRoot.getChildren().add(myMessage);

    this.myControlButton = new Button(text.get(className + "Button"));
    this.myRoot.getChildren().add(myControlButton);
    this.setButtonEventHandler(event -> this.close());

    this.myScene = new Scene(myRoot, LEVEL_SCREEN_SIZE, LEVEL_SCREEN_SIZE);
    myScene.getStylesheets().add(text.get("CSS"));
    this.setScene(myScene);
    this.show();
  }

  /**
   * Set the action to occur when the user clicks the button on the level screen
   * @param eventHandler Action to instantiate when the button is clicked.
   */
  public void setButtonEventHandler(EventHandler<MouseEvent> eventHandler) {
    myControlButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
  }

}
