package model;

/**
 * The Coord class represents a coordinate with x and y values.
 */
public class Coord {
  public int x;
  public int y;
  /**
   * Constructs a Coord object with the specified x and y values.
   *
   * @param x the x-coordinate value
   * @param y the y-coordinate value
   */
  public Coord(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Checks if this Coord object is equal to another object.
   * Two Coord objects are considered equal if their x and y values are the same.
   *
   * @param obj the object to compare
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Coord other = (Coord) obj;
    return this.x == other.x && this.y == other.y;
  }
}
