package view;

import java.util.List;
import java.util.Map;
import model.Coord;
import model.ShipType;

public interface Viewable {

  int[] chooseDimensions();
  /**
   * Displays the prompt for fleet selection.
   *
   */
  void takeShots();

  /**
   * Displays the board data according to current 2D array.
   *
   */
  void boardData();

  Map<ShipType, Integer> fleetSelection();



}
