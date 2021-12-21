public class Piece {
  int id;
  int x;
  int y;
  boolean isActive;
  int R;
  int G;
  int B;
  public Piece(int id, int x, int y, boolean isActive,int R, int G, int B){
    this.id=id;
    this.x = x;
    this.y = y;
    this.isActive = isActive;
    this.R = R;
    this.G = G;
    this.B = B;
  }

  public int getID(){
    return id;
  }
}
