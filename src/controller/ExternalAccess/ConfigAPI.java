package controller.ExternalAccess;


import controller.ConfigObjects.Config;
import java.io.IOException;

/**
 * An API that creates Config objects
 */
public interface ConfigAPI {

  /**
   * Creates a tower config object when given the name of the tower
   * @param plantName
   * @return
   * @throws IOException
   */
  Config getTowerConfig(String plantName) throws IOException;
}
