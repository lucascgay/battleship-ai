package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Coord;
import model.Ship;
import model.ShipType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerViewTest {
  private ByteArrayOutputStream outputStream;

  @BeforeEach
  public void setup() {
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
  }

  @AfterEach
  public void tearDown() {
    System.setOut(System.out);
    System.setIn(System.in);
  }

  @Test
  public void testChooseDimensionsIn() {
    String input = "8\n10\n"; // Custom input for height and width separated by newlines
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in); // Redirect System.in to use the ByteArrayInputStream

    PlayerView playerView = new PlayerView();
    int[] expected = {8, 10};
    int[] result = playerView.chooseDimensions();

    assertArrayEquals(expected, result);
  }

  @Test
  public void testFleetSelection() {
    String userInput = "2 1 1 1"; // Simulated user input: Carrier=2, Battleship=1, Destroyer=1, Submarine=1

    // Create a custom input stream to simulate user input
    InputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
    Scanner scanner = new Scanner(inputStream);

    PlayerView playerView = new PlayerView(); // Inject the custom scanner
    playerView.setScanner(scanner);

    playerView.smallerDim = 6; // Set the smallerDim value in the PlayerView object

    Map<ShipType, Integer> expectedFleet = new HashMap<>();
    expectedFleet.put(ShipType.CARRIER, 2);
    expectedFleet.put(ShipType.BATTLESHIP, 1);
    expectedFleet.put(ShipType.DESTROYER, 1);
    expectedFleet.put(ShipType.SUBMARINE, 1);

    Map<ShipType, Integer> actualFleet = playerView.fleetSelection();

    // Verify the expected output
    assertEquals(expectedFleet, actualFleet);

    // Clean up resources if needed
    scanner.close();
  }

  @Test
  public void testTakeShots() {
    String input = "6\n6\n"; // Custom input for height and width separated by newlines
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in); // Redirect System.in to use the ByteArrayInputStream

    PlayerView playerView = new PlayerView();
    playerView.chooseDimensions();

    String userInput = "1 2\n3 4\n"; // Simulated user input: (1, 2) and (3, 4)

    // Create a custom input stream to simulate user input
    InputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
    Scanner scanner = new Scanner(inputStream);

    playerView.setScanner(scanner);

    int unsunkShips = 2; // Set the number of unsunk ships for testing purposes


    List<Coord> damaged = new ArrayList<>();
    List<Coord> missed = new ArrayList<>();

    List<Coord> expectedShots = new ArrayList<>();
    expectedShots.add(new Coord(1, 2));
    expectedShots.add(new Coord(3, 4));

    List<Coord> actualShots = playerView.takeShots(unsunkShips, damaged, missed);

    // Verify the expected output
    assertEquals(expectedShots, actualShots);

    // Clean up resources if needed
    scanner.close();
  }


  @Test
  public void testShowBoard() {
    String input = "6\n6\n"; // Custom input for height and width separated by newlines
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in); // Redirect System.in to use the ByteArrayInputStream

    PlayerView playerView = new PlayerView();
    playerView.chooseDimensions();


    List<Ship> board = new ArrayList<>();
    List<Coord> hits = new ArrayList<>();
    List<Coord> misses = new ArrayList<>();

    // Set up the board, hits, and misses
    Ship ship1 =
        new Ship(new ArrayList<>(Arrays.asList(new Coord(0, 0), new Coord(0, 1),
            new Coord(0, 2))), ShipType.SUBMARINE);
    board.add(ship1);

    hits.add(new Coord(0, 0));
    hits.add(new Coord(1, 1));

    misses.add(new Coord(2, 2));
    misses.add(new Coord(3, 3));

    // Call the method
    playerView.showBoard(board, hits, misses);

    // Capture the printed output
    String printedOutput = outputStream.toString();

    // Define the expected output
    String expectedOutput = """
        Hello! Welcome to the OOD BattleSalvo Game!\s

        Please enter a valid height and width below:
        ------------------------------------------------------

        Your Board:
        HOOOOO
        SHOOOO
        SOMOOO
        OOOMOO
        OOOOOO
        OOOOOO
        """;

    // Compare the expected output with the captured output
    assertEquals(expectedOutput, printedOutput);
  }

}
