package view;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import model.Coord;
import model.ShipType;

public class PlayerView implements Viewable {
  private final Scanner scanner = new Scanner(System.in);
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


    values[0] = scanner.nextInt();
    values[1] = scanner.nextInt();

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

    try {
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


    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Uh Oh! You've entered an invalid fleet size.\n" +
          "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n" +
          "Remember, your fleet may not exceed size " + smallerDim + ".\n" +
          "--------------------------------------------------------------------------------");
      fleetSelectionLogic();

    }

    return map;
  }


  @Override
  public void takeShots() {


  }

  @Override
  public void boardData() {

  }


}
