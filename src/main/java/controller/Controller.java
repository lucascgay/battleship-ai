package controller;

import java.util.Map;
import model.PlayerModel;
import model.ShipType;
import view.PlayerView;

public class Controller {
  private final PlayerView playerview = new PlayerView();
  private final PlayerModel playermodel = new PlayerModel();



  public void run() {
    int[] dimArray = playerview.chooseDimensions();

    playermodel.setup(dimArray[0], dimArray[1], playerview.fleetSelection());
  }


}
