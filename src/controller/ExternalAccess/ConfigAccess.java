package controller.ExternalAccess;

import controller.ConfigObjects.Config;
import controller.ConfigObjects.TowerConfig;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Creates tower config objects when given a plant name 
 * 
 * @author Megan Richards
 */
public class ConfigAccess implements ConfigAPI {

  /**
   * @see ConfigAPI#getTowerConfig(String) 
   * @param plantName
   * @return
   * @throws IOException
   */
  @Override
  public Config getTowerConfig(String plantName) throws IOException {
    FileReader f = new FileReader(String.format("src/resources/towers/%s.properties", plantName));
    Properties ep = new Properties();
    ep.load(f);
    return new TowerConfig(ep);
  }

}
