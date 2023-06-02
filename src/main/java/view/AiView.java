package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Coord;
import model.Ship;
import model.ShipType;

public class AiView implements Viewable {
  private final int height;
  private final int width;
  int smallerDim;

  public AiView(int height, int width) {
    this.height = height;
    this.width = width;
  }

  @Override
  public int[] chooseDimensions() {
    return null;
  }

  @Override
  public List<Coord> takeShots(int unsunkShips, List<Coord> damaged, List<Coord> missed) {
    List<Coord> chosenShots = new ArrayList<>();

    while (chosenShots.size() < unsunkShips) {
      Coord shot = getRandomShot();
      if (!chosenShots.contains(shot) && !damaged.contains(shot) && !missed.contains(shot)) {
        chosenShots.add(shot);
      }
    }

    return new ArrayList<>(chosenShots);
  }
  private Coord getRandomShot() {
    int xCoord = (int) (Math.random() * width);
    int yCoord = (int) (Math.random() * height);
    return new Coord(xCoord, yCoord);
  }


  @Override
  public void showBoard(List<Ship> board, List<Coord> hits, List<Coord> misses) {
    System.out.println("\nOpponent Board Data:");
    String[][] arrayBoard = new String[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        arrayBoard[i][j] = "O";
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


  @Override
  public Map<ShipType, Integer> fleetSelection() {

    Map<ShipType, Integer> map = new HashMap<>();
    smallerDim = Math.min(height, width);
    int submarines = smallerDim - 3;

    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, submarines);

    return map;
  }
}
