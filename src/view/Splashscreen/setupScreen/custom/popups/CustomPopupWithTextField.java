package view.Splashscreen.setupScreen.custom.popups;

import java.util.Map;
import javafx.scene.control.TextField;

/**
 * Constructs an abstract custom popup with an option to add a text field input to the popup menu
 * @author Heather Grune (hlg20)
 */
public abstract class CustomPopupWithTextField extends CustomPopup {

  protected TextField myCustomName;

  public CustomPopupWithTextField(String css, Map<String, String> labels) {
    super(css, labels);
  }

  protected void addNameField() {
    myCustomName = new TextField();
    myRoot.getChildren().add(myCustomName);
  }
}
