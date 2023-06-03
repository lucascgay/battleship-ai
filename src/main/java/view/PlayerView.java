package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import model.Coord;
import model.Ship;
import model.ShipType;

/**
 * The PlayerView class implements the Viewable interface and represents the view component for the player in the BattleSalvo game.
 * It provides methods to choose dimensions, take shots, show the board, and select a fleet.
 */
public class PlayerView implements Viewable {
  private Scanner scanner;
  private int height;
  private int width;
  int smallerDim;
  /**
   * Setter method used for dependency injeciton.
   *
   */
  public PlayerView() {
    this.scanner = new Scanner(System.in);
  }
  /**
   * Method used to set the scanner in the playerview class.
   * @param scanner passes in the scanner object.
   */
  public void setScanner(Scanner scanner) {
    this.scanner = scanner;
  }

  /**
   * Allows the user to choose the dimensions of the game board.
   *
   * @return an array of integers representing the chosen dimensions [height, width]
   */
  @Override
  public int[] chooseDimensions() {
    System.out.println("Hello! Welcome to the OOD BattleSalvo Game! \n");
    System.out.println(
        "Please enter a valid height and width below:\n" +
            "------------------------------------------------------");
    int[] values = new int[2];
    return chooseDimensionsLogic(values);
  }
  /**
   * Helper for choose dimensions that uses recursion and checks values are within range.
   *
   * @return an array containing the ship types and their corresponding quantities
   */
  private int[] chooseDimensionsLogic(int[] values) {
    height = scanner.nextInt();
    width = scanner.nextInt();
    values[0] = height;
    values[1] = width;

    if (!(values[0] >= 6 && values[0] <= 15) || !(values[1] >= 6 && values[1] <= 15)) {
      System.out.println(
          """
              Uh Oh! You've entered invalid dimensions. Please remember that the height and width
              of the game must be in the range (6, 15), inclusive. Try again!
              ------------------------------------------------------""");
      return chooseDimensionsLogic(values);
    }

    smallerDim = Math.min(values[0], values[1]);
    return values;
  }

  /**
   * Displays the prompt for fleet selection and retrieves the fleet choices from the user.
   *
   * @return a map containing the ship types and their corresponding quantities
   */
  @Override
  public Map<ShipType, Integer> fleetSelection() {
    System.out.println(
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n" +
            "Remember, your fleet may not exceed size " + smallerDim + ".\n" +
            "--------------------------------------------------------------------------------");

    return fleetSelectionLogic();
  }

  /**
   * Helper for fleetSelection that uses recursion and checks fleet selected is within bounds.
   *
   */
  private Map<ShipType, Integer> fleetSelectionLogic() {
    int total = 0;
    Map<ShipType, Integer> map = new HashMap<>();
    int[] values = new int[4];

    for (int i = 0; i < 4; i++) {
      int temp = scanner.nextInt();
      total += temp;
      values[i] = temp;
    }
    if (total > smallerDim) {
      System.out.println("Uh Oh! You've entered invalid fleet sizes.\n" +
          "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n" +
          "Remember, your fleet may not exceed size " + smallerDim + ".\n" +
          "--------------------------------------------------------------------------------");
      return fleetSelectionLogic();
    }
    map.put(ShipType.CARRIER, values[0]);
    map.put(ShipType.BATTLESHIP, values[1]);
    map.put(ShipType.DESTROYER, values[2]);
    map.put(ShipType.SUBMARINE, values[3]);


    return map;
  }

  /**
   * Displays the prompt for taking shots on the game board and retrieves the shots from the user.
   *
   * @param unsunkShips the number of unsunk ships
   * @param damaged a list of coordinates representing the previously damaged shots
   * @param missed a list of coordinates representing the previously missed shots
   * @return a list of coordinates representing the shots chosen by the user
   */
  @Override
  public List<Coord> takeShots(int unsunkShips, List<Coord> damaged, List<Coord> missed) {

    System.out.println("Please Enter " + unsunkShips + " Shots:\n" +
        "------------------------------------------------------------------");

    return takeShotsLogic(unsunkShips, damaged, missed);
  }
  /**
   * Helper for takeShots which checks shots are within bounds.
   *
   * @param unsunkShips the number of unsunk ships
   * @param damaged a list of coordinates representing the previously damaged shots
   * @param missed a list of coordinates representing the previously missed shots
   * @return a list of coordinates representing the shots chosen by the user
   */
  private List<Coord> takeShotsLogic(int unsunkShips, List<Coord> damaged, List<Coord> missed) {

    List<Coord> shotList = new ArrayList<>();

    for (int i = 0; i < unsunkShips; i++) {
      int xCoord = scanner.nextInt();
      int yCoord = scanner.nextInt();
      Coord shot = new Coord(xCoord, yCoord);
      if ((shot.y >= height) || (shot.x >= width)) {
        System.out.println("Please enter values within height:" + height + " width: " + width +
            "\n------------------------------------------------------------------");
        return takeShotsLogic(unsunkShips, damaged, missed);
      } else if (damaged.contains(shot) || missed.contains(shot) || shotList.contains(shot)) {
        System.out.println("You already fired this shot:" +
            "\n------------------------------------------------------------------");
        return takeShotsLogic(unsunkShips, damaged, missed);
      }

      shotList.add(shot);
    }

    return shotList;
  }

  /**
   * Displays the game board with the ships, hits, and misses.
   *
   * @param board a list of Ship objects representing the ships on the board
   * @param hits a list of coordinates representing the hit shots
   * @param misses a list of coordinates representing the missed shots
   */
  @Override
  public void showBoard(List<Ship> board, List<Coord> hits, List<Coord> misses) {
    System.out.println("\nYour Board:");
    String[][] arrayBoard = new String[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        arrayBoard[i][j] = "O";
      }
    }
    for (Ship ship : board) {
      List<Coord> temp = ship.coords;
      for (Coord coords : temp) {
        arrayBoard[coords.y][coords.x] = "S";
      }
    }
    for (Coord coord : hits) {
      arrayBoard[coord.y][coord.x] = "H";
    }
    for (Coord coord : misses) {
      arrayBoard[coord.y][coord.x] = "M";
    }
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        System.out.print(arrayBoard[i][j]);
      }
      System.out.print("\n");
    }

  }
}
