package controller.DataAccess;

import java.util.Map;

public interface setupResourceAPI {

  Map<String, String> getSplashScreenProperties();

  Map<String, String> getSetupPropertiesMap(String language);

  Map<String, String[]> getSetupOptionsMap(String language);
}
