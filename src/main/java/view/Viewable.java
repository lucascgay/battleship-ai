package view;

import java.util.List;
import java.util.Map;
import model.Coord;
import model.Ship;
import model.ShipType;

public interface Viewable {

  int[] chooseDimensions();
  /**
   * Displays the prompt for fleet selection.
   *
   */
  List<Coord> takeShots(int unsunkShips, List<Coord> damaged, List<Coord> missed);
  /**
   * Displays the board data according to current 2D array.
   *
   */
  void showBoard(List<Ship> board, List<Coord> hits, List<Coord> misses);

  Map<ShipType, Integer> fleetSelection();



}
