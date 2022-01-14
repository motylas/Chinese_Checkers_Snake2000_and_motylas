/**
 * Class responsible for creating object of Piece class.
 */
public class Piece {
  /**
   * Id is atteched to each piece. In one ArrayList shouldn't exist two
   * pieces with the same id.
   */
  int id;
  /**
   * Variable x determines position which will be translate into X position
   * of Piece in Player Window.
   */
  int x;
  /**
   * Variable y determines position which will be translate into Y position
   * of Piece in Player Window.
   */
  int y;
  /**
   * Determines if this piece was chosen by Player.
   */
  boolean isActive;
  /**
   * First value in RGB to set Piece's color.
   */
  int R;
  /**
   * Second value in RGB to set Piece's color.
   */
  int G;
  /**
   * Third value in RGB to set Piece's color.
   */
  int B;

  /**
   * Constructor of 'Piece' Class
   * @param id Id is atteched to each piece. In one ArrayList shouldn't exist two
   * pieces with the same id.
   * @param x Variable x determines position which will be translate into X position
   * of Piece in Player Window.
   * @param y Variable y determines position which will be translate into Y position
   * of Piece in Player Window.
   * @param isActive Determines if this piece was chosen by Player.
   * @param R First value in RGB to set Piece's color.
   * @param G Second value in RGB to set Piece's color.
   * @param B Third value in RGB to set Piece's color.
   */
  public Piece(int id, int x, int y, boolean isActive,int R, int G, int B){
    this.id=id;
    this.x = x;
    this.y = y;
    this.isActive = isActive;
    this.R = R;
    this.G = G;
    this.B = B;
  }

  /**
   * Public method which returns piece's Id.
   * @return id Id of piece.
   */
  public int getID(){
    return id;
  }
}