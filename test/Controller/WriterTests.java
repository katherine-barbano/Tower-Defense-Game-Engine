package Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import controller.DataAccess.Writer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.junit.jupiter.api.Test;

public class WriterTests {

  Map<String, String> resourcePaths = new HashMap<>();
  File directory = new File("test/TestData");


  @Test
  public void WriteNewThemeCreatesPropertiesFileWithCorrectName() throws IOException {
    resourcePaths.put("Theme", "test/TestData/");
    Writer writer = new Writer(resourcePaths);
    makeThemePropertiesFile(writer);
    List<String> testFilesCreated = Arrays.asList(directory.list());
    assert (testFilesCreated.contains("testTheme.properties"));
  }

  @Test
  public void WriteNewThemeCreatesPropertiesFileWithCorrectInformation() throws IOException {
    resourcePaths.put("Theme", "test/TestData/");
    Writer writer = new Writer(resourcePaths);
    makeThemePropertiesFile(writer);
    FileInputStream inputStream = new FileInputStream("test/TestData/testTheme.properties");
    Properties p = new Properties();
    p.load(inputStream);
    assertTrue(p.containsKey("empty"));
    assertEquals("empty.png", p.get("empty"));
  }


  @Test
  public void WriteNewVariationCreatesPropertiesFileWithCorrectInformation() throws IOException {
    resourcePaths.put("Variation", "test/TestData/");
    Writer writer = new Writer(resourcePaths);
    makeVariationPropertiesFile(writer);
    FileInputStream inputStream = new FileInputStream("test/TestData/testVariation.properties");
    Properties p = new Properties();
    p.load(inputStream);
    assertTrue(p.containsKey("WinConditions"));
    assertTrue(p.containsKey("LossConditions"));
    assertEquals(p.get("WinConditions"), "Win");
    assertEquals(p.get("LossConditions"), "Loss");
  }

  @Test
  public void WriteNewGameCreatesPropertiesFileWithCorrectInformation() throws IOException {
    resourcePaths.put("Game", "test/TestData/");
    Writer writer = new Writer(resourcePaths);
    List<String> levels = Arrays.asList("level1", "level2");
    writer.WriteNewGame(levels, "testGame");
    FileInputStream inputStream = new FileInputStream("test/TestData/testGame.properties");
    Properties p = new Properties();
    p.load(inputStream);
    Map<String, String> correct = new HashMap<>();
    correct.put("0", "level1");
    correct.put("1", "level2");
    assertEquals(correct, p);
  }

  @Test
  public void WriteGridCSVMakesCSV() throws IOException {
    resourcePaths.put("Grid", "test/TestData/movingTestLocation/");
    Writer writer = new Writer(resourcePaths);
    File f = new File("test/TestData/grid1.csv");
    writer.WriteNewGrid(f);
    File dir = new File("test/TestData/movingTestLocation");
    List<String> testFilesCreated = Arrays.asList(dir.list());
    assert (testFilesCreated.contains("grid1.csv"));
    resourcePaths.put("Grid", "test/TestData/");
    writer = new Writer(resourcePaths);
    f = new File("test/TestData/movingTestLocation/grid1.csv");
    writer.WriteNewGrid(f);
  }

  private void makeVariationPropertiesFile(Writer writer) throws IOException {
    List<String> winCond = Arrays.asList("Win");
    List<String> lossCond = Arrays.asList("Loss");
    writer.WriteNewVariation(winCond, lossCond, "testVariation");
  }

  private void makeThemePropertiesFile(Writer writer) throws IOException {
    Map<String, File> themeImages = new HashMap<>();
    File empty = new File("data/images/empty.png");
    themeImages.put("empty", empty);
    writer.WriteNewTheme(themeImages, "testTheme");
  }

}