package controller.Managers;

import java.util.Map;


/**
 * Defines functionality for setting up a game, this extends this functionality beyond even games
 * and could also set up any number of applications such as maps, video streaming services, etc.
 *
 * @author Megan Richards
 */
public interface SetupManager {

  Map<String, String> getSelections();

  boolean isSelectionComplete();
}
