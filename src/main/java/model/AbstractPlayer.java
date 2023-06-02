package model;

import controller.Controller;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPlayer {
  List<Ship> shipList;
  List<Coord> allDamagedCoord;
  List<Coord> allMissCoord;
  Controller controller;
  Player player;

  public AbstractPlayer(Controller controller) {
    this.shipList = new ArrayList<>();
    this.allDamagedCoord = new ArrayList<>();
    this.allMissCoord = new ArrayList<>();
    this.controller = controller;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public List<Ship> returnShipList() {
    return shipList;
  }

  public List<Coord> returnAllDamagedCoord() {
    return allDamagedCoord;
  }

  public List<Coord> returnAllMissCoord() {
    return allMissCoord;
  }

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
