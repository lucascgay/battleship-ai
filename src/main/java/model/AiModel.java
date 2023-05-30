package model;

import java.util.List;
import java.util.Map;

public class AiModel implements Player {
  String name = "AI";
  @Override
  public String name() {
    return name;
  }

  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    return null;
  }

  @Override
  public List<Coord> takeShots() {
    return null;
  }

  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    return null;
  }

  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {

  }

  @Override
  public void endGame(GameResult result, String reason) {

  }
}
