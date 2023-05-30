package cs3500.pa03;

import controller.Controller;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    Controller controller = new Controller();
    controller.run();
  }
}