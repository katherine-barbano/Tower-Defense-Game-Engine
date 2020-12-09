package view.Splashscreen.setupScreen.custom.popups;

import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import view.Splashscreen.setupScreen.custom.FileUploader;

/**
 * Abstract class which defines the structure of a popup for selecting one configuration file.
 * @author Heather Grune (hlg20)
 */
public abstract class SingleFileUploadPopup extends CustomPopup {

  protected FileUploader myFileUploader;

  public SingleFileUploadPopup(String css, Map<String, String> labels) {
    super(css, labels);
    super.setupScene();
  }

  @Override
  protected void addUIComponents() {
    Text instructions = new Text(myLabels.get(this.getClass().getSimpleName()));
    Button selectFileButton = new Button("Select a CSV File");
    selectFileButton.setOnAction(event -> createFileUploader());
    myRoot.getChildren().add(instructions);
    myRoot.getChildren().add(selectFileButton);
  }

  protected void createFileUploader() {
    myFileUploader = new FileUploader(this, "Grid Configuration File", "*.csv");
  }

}
