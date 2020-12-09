package view.GameView.GameControlPanel;


import controller.GamePlay.Timing;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * This object has an event handler that pauses or plays the game on click
 *
 * @authors alex chao and heather grune
 */
public class PlayPauseButton extends GameControlComponent {

  private final Timing myTiming;

  public PlayPauseButton(Map<String, Image> images, double height, Timing timing) {
    super(images, height);
    this.myTiming = timing;
  }

  @Override
  protected void addEventListener() {
    EventHandler<MouseEvent> eventHandler = event -> myTiming.togglePause();
    this.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
  }
}
