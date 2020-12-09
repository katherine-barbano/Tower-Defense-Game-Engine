package view.Splashscreen.FirstSplashscreen;

import java.util.Map;
import view.Splashscreen.SelectionBox;

/**
 * The Language Selection dropdown extends SelectionBox to display the a dropdown of language options
 * and a label next to the dropdown.
 * @author Heather Grune(hlg20)
 */
public class LanguageSelection extends SelectionBox {


  public LanguageSelection(String[] options, Map<String, String> labels) {
    super(options, labels);
    this.setId("language-selection");
  }
}
