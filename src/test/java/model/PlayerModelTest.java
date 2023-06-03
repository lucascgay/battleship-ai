package model;

import controller.Controller;

import java.util.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerModelTest {

  private static PlayerModel playerModel;

  @BeforeAll
  public static void setUp() {
    Controller controller = new Controller();
    playerModel = new PlayerModel(controller);
  }

  @Test
  public void testReturnPlayerName() {
    String expectedName = "Player1";
    playerModel.name = expectedName;

    String actualName = playerModel.name();

    assertEquals(expectedName, actualName);
  }

  @Test
  public void testSetupWidthBigger() {
    PlayerModel playerModel = new PlayerModel(new Controller());
    int height = 10;
    int width = 10;
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 2);
    specifications.put(ShipType.DESTROYER, 3);
    specifications.put(ShipType.SUBMARINE, 4);

    List<Ship> shipPositions = playerModel.setup(height, width, specifications);

    // Assert that the returned list has the correct number of ships
    assertEquals(10, shipPositions.size());

    // Assert that each ship in the list has the expected ship type
    for (Ship ship : shipPositions) {
      assertTrue(specifications.containsKey(ship.shipType));
    }

    // Assert that the ship positions are within the board boundaries
    for (Ship ship : shipPositions) {
      List<Coord> temp = ship.coords;
      for (Coord coord : temp) {
        assertTrue(coord.x >= 0 && coord.x < width);
        assertTrue(coord.y >= 0 && coord.y < height);
      }
    }
  }

  @Test
  public void testSetupHeightBigger() {
    PlayerModel playerModel = new PlayerModel(new Controller());
    int height = 12;
    int width = 10;
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 2);
    specifications.put(ShipType.DESTROYER, 3);
    specifications.put(ShipType.SUBMARINE, 4);

    List<Ship> shipPositions = playerModel.setup(height, width, specifications);

    // Assert that the returned list has the correct number of ships
    assertEquals(10, shipPositions.size());

    // Assert that each ship in the list has the expected ship type
    for (Ship ship : shipPositions) {
      assertTrue(specifications.containsKey(ship.shipType));
    }

    // Assert that the ship positions are within the board boundaries
    for (Ship ship : shipPositions) {
      List<Coord> temp = ship.coords;
      for (Coord coord : temp) {
        assertTrue(coord.x >= 0 && coord.x < width);
        assertTrue(coord.y >= 0 && coord.y < height);
      }
    }
  }

  @Test
  public void testReportDamage() {
    List<Coord> opponentShots = new ArrayList<>();
    opponentShots.add(new Coord(1, 2));
    opponentShots.add(new Coord(3, 4));

    List<Ship> shipList = new ArrayList<>();
    List<Coord> coords1 = new ArrayList<>();
    coords1.add(new Coord(1, 2));
    coords1.add(new Coord(1, 3));
    shipList.add(new Ship(coords1, ShipType.BATTLESHIP));
    List<Coord> coords2 = new ArrayList<>();
    coords2.add(new Coord(3, 4));
    coords2.add(new Coord(3, 5));
    shipList.add(new Ship(coords2, ShipType.DESTROYER));
    playerModel.shipList = shipList;

    List<Coord> damagedCoords = playerModel.reportDamage(opponentShots);

    assertNotNull(damagedCoords);
    assertEquals(2, damagedCoords.size());
  }

  @Test
  public void testReportDamageNull() {
    assertEquals(new ArrayList<>(), playerModel.reportDamage(null));
  }

}
