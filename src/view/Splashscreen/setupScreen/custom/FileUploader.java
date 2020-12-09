package view.Splashscreen.setupScreen.custom;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Create the popup to upload a file with a chosen extension.
 * @author Heather Grune (hlg20)
 */
public class FileUploader {

  private final File mySelectedFile;

  public FileUploader(Stage window, String description, String extension) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new ExtensionFilter(description,
        extension, "*.sim"));
    mySelectedFile = fileChooser.showOpenDialog(window);
  }

  /**
   * The selected file from the uploader
   * @return the selected File
   */
  public File getSelectedFile() {
    return mySelectedFile;
  }

}
