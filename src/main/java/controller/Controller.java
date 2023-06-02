package controller;

import java.util.List;
import model.Coord;
import model.PlayerModel;
import model.AiModel;
import view.AiView;
import view.PlayerView;

public class Controller {
  private final PlayerModel playermodel;
  private final AiModel aimodel;
  private PlayerView playerview;
  private AiView aiview;

  public Controller() {
    playermodel = new PlayerModel(this);
    aimodel = new AiModel(this);
    playermodel.setPlayer(aimodel);
    aimodel.setPlayer(playermodel);
  }


  public void run() {

    playerview = new PlayerView();
    //Welcome Message and Board Parameters
    int[] dimArray = playerview.chooseDimensions();
    aiview = new AiView(dimArray[0], dimArray[1]);
    //Setup / FleetSelection
    playermodel.setup(dimArray[0], dimArray[1], playerview.fleetSelection());
    aimodel.setup(dimArray[0], dimArray[1], aiview.fleetSelection());

    List<Coord> aiShots = null;
    List<Coord> playerShots = null;
    //Salvo Stage


    while (!(playermodel.unsunkShips() == 0) || !(aimodel.unsunkShips() == 0)) {

      aiview.showBoard(aimodel.returnShipList(), aimodel.reportDamage(playerShots),
          aimodel.reportMisses(playerShots));

      playerview.showBoard(playermodel.returnShipList(), playermodel.reportDamage(aiShots),
          playermodel.reportMisses(aiShots));

      aiShots = this.aiCallTakeShot();
      playerShots = this.playerCallTakeShot();

    }
    if (playermodel.unsunkShips() == 0) {
      System.out.print("\n YOU LOST...AI WINS");
    } else {
      System.out.print("\n YOU WIN...AI LOSES");
    }

  }

  public List<Coord> playerCallTakeShot() {
    return playerview.takeShots(playermodel.unsunkShips(), aimodel.returnAllDamagedCoord(),
        aimodel.returnAllMissCoord());
  }

  public List<Coord> aiCallTakeShot() {
    return aiview.takeShots(aimodel.unsunkShips(), playermodel.returnAllDamagedCoord(),
        playermodel.returnAllMissCoord());
  }
}
