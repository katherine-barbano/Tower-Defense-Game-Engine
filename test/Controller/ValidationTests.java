package Controller;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import controller.DataAccess.ResourceAccessor;
import controller.DataAccess.Validation.Validation;
import controller.Exceptions.GeneralExceptions;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ValidationTests {

  Validation validation = new Validation(new ResourceAccessor());

  @Test
  public void checkCustomGameThrowsErrorWithNullName() {
    List<String> selections = Arrays.asList("level1", "level2");
    assertThrows(GeneralExceptions.NullNameException.class,
        () -> validation.checkCustomGame(selections, ""));

  }

  @Test
  public void checkCustomGameThrowsErrorWithNoLevels() {
    List<String> selections = Arrays.asList(new String[]{});
    assertThrows(GeneralExceptions.EmptySelectionException.class,
        () -> validation.checkCustomGame(selections, "exampleGameName"));
  }

  @Test
  public void checkCustomGameDoesNotThrowErrorWithLevelsAndName() {
    List<String> selections = Arrays.asList("level1");
    assertDoesNotThrow(() -> validation.checkCustomGame(selections, "exampleGameName"));
  }

  @Test
  public void checkCustomLevelDoesNotThrowErrorWithNameGiven() {
    assertDoesNotThrow(() -> validation.checkCustomLevel("exampleGamelevel"));
  }

  @Test
  public void checkCustomLevelThrowsErrorWithEmptyNameField() {
    assertThrows(GeneralExceptions.NullNameException.class, () -> validation.checkCustomLevel(""));
  }

  @Test
  public void checkCustomGridCSVDoesNotThrowErrorWhenContainingValues() {
    assertDoesNotThrow(
        () -> validation.checkCustomGridCSV(new File("data/grids/grid1.csv")));
  }

  @Test
  public void checkCustomGridCSVThrowsErrorWhenEmpty() {
    assertThrows(GeneralExceptions.EmptyCSVException.class,
        () -> validation.checkCustomGridCSV(new File("data/grids/empty.csv")));
  }

  @Test
  public void checkCustomThemeThrowsErrorWithNoSelections() {
    Map<String, File> selections = new HashMap<>();
    assertThrows(GeneralExceptions.EmptySelectionException.class,
        () -> validation.checkCustomTheme(selections, "exampleGameName"));
  }

  @Test
  public void checkCustomThemeThrowsErrorWithNoName()
      throws IOException, GeneralExceptions.NullNameException, GeneralExceptions.EmptySelectionException, GeneralExceptions.UnsupportedLevelException {
    Map<String, File> selections = new HashMap<>();
    assertThrows(GeneralExceptions.NullNameException.class,
        () -> validation.checkCustomTheme(selections, ""));
  }

  @Test
  public void checkCustomThemeDoesNotThrowErrorWithSelectionAndName() throws IOException {
    Map<String, File> selections = new HashMap<>();
    String path = "data/images/empty.png";
    File file = new File(path);
    selections.put("sample", file);
    assertDoesNotThrow(() -> validation.checkCustomTheme(selections, "something"));
  }

  @Test
  public void checkCustomVariationThrowsErrorWithNoSelections() {
    assertThrows(GeneralExceptions.EmptySelectionException.class, () -> validation
        .checkCustomVariations(new ArrayList<>(), new ArrayList<>(), "exampleGameName"));
  }

  @Test
  public void checkCustomVariationThrowsErrorWithNoName() {
    List<String> WinConditions = Arrays.asList("something");
    List<String> LossConditions = Arrays.asList("something");
    assertThrows(GeneralExceptions.NullNameException.class,
        () -> validation.checkCustomVariations(WinConditions, LossConditions, ""));
  }

  @Test
  public void checkCustomVariationDoesNotThrowErrorWithSelectionAndName() {
    List<String> WinConditions = Arrays.asList("something");
    List<String> LossConditions = Arrays.asList("something");
    assertDoesNotThrow(
        () -> validation.checkCustomVariations(WinConditions, LossConditions, "something"));
  }
}