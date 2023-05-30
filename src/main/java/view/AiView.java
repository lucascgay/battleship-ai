package view;

import java.util.List;
import java.util.Map;
import model.Coord;
import model.ShipType;

public class AiView implements Viewable{
  @Override
  public int[] chooseDimensions() {

    int[] values = new int[2];

    values[0] = (int) (Math.random() * 10 + 6);
    values[1] = (int) (Math.random() * 10 + 6);


    return values;
  }

  @Override
  public void takeShots() {

  }

  @Override
  public void boardData() {

  }

  @Override
  public Map<ShipType, Integer> fleetSelection() {
    return null;
  }
}
