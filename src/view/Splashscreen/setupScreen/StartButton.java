package view.Splashscreen.setupScreen;


import javafx.scene.control.Button;

/**
 * Creates the start button.  This button will be pressed to intialize the game based on the user's
 * game selection.
 * @author Heather Grune (hlg20)
 */
public class StartButton extends Button {

  public StartButton(String buttonText) {
    super(buttonText);
    this.setId("start-button");
  }

}
