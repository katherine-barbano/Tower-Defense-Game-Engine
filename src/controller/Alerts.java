package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 * Generates error messages when catching exceptions
 *
 * @author Megan Richards
 */
public class Alerts {

  /**
   * Makes an alert with a given message
   * @param message
   */
  public void makeAlert(String message) {
    Alert a = new Alert(Alert.AlertType.NONE);
    ButtonType close = new ButtonType("Ok", ButtonBar.ButtonData.CANCEL_CLOSE);
    a.getButtonTypes().addAll(close);
    a.setContentText(message);
    a.show();
  }
}
