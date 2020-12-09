package controller.DataAccess.Validation;

import controller.Exceptions.GeneralExceptions.NoSelectionException;

public class setupValidation {

  public boolean isNonNull(String s) throws NoSelectionException {
    return !s.equals("");
  }
}
