package model;

/**
 * The ShipType enumeration represents the different types of ships in the game.
 */
public enum ShipType {

  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3);

  final int shipSize;
  /**
   * Constructs a ShipType enum constant with the specified ship size.
   *
   * @param shipSize the size of the ship
   */
  ShipType(int shipSize){
    this.shipSize = shipSize;
  }

}
