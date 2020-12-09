package controller.Exceptions;

public class PropertyExceptions {

  public static class IncorrectGamePropertyFile extends Exception {

    public IncorrectGamePropertyFile(String filepath) {
      super(String.format(
          "The chosen game properties file  %s has one or more missing level properties files",
          filepath));
    }
  }

  public static class IncorrectLevelPropertyFile extends Exception {

    public IncorrectLevelPropertyFile(String filepath) {
      super(String.format("The chosen game properties file  %s has one or more missing elements",
          filepath));
    }
  }
}
