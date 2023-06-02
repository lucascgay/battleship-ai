package model;

import controller.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerModel extends AbstractPlayer implements Player {
  String name;
  AiModel aiModel;
  public PlayerModel(Controller controller) {
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

        for (int i = 0; i < specifications.get(shipType); i++) {
          int startingPoint = (int) (Math.random() * space);
          List<Coord> coords = new ArrayList<>();

          for (int k = startingPoint; k < startingPoint + shipType.shipSize; k++) {
            coords.add(new Coord(rowOffset + i, k));
          }

          Ship ship = new Ship(coords, shipType);
          shipPositions.add(ship);
        }

        rowOffset += specifications.get(shipType);
      }
    } else {
      for (ShipType shipType : ShipType.values()) {
        int space = width - shipType.shipSize + 1;

        for (int i = 0; i < specifications.get(shipType); i++) {
          int startingPoint = (int) (Math.random() * space);
          List<Coord> coords = new ArrayList<>();

          for (int k = startingPoint; k < startingPoint + shipType.shipSize; k++) {
            coords.add(new Coord(k, rowOffset + i));
          }

          Ship ship = new Ship(coords, shipType);
          shipPositions.add(ship);
        }

        rowOffset += specifications.get(shipType);
      }
    }

    shipList = shipPositions;
    return shipPositions;
  }

  @Override
  public List<Coord> takeShots() {
    return controller.playerCallTakeShot();
  }


  // aimodel.successfullhits(playermodel.reportDamage(aimodel.takeShots()));
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
    aiModel.reportDamage(aiModel.takeShots());
  }

  @Override
  public void endGame(GameResult result, String reason) {

  }
}
