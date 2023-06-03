package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Coord;
import model.Ship;
import model.ShipType;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AiViewTest {

  private AiView aiView;
  private List<Coord> damaged;
  private List<Coord> missed;
  private ByteArrayOutputStream outputStream;

  @BeforeEach
  public void setup() {
    aiView = new AiView(10, 10);
    damaged = new ArrayList<>();
    missed = new ArrayList<>();
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
  }

  @Test
  public void takeShots_UnsunkShipsGreaterThanZero_ReturnsShots() {
    List<Coord> shots = aiView.takeShots(4, damaged, missed);

    assertEquals(4, shots.size());
    for (Coord shot : shots) {
      assertFalse(damaged.contains(shot));
      assertFalse(missed.contains(shot));
    }
  }

  @Test
  public void takeShots_UnsunkShipsEqualToZero_ReturnsEmptyList() {
    List<Coord> shots = aiView.takeShots(0, damaged, missed);

    assertEquals(0, shots.size());
  }

  @Test
  public void showBoard_ValidData_PrintsBoard() {
    List<Ship> board = Arrays.asList(
        new Ship(Arrays.asList(new Coord(0, 0), new Coord(0, 1), new Coord(0, 2)), ShipType.CARRIER),
        new Ship(Arrays.asList(new Coord(1, 0), new Coord(1, 1), new Coord(1, 2)), ShipType.BATTLESHIP),
        new Ship(Arrays.asList(new Coord(2, 0), new Coord(2, 1)), ShipType.DESTROYER),
        new Ship(List.of(new Coord(3, 0)), ShipType.SUBMARINE)
    );
    List<Coord> hits = Arrays.asList(new Coord(0, 0), new Coord(1, 1));
    List<Coord> misses = Arrays.asList(new Coord(2, 2), new Coord(3, 3));

    aiView.showBoard(board, hits, misses);

    String expectedOutput = """

        Opponent Board Data:
        HOOOOOOOOO
        OHOOOOOOOO
        OOMOOOOOOO
        OOOMOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        """;

    assertEquals(expectedOutput, getConsoleOutput());
  }

  @Test
  public void fleetSelection_ValidInput_ReturnsFleetMap() {
    Map<ShipType, Integer> fleet = aiView.fleetSelection();

    assertEquals(1, fleet.get(ShipType.CARRIER));
    assertEquals(1, fleet.get(ShipType.BATTLESHIP));
    assertEquals(1, fleet.get(ShipType.DESTROYER));
    assertEquals(7, fleet.get(ShipType.SUBMARINE));
  }

  @Test
  public void testChooseDimensions() {
    assertNull(aiView.chooseDimensions());
  }

  private String getConsoleOutput() {
    return outputStream.toString().replaceAll("\r\n", "\n");
  }
}
