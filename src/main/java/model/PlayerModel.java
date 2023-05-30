package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PlayerModel implements Player {

  int[][] board;
  String name;


  @Override
  public String name() {
    return name;
  }

  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {

    //for each ship type I need a ShipType and a list of Coord
    int[][] board = new int[height][width];
    List<Ship> shipPositions = new ArrayList<>();

    if (height > width) {
      int space;
      int counter = 0;

      space = ShipType.CARRIER.shipSize - height;

      for (int i = 0; i < specifications.get(ShipType.CARRIER); i ++) {
        int startingPoint = (int) (Math.random() * space);
        List<Coord> coords = new ArrayList<>();
        for (int k = startingPoint; k < ShipType.CARRIER.shipSize; k ++) {
          board[i][k] = 1;
          coords.add(new Coord(i, k));
        }
        counter ++;
        Ship ship = new Ship(coords, ShipType.CARRIER);
        shipPositions.add(ship);
      }

      space = ShipType.BATTLESHIP.shipSize - height;

      for (int i = 0; i < specifications.get(ShipType.BATTLESHIP); i ++) {
        int startingPoint = (int) (Math.random() * space);
        List<Coord> coords = new ArrayList<>();
        for (int k = startingPoint; k < ShipType.BATTLESHIP.shipSize; k ++) {
          board[k][i + counter - 1] = 1;
          coords.add(new Coord(k, i));
        }
        counter ++;
        Ship ship = new Ship(coords, ShipType.BATTLESHIP);
        shipPositions.add(ship);
      }

      space = ShipType.DESTROYER.shipSize - height;

      for (int i = 0; i < specifications.get(ShipType.DESTROYER); i ++) {
        int startingPoint = (int) (Math.random() * space);
        List<Coord> coords = new ArrayList<>();
        for (int k = startingPoint; k < ShipType.DESTROYER.shipSize; k ++) {
          board[k][i + counter - 1] = 1;
          coords.add(new Coord(k, i));
        }
        counter ++;
        Ship ship = new Ship(coords, ShipType.BATTLESHIP);
        shipPositions.add(ship);
      }

      space = ShipType.SUBMARINE.shipSize - height;

      for (int i = 0; i < specifications.get(ShipType.SUBMARINE); i ++) {
        int startingPoint = (int) (Math.random() * space);
        List<Coord> coords = new ArrayList<>();
        for (int k = startingPoint; k < ShipType.SUBMARINE.shipSize; k ++) {
          board[k][i + counter - 1] = 1;
          coords.add(new Coord(k, i));
        }
        counter ++;
        Ship ship = new Ship(coords, ShipType.SUBMARINE);
        shipPositions.add(ship);
      }

      for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[i].length; j++) {
          System.out.print(board[i][j] + " ");
        }
        System.out.println(); // Move to the next line after printing each row
      }

      //placeTopdown();


    }

      //placeSideways();


    return shipPositions;
  }



  private List<Ship> placeTopdown(){
    return null;


  }
  private List<Ship> placeSideways() {
    return null;
  }

  @Override
  public List<Coord> takeShots() {

    return null;
  }

  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {

    for (Coord shot : opponentShotsOnBoard) {
      //for (all coords of boat positions)
        //if (shot.equals(boatcoords))
    }

    return null;
  }

  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {

  }

  @Override
  public void endGame(GameResult result, String reason) {

  }
}
