package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Coord;
import model.Ship;
import model.ShipType;

/**
 * The AiView class represents the view for the AI player in the BattleSalvo game.
 * It provides methods to choose dimensions, take shots, show the board, and select a fleet.
 */
public class AiView implements Viewable {
  /**
   * The height of the game board.
   */
  private final int height;
  /**
   * The width of the game board.
   */
  private final int width;
  /**
   * The smaller dimension between height and width.
   */
  int smallerDim;

  /**
   * Constructs an AiView object with the specified height and width.
   *
   * @param height the height of the game board
   * @param width the width of the game board
   */
  public AiView(int height, int width) {
    this.height = height;
    this.width = width;
  }

  /**
   * Allows the AI to choose the dimensions of the game board.
   *
   * @return an array of integers representing the chosen dimensions [height, width]
   */
  @Override
  public int[] chooseDimensions() {
    return null;
  }

  /**
   * Generates a list of shots for the AI to take on the game board.
   *
   * @param unsunkShips the number of unsunk ships remaining
   * @param damaged a list of coordinates representing the previously damaged shots
   * @param missed a list of coordinates representing the previously missed shots
   * @return a list of coordinates representing the AI's chosen shots
   */
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
  /**
   * Generates a random shot on the game board.
   *
   * @return a random shot coordinate
   */
  private Coord getRandomShot() {
    int xCoord = (int) (Math.random() * width);
    int yCoord = (int) (Math.random() * height);
    return new Coord(xCoord, yCoord);
  }

  /**
   * Displays the game board with the opponent's ships, hits, and misses.
   *
   * @param board a list of Ship objects representing the opponent's ships on the board
   * @param hits a list of coordinates representing the hit shots
   * @param misses a list of coordinates representing the missed shots
   */
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

  /**
   * Allows the AI to select its fleet composition.
   *
   * @return a map containing the ship types and their corresponding quantities
   */
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
