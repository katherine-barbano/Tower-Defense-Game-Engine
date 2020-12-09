package view.Splashscreen.setupScreen.custom.popups;

import controller.Customization.CustomLevelComponentHandler;
import java.util.Map;

/**
 * Creates the popup for uploading a custom enemy wave CSV.
 * @author Heather Grune (hlg20)
 */
public class CustomEnemyWaveSelectionPopup extends SingleFileUploadPopup {

  private final CustomLevelComponentHandler myComponentHandler;

  public CustomEnemyWaveSelectionPopup(String css, Map<String, String> labels,
      CustomLevelComponentHandler componentHandler) {
    super(css, labels);
    this.myComponentHandler = componentHandler;
  }

  @Override
  protected void applyChoices() {
    try {
      myComponentHandler.makeCustomWaveCSV(myFileUploader.getSelectedFile());
      this.close();
    } catch (Exception e) {
      makeAlert("Enemy Waves CSV Error", e.getMessage());
    }
  }
}
