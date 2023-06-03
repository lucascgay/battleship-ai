package view;

import java.util.List;
import java.util.Map;
import model.Coord;
import model.Ship;
import model.ShipType;

/**
 * The Viewable interface defines the contract for the view components in the BattleSalvo game.
 * It provides methods to choose dimensions, take shots, show the board, and select a fleet.
 */
public interface Viewable {

  /**
   * Allows the user to choose the dimensions for the game board.
   *
   * @return an array of integers representing the chosen dimensions [height, width]
   */
  int[] chooseDimensions();

  /**
   * Displays the prompt for taking shots on the game board.
   *
   * @param unsunkShips the number of unsunk ships
   * @param damaged a list of coordinates representing the previously damaged shots
   * @param missed a list of coordinates representing the previously missed shots
   * @return a list of coordinates representing the shots chosen by the user
   */
  List<Coord> takeShots(int unsunkShips, List<Coord> damaged, List<Coord> missed);
  /**
   * Displays the game board with the ships, hits, and misses.
   *
   * @param board a list of Ship objects representing the ships on the board
   * @param hits a list of coordinates representing the hit shots
   * @param misses a list of coordinates representing the missed shots
   */
  void showBoard(List<Ship> board, List<Coord> hits, List<Coord> misses);

  /**
   * Allows the user to select the fleet of ships.
   *
   * @return a map containing the ship types and their corresponding quantities
   */
  Map<ShipType, Integer> fleetSelection();



}
