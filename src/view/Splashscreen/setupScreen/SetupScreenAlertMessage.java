package view.Splashscreen.setupScreen;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 * This class creates the alert messages to be displayed when catching errors that occur in customized
 * game setup.
 * @author Heather Grune (hlg20)
 */
public class SetupScreenAlertMessage {

  public SetupScreenAlertMessage(String header, String message) {
    Alert a = new Alert(Alert.AlertType.NONE);
    ButtonType close = new ButtonType("Ok", ButtonBar.ButtonData.CANCEL_CLOSE);
    a.getButtonTypes().addAll(close);
    a.setHeaderText(header);
    a.setContentText(message);
    a.show();
  }
}
