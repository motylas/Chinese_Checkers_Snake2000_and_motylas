/**
 * Class responsible for creating object of Tile type
 */
public class Tile {
  /**
   * Variable x determines position which will be translate into X position
   * of Tile in Player Window.
   */
  int x;
  /**
   * Variable y determines position which will be translate into Y position
   * of Tile in Player Window.
   */
  int y;
  /**
   * Determines if tile is taken by piece.
   */
  boolean isTaken;
  /**
   * Determines if tile is available for chosen piece.
   */
  boolean isAvailable;
  /**
   * First value in RGB to set Tile border's color.
   */
  int R;
  /**
   * Second value in RGB to set Tile border's color.
   */
  int G;
  /**
   * Third value in RGB to set Tile border's color.
   */
  int B;

  /**
   * Constructor of 'Tile' class.
   * @param x Variable x determines position which will be translate into X position
   * of Tile in Player Window.
   * @param y Variable y determines position which will be translate into Y position
   * of Tile in Player Window.
   * @param isTaken Determines if tile is taken by piece.
   * @param isAvailable Determines if tile is available for chosen piece.
   * @param R First value in RGB to set Tile border's color.
   * @param G Second value in RGB to set Tile border's color.
   * @param B Third value in RGB to set Tile border's color.
   */
  public Tile(int x, int y, boolean  isTaken, boolean isAvailable, int R, int G, int B){
    this.x = x;
    this.y = y;
    this.isTaken = isTaken;
    this.isAvailable = isAvailable;
    this.R = R;
    this.G = G;
    this.B = B;
  }
}