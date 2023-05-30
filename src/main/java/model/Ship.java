package model;

import java.util.List;

public class Ship {
  List<Coord> coords;
  ShipType shipType;
  public Ship(List<Coord> coords, ShipType shipType) {
    this.coords = coords;
    this.shipType = shipType;
  }
}
