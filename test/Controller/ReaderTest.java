package Controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.opencsv.exceptions.CsvException;
import controller.DataAccess.Reader;
import controller.DataAccess.ResourceAccessor;
import controller.DataAccess.towerDefenseResourceAPI;
import controller.Exceptions.GeneralExceptions;
import controller.Exceptions.ReaderExceptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.junit.jupiter.api.Test;

public class ReaderTest {

  Reader reader = new Reader();
  towerDefenseResourceAPI resources = new ResourceAccessor();
  private Map<String, String> enemiesToClassNames = new HashMap<>();

  public Properties makeCorrectLanguageProperties() {
    Properties correct = new Properties();
    correct.put("english", "");
    correct.put("spanish", "");
    correct.put("french", "");
    return correct;
  }

  @Test
  public void getsCorrectPropertyKeys() throws IOException {
    Properties correct = makeCorrectLanguageProperties();
    Properties readValues = reader.getProperties("src/resources/general/Languages.properties");
    assert (correct.keySet().containsAll(readValues.keySet()));
    assert (correct.size() == readValues.size());
  }

  @Test
  public void getsCorrectPropertyValues() throws IOException {
    Properties correct = makeCorrectLanguageProperties();
    Properties readValues = reader.getProperties("src/resources/general/Languages.properties");
    assert (correct.values().containsAll(readValues.values()));
  }

  @Test
  public void getsCorrectCSVValues()
      throws IOException, CsvException, ReaderExceptions.IncorrectCSVFormat, GeneralExceptions.NullNameException {
    enemiesToClassNames = resources.getEnemyNameMap("basic");
    List<List<String>> initialCondition = reader
        .getEnemies("data/waves/board1.csv", enemiesToClassNames);

    List<List<String>> correct = new ArrayList<>();
    correct.add(Arrays.asList("0", "0", "0", "0", "0"));
    correct.add(Arrays.asList("0", "0", "BombEnemy", "0", "0"));
    correct.add(Arrays.asList("0", "FastShooterEnemy", "0", "0", "0"));
    correct.add(Arrays.asList("HighHealthEnemy", "0", "0", "0", "0"));
    correct.add(Arrays.asList("0", "0", "FastShooterEnemy", "0", "BasicEnemy"));
    correct.add(Arrays.asList("BasicEnemy", "0", "0", "0", "0"));
    correct.add(Arrays.asList("0", "0", "ShooterEnemy", "0", "0"));
    correct.add(Arrays.asList("0", "HighHealthEnemy", "0", "0", "0"));
    assertEquals(correct, initialCondition);
  }

  @Test
  public void throwsErrorWithMismatchingRows() {
    assertThrows(ReaderExceptions.IncorrectCSVFormat.class, () -> {
      reader.getEnemies("data/waves/incorrectRows.csv", enemiesToClassNames);
    });
  }

  @Test
  public void throwsErrorWithMismatchingColumns() {
    assertThrows(ReaderExceptions.IncorrectCSVFormat.class, () -> {
      reader.getEnemies("data/waves/incorrectColumns.csv", enemiesToClassNames);
    });
  }

  @Test
  public void noErrorThrownForAppropriateShapedEnemyCSV() {
    assertDoesNotThrow(() -> {
      reader.getEnemies("data/waves/board1.csv", enemiesToClassNames);
    });

  }

}