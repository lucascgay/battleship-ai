package model;

import controller.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Model class represents a player model in the game of BattleSalvo. It implements the Player interface
 * and provides the logic for player actions and behaviors.
 */
public class PlayerModel extends AbstractPlayer implements Player {
  /**
   * The name of the player.
   */
  String name;
  /**
   * The AiModel instance associated with the player.
   */
  AiModel aiModel;
  /**
   * Constructs a Model object with the specified controller.
   *
   * @param controller the controller object associated with the player.
   */
  public PlayerModel(Controller controller) {
    super(controller);
  }

  /**
   * Returns the name of the player.
   *
   * @return the name of the player.
   */
  @Override
  public String name() {
    return name;
  }

  /**
   * Given the specifications for a BattleSalvo board, allows the player to manually position their ships
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

        for (int i = 0; i < specifications.get(shipType); i++) {
          int startingPoint = (int) (Math.random() * space);
          List<Coord> coords = new ArrayList<>();

          for (int k = startingPoint; k < startingPoint + shipType.shipSize; k++) {
            coords.add(new Coord(rowOffset + i, k));
          }

          Ship ship = new Ship(coords, shipType);
          shipPositions.add(ship);
        }

        rowOffset += specifications.get(shipType);
      }
    } else {
      for (ShipType shipType : ShipType.values()) {
        int space = width - shipType.shipSize + 1;

        for (int i = 0; i < specifications.get(shipType); i++) {
          int startingPoint = (int) (Math.random() * space);
          List<Coord> coords = new ArrayList<>();

          for (int k = startingPoint; k < startingPoint + shipType.shipSize; k++) {
            coords.add(new Coord(k, rowOffset + i));
          }

          Ship ship = new Ship(coords, shipType);
          shipPositions.add(ship);
        }

        rowOffset += specifications.get(shipType);
      }
    }

    shipList = shipPositions;
    return shipPositions;
  }

  /**
   * Generates the player's shots on the opponent's board.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    return controller.playerCallTakeShot();
  }

  /**
   * Given the list of shots the opponent has fired on the player's board, reports which shots hit
   * a ship on the player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on the player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a ship
   * on the player's board
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
   * Notifies the player of the shots in their previous volley that successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    aiModel.reportDamage(aiModel.takeShots());
  }

  /**
   * Notifies the player that the game is over.
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {

  }
}
