package view.Splashscreen.setupScreen.custom.dropdowns;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import view.Splashscreen.SelectionBox;
import view.Splashscreen.setupScreen.SetupScreenAlertMessage;

/**
 * CustomSelection is an abstract class that constructs a selectionBox that has a custom option.  When the custom option is
 * selected it constructs a new popup to customize a specific parameter.
 * @author Heather Grune (hlg20)
 */
public abstract class CustomSelection extends SelectionBox {

  public static final String CUSTOM_STRING = "Custom";
  protected final String myCSS;
  protected final Map<String, String> myLabels;
  protected final Map<String, String[]> myOptions;

  public CustomSelection(String css, Map<String, String[]> options, Map<String, String> labels) {
    super(options, labels);
    myCSS = css;
    myLabels = labels;
    myOptions = options;
  }

  @Override
  protected void setOnSelection(int selectedIndex) {
    super.setOnSelection(selectedIndex);
    if (mySelection.equals(CUSTOM_STRING)) {
      try {
        constructPopup();
      } catch (Exception e) {
        new SetupScreenAlertMessage("Custom Selection Error", "Could not construct popup menu");
      }
    }
  }

  /**
   * Get the selection from the dropdown.  If the selection is custom, get the name of the customized
   * field.
   * @return Name of selection or customized selection
   */
  @Override
  public String getMySelection() {
    String selection = super.getMySelection();

    if (selection.equals(CustomSelection.CUSTOM_STRING)) {
      return getCustomName();
    }
    return selection;
  }

  protected abstract String getCustomName();


  protected void constructPopup()
      throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
    Class<?> popup = Class.forName(
        "view.Splashscreen.setupScreen.custom.popups.Custom" + this.getClass().getSimpleName()
            + "Popup");
    Constructor<?> popupConstructor = popup.getConstructor(String.class, Map.class);
    popupConstructor.newInstance(myCSS, myLabels);
  }

}
