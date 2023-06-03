package model;

import java.util.List;

/**
 * The Ship class represents a ship in the game.
 */
public class Ship {
  public List<Coord> coords;
  ShipType shipType;

  /**
   * Constructs a Ship object with the specified coordinates and ship type.
   *
   * @param coords    the coordinates occupied by the ship
   * @param shipType  the type of the ship
   */
  public Ship(List<Coord> coords, ShipType shipType) {
    this.coords = coords;
    this.shipType = shipType;
  }
}
