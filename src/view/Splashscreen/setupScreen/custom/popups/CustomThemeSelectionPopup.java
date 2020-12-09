package view.Splashscreen.setupScreen.custom.popups;

import controller.Customization.CustomLevelComponentHandler;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Button;
import view.Splashscreen.setupScreen.custom.FileUploader;

/**
 * Creates the popup for selecting a custom theme.  Contains buttons to upload images files
 * for all game components.
 * @author Heather Grune (hlg20)
 */
public class CustomThemeSelectionPopup extends CustomPopupWithTextField {

  public static final String COMPONENT_NAMES_KEY = "ComponentNames";
  public static final String COMPONENT_SELECTION_LABEL = "ComponentNameLabel";
  private final CustomLevelComponentHandler myComponentHandler;
  private final String[] myComponentNames;
  private final Map<String, File> mySelectedImages;
  private final Map<String, FileUploader> myFileUploaders;

  public CustomThemeSelectionPopup(String css, Map<String, String[]> options,
      Map<String, String> labels,
      CustomLevelComponentHandler componentHandler) {
    super(css, labels);
    this.myComponentHandler = componentHandler;
    this.myComponentNames = options.get(COMPONENT_NAMES_KEY);
    this.mySelectedImages = new HashMap<>();
    this.myFileUploaders = new HashMap<>();
    super.setupScene();
  }

  @Override
  protected void addUIComponents() {
    addNameField();
    for (String name : myComponentNames) {
      Button selectFileButton = new Button(
          String.format(myLabels.get(COMPONENT_SELECTION_LABEL), name));
      selectFileButton.setOnAction(event -> addFile(name));
      myRoot.getChildren().add(selectFileButton);
    }
  }

  private void addFile(String name) {
    FileUploader fileUpload = new FileUploader(this, "Component Image", "*.png");
    myFileUploaders.put(name, fileUpload);
  }

  @Override
  protected void applyChoices() {
    try {
      createImageMap();
      String levelName = myCustomName.getText();
      myComponentHandler.makeCustomTheme(mySelectedImages, levelName);
      this.close();
    } catch (Exception e) {
      makeAlert("Custom Theme Creation Error", e.getMessage());
    }
  }

  private void createImageMap() {
    for (String key : myFileUploaders.keySet()) {
      File newImage = myFileUploaders.get(key).getSelectedFile();
      mySelectedImages.put(key, newImage);
    }
  }
}
