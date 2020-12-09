package view.Splashscreen.setupScreen.custom.popups;

import controller.Customization.CustomLevelComponentHandler;
import java.io.File;
import java.util.Map;

/**
 * Creates the popup for uploading a custom grid CSV.
 * @author Heather Grune (hlg20)
 */
public class CustomGridSelectionPopup extends SingleFileUploadPopup {

  private final CustomLevelComponentHandler myComponentHandler;

  public CustomGridSelectionPopup(String css, Map<String, String> labels,
      CustomLevelComponentHandler handler) {
    super(css, labels);
    myComponentHandler = handler;
  }

  @Override
  protected void applyChoices() {
    try {
      File gridFile = myFileUploader.getSelectedFile();
      myComponentHandler.makeCustomGrid(gridFile);
      this.close();
    } catch (Exception e) {
      makeAlert("Custom Grid Configuration Error", e.getMessage());
    }
  }

}
