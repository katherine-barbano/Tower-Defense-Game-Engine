package view.Splashscreen.FirstSplashscreen;

import javafx.scene.control.Button;

/**
 * The Setup button extends the JavaFX Button class and will be used to initiate the process of
 * getting the language selection and moving to the next splashscreen.
 * @author Heather Grune (hlg20)
 */
public class SetupButton extends Button {

  public SetupButton(String buttonText) {
    super(buttonText);
    this.setId("setup-button");
  }

}
