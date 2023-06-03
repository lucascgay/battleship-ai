package model;

import controller.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The AiModel class represents an AI player in the game of BattleSalvo. It implements the Player interface
 * and provides the logic for AI player actions and behaviors.
 */
public class AiModel extends AbstractPlayer implements Player {
  /**
   * The name of the AI player.
   */
  String name = "AI";
  /**
   * The PlayerModel instance associated with the AI player.
   */
  PlayerModel playerModel;

  /**
   * Constructs an AiModel object with the specified controller.
   *
   * @param controller the controller object associated with the AI player.
   */
  public AiModel(Controller controller) {
    super(controller);
  }

  /**
   * Returns the name of the AI player.
   *
   * @return the name of the AI player.
   */
  @Override
  public String name() {
    return name;
  }

  /**
   * Given the specifications for a BattleSalvo board, randomly generates the positions of the ships
   * on the board and returns the list of ships.
   *
   * @param height the height of the board, range: [6, 15] inclusive
   * @param width the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    List<Ship> shipPositions = new ArrayList<>();
    int rowOffset = 0;

    if (height > width) {
      for (ShipType shipType : ShipType.values()) {
        int space = height - shipType.shipSize + 1;
        int numShips = specifications.getOrDefault(shipType, 0);

        for (int i = 0; i < numShips; i++) {
          int startingPoint = (int) (Math.random() * space);
          List<Coord> coords = new ArrayList<>();

          for (int k = startingPoint; k < startingPoint + shipType.shipSize; k++) {
            coords.add(new Coord(rowOffset + i, k));
          }

          Ship ship = new Ship(coords, shipType);
          shipPositions.add(ship);
        }

        rowOffset += numShips;
      }
    } else {
      for (ShipType shipType : ShipType.values()) {
        int space = width - shipType.shipSize + 1;
        int numShips = specifications.getOrDefault(shipType, 0);

        for (int i = 0; i < numShips; i++) {
          int startingPoint = (int) (Math.random() * space);
          List<Coord> coords = new ArrayList<>();

          for (int k = startingPoint; k < startingPoint + shipType.shipSize; k++) {
            coords.add(new Coord(k, rowOffset + i));
          }

          Ship ship = new Ship(coords, shipType);
          shipPositions.add(ship);
        }

        rowOffset += numShips;
      }
    }

    shipList = shipPositions;
    return shipPositions;
  }

  /**
   * Generates the AI player's shots on the opponent's board.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    return controller.aiCallTakeShot();
  }

  /**
   * Given the list of shots the opponent has fired on the AI player's board, reports which shots hit
   * a ship on the AI player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on the AI player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a ship
   * on the AI player's board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {

    if (opponentShotsOnBoard == null) {
      return new ArrayList<>();
    } else {
      List<Coord> damageCoord = new ArrayList<>();

      for (Coord shot : opponentShotsOnBoard) {
        for (Ship ship : shipList) {
          List<Coord> temp = ship.coords;
          for (Coord coord : temp) {

            if ((shot.x == coord.x) && (shot.y == coord.y)) {
              damageCoord.add(shot);
            }
          }
        }
      }

      allDamagedCoord.addAll(damageCoord);
      return allDamagedCoord;
    }
  }

  /**
   * Notifies the AI player of the shots in their previous volley that successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    playerModel.reportDamage(playerModel.takeShots());
  }

  /**
   * Notifies the AI player that the game is over.
   *
   * @param result if the AI player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {

  }
}
