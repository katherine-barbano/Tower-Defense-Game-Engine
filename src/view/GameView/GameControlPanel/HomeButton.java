package view.GameView.GameControlPanel;

import controller.Managers.AppControl;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * This has an event handler that sends the user back to the home screen on click
 *
 * @authors alex chao and heather grune
 */
public class HomeButton extends GameControlComponent {

  private final AppControl appControl;

  public HomeButton(Map<String, Image> images, double height, AppControl appControl) {
    super(images, height);
    this.appControl = appControl;
  }

  @Override
  protected void addEventListener() {
    EventHandler<MouseEvent> eventHandler = event -> appControl.restart();
    this.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
  }
}
