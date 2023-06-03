package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import controller.Controller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AbstractPlayerTest {

  private PlayerMock playerMock;

  @BeforeEach
  public void setUp() {
    Controller controller = new Controller();
    playerMock = new PlayerMock(controller);
  }

  @Test
  public void testReturnShipList() {
    assertEquals(new ArrayList<>(), playerMock.returnShipList());
  }
  @Test
  public void testReturnAllDamagedCoord() {
    assertEquals(new ArrayList<>(), playerMock.returnAllDamagedCoord());
  }
  @Test
  public void testReturnAllMissCoord() {
    assertEquals(new ArrayList<>(), playerMock.returnAllMissCoord());
  }


  @Test
  public void testUnsunkShips() {
    // Set up a sample ship list
    List<Ship> shipList = new ArrayList<>();

    // Create a ship with all damaged coordinates
    List<Coord> damagedCoords = new ArrayList<>();
    damagedCoords.add(new Coord(1, 2));
    damagedCoords.add(new Coord(3, 4));
    Ship sunkShip = new Ship(damagedCoords, ShipType.BATTLESHIP);
    shipList.add(sunkShip);
    playerMock.allDamagedCoord = damagedCoords;

    // Create a ship with some undamaged coordinates
    List<Coord> undamagedCoords = new ArrayList<>();
    undamagedCoords.add(new Coord(5, 6));
    undamagedCoords.add(new Coord(7, 8));
    Ship unsunkShip = new Ship(undamagedCoords, ShipType.CARRIER);
    shipList.add(unsunkShip);

    // Set the ship list in the player model
    playerMock.shipList = shipList;

    // Call the unsunkShips method
    int result = playerMock.unsunkShips();

    // Assert that the result is the expected number of unsunk ships
    assertEquals(1, result);
  }

  @Test
  public void testReportMisses() {
    // Set up a sample ship list
    List<Ship> shipList = new ArrayList<>();

    // Create a ship with damaged coordinates
    List<Coord> damagedCoords = new ArrayList<>();
    damagedCoords.add(new Coord(1, 2));
    damagedCoords.add(new Coord(3, 4));
    Ship damagedShip = new Ship(damagedCoords, ShipType.BATTLESHIP);
    shipList.add(damagedShip);

    // Set the ship list in the player model
    playerMock.shipList = shipList;

    // Create a list of opponent shots with hits and misses
    List<Coord> opponentShots = new ArrayList<>();
    opponentShots.add(new Coord(1, 2));  // Hit on damaged ship
    opponentShots.add(new Coord(5, 6));  // Miss

    // Call the reportMisses method
    List<Coord> result = playerMock.reportMisses(opponentShots);

    // Assert that the result contains only the missed shots
    assertEquals(1, result.size());
    Assertions.assertTrue(result.contains(new Coord(5, 6)));
  }

  @Test
  public void testReportMissesNull() {
    assertEquals(new ArrayList<>(), playerMock.reportMisses(null));
  }

}
class PlayerMock extends AbstractPlayer {
  public PlayerMock(Controller controller) {
    super(controller);
  }
}
