package controller;

import java.util.List;
import model.Coord;
import model.PlayerModel;
import model.AiModel;
import view.AiView;
import view.PlayerView;

/**
 * The Controller class manages the game flow and interactions between the player, AI, and views
 * in the game of BattleSalvo.
 */
public class Controller {
  /**
   * The player model instance.
   */
  private final PlayerModel playermodel;
  /**
   * The AI model instance.
   */
  private final AiModel aimodel;
  /**
   * The player view instance.
   */
  private PlayerView playerview;
  /**
   * The AI view instance.
   */
  private AiView aiview;

  /**
   * Constructs a Controller object and initializes the player and AI models.
   */
  public Controller() {
    playermodel = new PlayerModel(this);
    aimodel = new AiModel(this);
    playermodel.setPlayer(aimodel);
    aimodel.setPlayer(playermodel);
  }

  /**
   * Runs the game by initializing the views, performing setup, and executing the salvo stage
   * until the game ends.
   */
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

      if (playermodel.unsunkShips() == 0) {
        System.out.print("\nYOU LOST...AI WINS");
        break;
      } else if (aimodel.unsunkShips() == 0){
        System.out.print("\nYOU WIN...AI LOSES");
        break;
      }

      aiShots = this.aiCallTakeShot();
      playerShots = this.playerCallTakeShot();
    }
  }

  /**
   * Retrieves the shots taken by the player.
   *
   * @return the locations of shots taken by the player
   */
  public List<Coord> playerCallTakeShot() {
    return playerview.takeShots(playermodel.unsunkShips(), aimodel.returnAllDamagedCoord(),
        aimodel.returnAllMissCoord());
  }

  /**
   * Retrieves the shots taken by the AI.
   *
   * @return the locations of shots taken by the AI
   */
  public List<Coord> aiCallTakeShot() {
    return aiview.takeShots(aimodel.unsunkShips(), playermodel.returnAllDamagedCoord(),
        playermodel.returnAllMissCoord());
  }
}
