package view.Splashscreen;

import controller.Alerts;
import java.util.Arrays;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Abstract class which defines the structure of all dropdown box components on both splashscreens.
 * Extends HBox to contain a label and a choice box of selection options.
 * @author Heather Grune hlg20)
 */
public abstract class SelectionBox extends HBox {

  private final Alerts alerts = new Alerts();
  protected Text myLabel;
  protected String mySelection;
  private ChoiceBox myChoiceBox;
  private ObservableList myChoices;

  public SelectionBox(String[] selectionChoices, Map<String, String> labels) {
    initialize(selectionChoices, labels);
  }

  public SelectionBox(Map<String, String[]> options, Map<String, String> labels) {
    String[] selectionChoices = getChoices(options);
    initialize(selectionChoices, labels);
  }

  private void initialize(String[] selectionChoices, Map<String, String> labels) {
    this.myLabel = new Text(getLabel(labels));
    this.myChoiceBox = new ChoiceBox();
    addChoices(selectionChoices);

    this.getChildren().add(myLabel);
    this.getChildren().add(myChoiceBox);
    this.setAlignment(Pos.CENTER);
  }

  private String getLabel(Map<String, String> labels) {
    return labels.get(this.getClass().getSimpleName() + "Label");
  }

  private String[] getChoices(Map<String, String[]> options) {
    if (options != null) {
      return options.get(this.getClass().getSimpleName());
    }
    return new String[0];
  }

  private void addChoices(String[] choices) {
    myChoices = FXCollections.observableArrayList(Arrays.asList(choices));
    myChoiceBox.setItems(myChoices);

    myChoiceBox.getSelectionModel().selectedIndexProperty()
        .addListener(
            (observable, oldValue, newValue) -> setOnSelection(newValue.intValue()));
  }

  protected void setOnSelection(int selectedIndex) {
    try {
      mySelection = (String) myChoices.get(selectedIndex);
    } catch (Exception e) {
      alerts.makeAlert(e.getMessage());
    }
  }


  /**
   * Accessor for the user's selection.
   * @return the selected option
   */
  public String getMySelection() {
    return mySelection;
  }

  /**
   * Accessor for the dropdown
   * @return ChoiceBox component
   */
  public ChoiceBox getMyChoiceBox() {
    return this.myChoiceBox;
  }
}
