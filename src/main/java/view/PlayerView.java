package view;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import model.Coord;
import model.Ship;
import model.ShipType;

public class PlayerView implements Viewable {
  private final Scanner scanner = new Scanner(System.in);
  private int height;
  private int width;
  int smallerDim;

  @Override
  public int[] chooseDimensions() {
    System.out.println("Hello! Welcome to the OOD BattleSalvo Game! \n");
    System.out.println(
        "Please enter a valid height and width below:\n" +
            "------------------------------------------------------");
    int[] values = new int[2];
    return chooseDimensionsLogic(values);
  }

  private int[] chooseDimensionsLogic(int[] values) {

    height = scanner.nextInt();
    width = scanner.nextInt();

    values[0] = height;
    values[1] = width;

    if (!(values[0] >= 6 && values[0] <= 15) || !(values[1] >= 6 && values[1] <= 15)) {
      System.out.println(
          "Uh Oh! You've entered invalid dimensions. Please remember that the height and width\n" +
              "of the game must be in the range (6, 15), inclusive. Try again!\n" +
              "------------------------------------------------------");
      chooseDimensionsLogic(values);
    }

    smallerDim = Math.min(values[0], values[1]);

    return values;
  }

  @Override
  public Map<ShipType, Integer> fleetSelection() {
    System.out.println(
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n" +
            "Remember, your fleet may not exceed size " + smallerDim + ".\n" +
            "--------------------------------------------------------------------------------");

    return fleetSelectionLogic();
  }

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
      fleetSelectionLogic();
    }
    map.put(ShipType.CARRIER, values[0]);
    map.put(ShipType.BATTLESHIP, values[1]);
    map.put(ShipType.DESTROYER, values[2]);
    map.put(ShipType.SUBMARINE, values[3]);


    return map;
  }



  @Override
  public List<Coord> takeShots(int unsunkShips, List<Coord> damaged, List<Coord> missed) {

    System.out.println("Please Enter " + unsunkShips + " Shots:\n" +
        "------------------------------------------------------------------");

    return takeShotsLogic(unsunkShips, damaged, missed);
  }
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
      } else if (damaged.contains(shot) || missed.contains(shot)) {
        System.out.println("You already fired this shot:" +
            "\n------------------------------------------------------------------");
        return takeShotsLogic(unsunkShips, damaged, missed);
      }

      shotList.add(shot);
    }

    return shotList;
  }

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
