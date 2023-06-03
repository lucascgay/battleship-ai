package model;

import controller.Controller;
import java.util.ArrayList;
import java.util.List;

/**
 * The AbstractPlayer class represents an abstract player in the game. It provides common functionality and data
 * that can be inherited and used by specific player implementations.
 */
public abstract class AbstractPlayer {
  /**
   * The list of ships belonging to the player.
   */
  List<Ship> shipList;
  /**
   * The list of coordinates representing all the damaged shots made by the player.
   */
  List<Coord> allDamagedCoord;
  /**
   * The list of coordinates representing all the missed shots made by the player.
   */
  List<Coord> allMissCoord;
  /**
   * The controller associated with the player.
   */
  Controller controller;
  /**
   * The player instance associated with the player.
   */
  Player player;

  /**
   * Constructs an AbstractPlayer object with the specified controller.
   *
   * @param controller the controller object associated with the player.
   */
  public AbstractPlayer(Controller controller) {
    this.shipList = new ArrayList<>();
    this.allDamagedCoord = new ArrayList<>();
    this.allMissCoord = new ArrayList<>();
    this.controller = controller;
  }

  /**
   * Sets the player instance associated with the player.
   *
   * @param player the player instance associated with the player.
   */
  public void setPlayer(Player player) {
    this.player = player;
  }

  /**
   * Returns the list of ships belonging to the player.
   *
   * @return the list of ships belonging to the player.
   */
  public List<Ship> returnShipList() {
    return shipList;
  }

  /**
   * Returns the list of coordinates representing all the damaged shots made by the player.
   *
   * @return the list of coordinates representing all the damaged shots made by the player.
   */
  public List<Coord> returnAllDamagedCoord() {
    return allDamagedCoord;
  }

  /**
   * Returns the list of coordinates representing all the missed shots made by the player.
   *
   * @return the list of coordinates representing all the missed shots made by the player.
   */
  public List<Coord> returnAllMissCoord() {
    return allMissCoord;
  }

  /**
   * Returns the number of unsunk ships belonging to the player.
   *
   * @return the number of unsunk ships belonging to the player.
   */
  public int unsunkShips() {
    List<Ship> sunkShips = new ArrayList<>();

    for (Ship ship : shipList) {
      boolean isSunk = true;
      for (Coord coord : ship.coords) {
        if (! allDamagedCoord.contains(coord)) {
          isSunk = false;
          break;
        }
      }
      if (isSunk) {
        sunkShips.add(ship);
      }
    }

    return shipList.size() - sunkShips.size();
  }

  /**
   * Reports the missed shots made by the opponent on the player's board.
   *
   * @param opponentShotsOnBoard the list of coordinates representing the opponent's shots on the player's board.
   * @return the list of coordinates representing the missed shots made by the opponent.
   */
  public List<Coord> reportMisses(List<Coord> opponentShotsOnBoard) {
    if (opponentShotsOnBoard == null) {
      return new ArrayList<>();
    } else {
      List<Coord> missCoord = new ArrayList<>();

      for (Coord shot : opponentShotsOnBoard) {
        boolean isHit = false;
        for (Ship ship : shipList) {
          List<Coord> temp = ship.coords;
          for (Coord coord : temp) {
            if (shot.x == coord.x && shot.y == coord.y) {
              isHit = true;
              break;
            }
          }
          if (isHit) {
            break;
          }
        }
        if (!isHit) {
          missCoord.add(shot);
        }
      }

      allMissCoord.addAll(missCoord);
      return allMissCoord;
    }
  }
}
