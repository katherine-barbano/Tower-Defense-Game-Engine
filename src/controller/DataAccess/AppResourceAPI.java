package controller.DataAccess;

import java.io.IOException;
import java.util.Map;

public interface AppResourceAPI {

  Map<String, String> getAppResources(String appPropertiesFileName);

  Map<String, String> getResourcePathMap();

  Map<String, String> getGameStatus(String gameName) throws IOException;
}
