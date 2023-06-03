package model;

import static org.junit.jupiter.api.Assertions.*;

import controller.Controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AiModelTest {
  private AiModel aiModel;

  @BeforeEach
  public void setup() {
    aiModel = new AiModel(new Controller());
  }


  @Test
  public void testSetupWidthBigger() {
    int height = 10;
    int width = 10;
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 2);
    specifications.put(ShipType.DESTROYER, 3);
    specifications.put(ShipType.SUBMARINE, 4);

    List<Ship> shipPositions = aiModel.setup(height, width, specifications);

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
    int height = 12;
    int width = 10;
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 2);
    specifications.put(ShipType.DESTROYER, 3);
    specifications.put(ShipType.SUBMARINE, 4);

    List<Ship> shipPositions = aiModel.setup(height, width, specifications);

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
    // Create a sample list of opponent shots
    List<Coord> opponentShots = new ArrayList<>();
    opponentShots.add(new Coord(1, 2));
    opponentShots.add(new Coord(3, 4));
    opponentShots.add(new Coord(5, 6));

    // Set up a sample ship list
    List<Ship> shipList = new ArrayList<>();
    List<Coord> shipCoords = new ArrayList<>();
    shipCoords.add(new Coord(1, 2));
    shipCoords.add(new Coord(3, 4));
    Ship ship = new Ship(shipCoords, ShipType.BATTLESHIP);
    shipList.add(ship);

    // Set the ship list in the player model
    aiModel.shipList = shipList;

    // Call the reportDamage method
    List<Coord> result = aiModel.reportDamage(opponentShots);

    // Assert that the result contains the damaged coordinates
    assertEquals(2, result.size());
    assertTrue(result.contains(new Coord(1, 2)));
    assertTrue(result.contains(new Coord(3, 4)));
  }

  @Test
  public void testReportDamageNull() {
    assertEquals(new ArrayList<>(), aiModel.reportDamage(null));
  }

}