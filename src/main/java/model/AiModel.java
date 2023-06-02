package model;

import controller.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AiModel extends AbstractPlayer implements Player {
  String name = "AI";
  PlayerModel playerModel;

  public AiModel(Controller controller) {
    super(controller);
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    List<Ship> shipPositions = new ArrayList<>();
    int rowOffset = 0;

    if (height > width) {
      for (ShipType shipType : ShipType.values()) {
        int space = height - shipType.shipSize + 1;
        int numShips = specifications.getOrDefault(shipType, 0);

        for (int i = 0; i < numShips; i++) {
          int startingPoint = (int) (Math.random() * space);
          List<Coord> coords = new ArrayList<>();

          for (int k = startingPoint; k < startingPoint + shipType.shipSize; k++) {
            coords.add(new Coord(rowOffset + i, k));
          }

          Ship ship = new Ship(coords, shipType);
          shipPositions.add(ship);
        }

        rowOffset += numShips;
      }
    } else {
      for (ShipType shipType : ShipType.values()) {
        int space = width - shipType.shipSize + 1;
        int numShips = specifications.getOrDefault(shipType, 0);

        for (int i = 0; i < numShips; i++) {
          int startingPoint = (int) (Math.random() * space);
          List<Coord> coords = new ArrayList<>();

          for (int k = startingPoint; k < startingPoint + shipType.shipSize; k++) {
            coords.add(new Coord(k, rowOffset + i));
          }

          Ship ship = new Ship(coords, shipType);
          shipPositions.add(ship);
        }

        rowOffset += numShips;
      }
    }

    shipList = shipPositions;

    return shipPositions;
  }

  @Override
  public List<Coord> takeShots() {
    return controller.aiCallTakeShot();
  }

  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {

    if (opponentShotsOnBoard == null) {
      return new ArrayList<>();
    } else {
      List<Coord> damageCoord = new ArrayList<>();

      for (Coord shot : opponentShotsOnBoard) {
        for (Ship ship : shipList) {
          List<Coord> temp = ship.coords;
          for (Coord coord : temp) {

            if ((shot.x == coord.x) && (shot.y == coord.y)) {
              damageCoord.add(shot);
            }
          }
        }
      }


      allDamagedCoord.addAll(damageCoord);

      return allDamagedCoord;
    }
  }

  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    playerModel.reportDamage(playerModel.takeShots());
  }

  @Override
  public void endGame(GameResult result, String reason) {

  }
}
