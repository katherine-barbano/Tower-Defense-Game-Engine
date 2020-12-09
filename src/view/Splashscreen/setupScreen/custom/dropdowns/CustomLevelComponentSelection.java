package view.Splashscreen.setupScreen.custom.dropdowns;

import controller.Alerts;
import controller.Customization.CustomLevelComponentHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Defines behavior customSelection dropdowns for level components such as theme, grid configuration,
 * and enemy wave configuration. Abstract class which extends CustomSelection.
 * @author Heather Grune (hlg20)
 */
public abstract class CustomLevelComponentSelection extends CustomSelection {

  private final Alerts alerts = new Alerts();
  protected CustomLevelComponentHandler myComponentHandler;

  public CustomLevelComponentSelection(String css, Map<String, String[]> options,
      Map<String, String> labels, CustomLevelComponentHandler componentHandler) {
    super(css, options, labels);
    this.myComponentHandler = componentHandler;
  }

  @Override
  protected String getCustomName() {
    try {
      String className = this.getClass().getSimpleName();
      Method getChosenName = myComponentHandler.getClass()
          .getDeclaredMethod("get" + className + "Name");
      return (String) getChosenName.invoke(myComponentHandler);

    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      alerts.makeAlert(e.getMessage());

      return "";
    }
  }
}
