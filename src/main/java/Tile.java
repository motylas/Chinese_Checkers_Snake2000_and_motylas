public class Tile {
  int x;
  int y;
  boolean isTaken;
  boolean isAvailable;
  int R;
  int G;
  int B;
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