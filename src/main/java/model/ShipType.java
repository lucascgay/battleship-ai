package model;

public enum ShipType {

  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3);

  final int shipSize;
  ShipType(int shipSize){
    this.shipSize = shipSize;
  }



}
