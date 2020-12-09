package view.Splashscreen.setupScreen.custom.popups;

import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Defines an abstract custom popup window.  Used to display components necessary to customize
 * themes, configurations, and levels.
 * @author Heather Grune (hlg20)
 */
public abstract class CustomPopup extends Stage {

  protected Scene myScene;
  protected VBox myRoot;
  protected String myCSS;
  protected Map<String, String> myLabels;
  protected Button myApplyChoicesButton;

  public CustomPopup(String css, Map<String, String> labels) {
    this.myCSS = css;
    this.myLabels = labels;
  }

  protected void setupScene() {
    myRoot = new VBox();
    myRoot.getStyleClass().add("splash-screen");
    myScene = new Scene(myRoot, 400, 400);
    myScene.getStylesheets().add(myCSS);
    addUIComponents();
    addApplyChoicesButton();
    this.setScene(myScene);
    this.show();
  }

  protected abstract void addUIComponents();

  protected void addApplyChoicesButton() {
    myApplyChoicesButton = new Button("Apply");
    myApplyChoicesButton.setOnAction(event -> applyChoices());
    myRoot.getChildren().add(myApplyChoicesButton);
  }

  protected void applyChoices() {
    this.close();
  }

  protected void makeAlert(String header, String message) {
    Alert a = new Alert(Alert.AlertType.NONE);
    ButtonType close = new ButtonType("Ok", ButtonBar.ButtonData.CANCEL_CLOSE);
    a.getButtonTypes().addAll(close);
    a.setHeaderText(header);
    a.setContentText(message);
    a.show();
  }

}
