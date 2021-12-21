import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D.Float;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;

public class Window extends JFrame implements ActionListener {
  private final Menu menu3;
  private final MenuItem i7;
  private final Label myLabel;
  private MenuBar myMenu;
  private java.awt.geom.Ellipse2D.Float circle;
  private JPanel surface;
  public boolean firstClick = false;
  private boolean firstDraw = false;
  public int actual_player = 1;
  private char[][] board = {
      {'x','x','x','x','x','x','x','x','x','x','x','x','o','x','x','x','x'},
      {'x','x','x','x','x','x','x','x','x','x','x','o','o','x','x','x','x'},
      {'x','x','x','x','x','x','x','x','x','x','o','o','o','x','x','x','x'},
      {'x','x','x','x','x','x','x','x','x','o','o','o','o','x','x','x','x'},
      {'x','x','x','x','o','o','o','o','o','o','o','o','o','o','o','o','o'},
      {'x','x','x','x','o','o','o','o','o','o','o','o','o','o','o','o','x'},
      {'x','x','x','x','o','o','o','o','o','o','o','o','o','o','o','x','x'},
      {'x','x','x','x','o','o','o','o','o','o','o','o','o','o','x','x','x'},
      {'x','x','x','x','o','o','o','o','o','o','o','o','o','x','x','x','x'},
      {'x','x','x','o','o','o','o','o','o','o','o','o','o','x','x','x','x'},
      {'x','x','o','o','o','o','o','o','o','o','o','o','o','x','x','x','x'},
      {'x','o','o','o','o','o','o','o','o','o','o','o','o','x','x','x','x'},
      {'o','o','o','o','o','o','o','o','o','o','o','o','o','x','x','x','x'},
      {'x','x','x','x','o','o','o','o','x','x','x','x','x','x','x','x','x'},
      {'x','x','x','x','o','o','o','x','x','x','x','x','x','x','x','x','x'},
      {'x','x','x','x','o','o','x','x','x','x','x','x','x','x','x','x','x'},
      {'x','x','x','x','o','x','x','x','x','x','x','x','x','x','x','x','x'},
  };
  public ArrayList<Tile> tiles = new ArrayList();
  public ArrayList<Piece> redPieces = new ArrayList();
  public ArrayList<Piece> greenPieces = new ArrayList();
  public ArrayList<Piece> bluePieces = new ArrayList();
  public ArrayList<Piece> yellowPieces = new ArrayList();
  public ArrayList<Piece> purplePieces = new ArrayList();
  public ArrayList<Piece> cyanPieces = new ArrayList();
  private JDialog dialog;


  private void Surface() {
    this.surface = new JPanel() {
      public void paintComponent(Graphics var1) {
        super.paintComponent(var1);
        Window.this.firstDrawing(var1);
        Window.this.doDrawing(var1);
      }
    };
    surface.addMouseListener(new ChooseTile());
  }

    public void drawingCircle(int xOffset, int yOffset, int xPos, int yPos, float rad, Graphics2D grap){
    double X = (xOffset +((xPos - (12 - yPos) * 0.5) * 50));
    double Y = (yOffset + ((yPos * 0.866) * 50));
    Window.this.circle = new java.awt.geom.Ellipse2D.Float((float)(X), (float)(Y), (rad), (rad));
    grap.draw(this.circle);
    Window.this.surface.repaint();
  }

  public void drawingTiles(Graphics2D grap, float radius){
    for(Tile obj: tiles){
      grap.setPaint(new Color(obj.R,obj.G,obj.B));
      drawingCircle(215, 10, obj.x, obj.y, radius, grap);
      grap.draw(this.circle);
    }
  }

  public void drawingPieces(ArrayList<Piece> pieces, Graphics2D grap,float radius){
    for(Piece obj: pieces){
      grap.setPaint(new Color(obj.R,obj.G,obj.B));
      drawingCircle(220,15,obj.x, obj.y,radius,grap);
      grap.fill(this.circle);
      for(Tile til: tiles){
        if(obj.x == til.x && obj.y == til.y){
          til.isTaken = true;
        }
      }
    }
  }

  public void isTakenTileChecking( int a1, int a2, int c1, int c2, int c3){
      for(Tile objec: tiles){
        if(objec.x == a1 && objec.y == a2 && !objec.isTaken){
          objec.isAvailable = true;
          objec.R = c1;
          objec.G = c2;
          objec.B = c3;
        }
      }

  }


  public void addingPiecesToList(int t,int j_1, int j_2, int k_1, int subs,ArrayList<Piece> pieces, Graphics2D grap, float radius,int R, int G, int B){
    int id=1;
    int i = subs;
    int type = t;
    for (int j = j_1; j <= j_2; j++) {
      if (type == 1) {
          for (int k = k_1; k <= i; k++) {
            pieces.add(new Piece(id,j, k, false, R, G, B));
            drawingCircle(220, 15, j, k, radius, grap);
            id++;
          }
          i--;
      }
      else if(type == 2){
          for (int k = i; k <= k_1; k++) {
            pieces.add(new Piece(id,j, k, false, R, G, B));
            id++;
            drawingCircle(220, 15, j, k, radius, grap);
          }
          i--;

      }
    }
  }


  private void firstDrawing(Graphics var1) {
    if(firstDraw == false){
      Graphics2D var2 = (Graphics2D)var1;
      float radius = 40f;
      float pieceRadius = 30f;
      for(int x = 0; x <=16; x++) {
        for(int y = 0; y <=16; y++) {
          if(board[x][y] == 'o'){
            System.out.printf("%d   %d\n",x,y);
            tiles.add(new Tile(x,y, false, false, 0, 0, 0));

            drawingCircle(215, 10, x, y, radius, var2);
          }
        }
      }
      addingPiecesToList(1,4,7,13,16,redPieces,var2,pieceRadius,255,0,0);
      addingPiecesToList(1,13,16,4,7,greenPieces,var2,pieceRadius,0,255,0);
      addingPiecesToList(1,4,7,4,7,bluePieces,var2,pieceRadius,0,0,255);
      addingPiecesToList(2,0,3,12,12,yellowPieces,var2,pieceRadius,255,255,0);
      addingPiecesToList(2,9,12,3,3,purplePieces,var2,pieceRadius,255,0,255);
      addingPiecesToList(2,9,12,12,12,cyanPieces,var2,pieceRadius,0,255,255);
      firstDraw = true;
    }
  }

  private void doDrawing(Graphics var1) {
    Graphics2D var2 = (Graphics2D)var1;
    float radius = 40f;
    float pieceRadius = 30f;
    drawingTiles(var2,radius);
    drawingPieces(redPieces, var2, pieceRadius);
    drawingPieces(greenPieces, var2, pieceRadius);
    drawingPieces(bluePieces, var2, pieceRadius);
    drawingPieces(yellowPieces, var2, pieceRadius);
    drawingPieces(purplePieces, var2, pieceRadius);
    drawingPieces(cyanPieces, var2, pieceRadius);
  }


  public void actionPerformed(ActionEvent var1) {

    if (var1.getActionCommand().equals("Nastepny Gracz")) {
      actual_player++;
      if(actual_player == 7){
        actual_player = 1;
      }

//      this.dialog = new JDialog(this, "Informacje", false);
//      this.dialog.setSize(600, 240);
//      this.dialog.setLayout(new GridLayout(3, 1));
//      this.dialog.setLocationRelativeTo((Component)null);
//      JLabel var17 = new JLabel("  Nazwa: Trylma");
//      this.dialog.add(var17);
//      this.dialog.setVisible(true);
    }
  }

  public static void main(String[] var0) {
    Window var1 = new Window();
    var1.setBounds(100, 0, 1280, 960);
    var1.setVisible(true);
  }

  public class ChooseTile extends MouseAdapter {

    int X1;
    int Y1;

    public void mousePressed(MouseEvent e){
      X1 = e.getX();
      Y1 = e.getY();
      // TODO: 12/21/2021 change to switch case
      if(actual_player == 1){
        playerMove(X1,Y1,redPieces);
      }
      else if(actual_player == 2){
        playerMove(X1,Y1,yellowPieces);
      }
      else if(actual_player == 3){
        playerMove(X1,Y1,bluePieces);
      }
      else if(actual_player == 4){
        playerMove(X1,Y1,purplePieces);
      }
      else if(actual_player == 5){
        playerMove(X1,Y1,greenPieces);
      }
      else if(actual_player == 6){
        playerMove(X1,Y1,cyanPieces);
      }
    }
  }

  public void playerMove(int X1, int Y1, ArrayList<Piece> pieces){
    for(Tile obj: tiles) {
      Window.this.circle = new Ellipse2D.Float((float)(215 +((obj.x - (12 - obj.y) * 0.5) * 50)),
          (float)(10 + ((obj.y * 0.866) * 50)), 40f, 40f);
      if(circle.contains(X1, Y1) && !firstClick) {
        for(Piece pie: pieces){
          if(obj.x == pie.x && obj.y == pie.y){
            for(Tile obje: tiles){
              if( (obje.x == pie.x + 1 && obje.y == pie.y ||
                  obje.x == pie.x - 1 && obje.y == pie.y ||
                  obje.x == pie.x + 1 && obje.y == pie.y - 1 ||
                  obje.x == pie.x - 1 && obje.y == pie.y + 1 ||
                  obje.x == pie.x && obje.y == pie.y + 1||
                  obje.x == pie.x && obje.y == pie.y - 1) && !obje.isTaken){
                obje.isAvailable = true;
                obje.R = pie.R;
                obje.G = pie.G;
                obje.B = pie.B;
              }
              if(obje.x == pie.x + 1 && obje.y == pie.y && obje.isTaken){
                isTakenTileChecking(pie.x + 2,pie.y,pie.R,pie.G,pie.B);
              }
              if(obje.x == pie.x - 1 && obje.y == pie.y && obje.isTaken){
                isTakenTileChecking(pie.x - 2,pie.y,pie.R,pie.G,pie.B);
              }
              if(obje.x == pie.x + 1 && obje.y == pie.y - 1 && obje.isTaken){
                isTakenTileChecking(pie.x + 2,pie.y - 2,pie.R,pie.G,pie.B);
              }
              if(obje.x == pie.x - 1 && obje.y == pie.y + 1 && obje.isTaken){
                isTakenTileChecking(pie.x - 2,pie.y + 2,pie.R,pie.G,pie.B);
              }
              if(obje.x == pie.x && obje.y == pie.y + 1 && obje.isTaken){
                isTakenTileChecking(pie.x,pie.y + 2,pie.R,pie.G,pie.B);
              }
              if(obje.x == pie.x && obje.y == pie.y - 1 && obje.isTaken){
                isTakenTileChecking(pie.x,pie.y - 2,pie.R,pie.G,pie.B);
              }
            }
            pie.isActive = true;
          }
        }
        firstClick = true;
      }

      else if(circle.contains(X1, Y1) && firstClick) {
        if(obj.isAvailable){
          for(Piece pie: pieces){
            if(pie.isActive){
              pie.x = obj.x;
              pie.y = obj.y;
              pie.isActive = false;
              Client.action("MOVE;"+pie.x+";"+pie.y+";"+pie.getID()+";"+actual_player);
            }
          }
        }
        for(Piece pie: pieces){
          if(pie.isActive){
            pie.isActive = false;
          }
        }

        for(Tile obje: tiles){
          obje.isAvailable = false;
          obje.isTaken = false;
          obje.R = 0;
          obje.G = 0;
          obje.B = 0;
        }
        firstClick = false;
      }
    }
  }

  // TODO: 12/21/2021 make try catch
  public void otherMove(String action){
    String[] values = action.split(";");
    int x = Integer.parseInt(values[1]);
    int y = Integer.parseInt(values[2]);
    int id = Integer.parseInt(values[3]);
    int player = Integer.parseInt(values[4]);

    switch (player){
      case 1: changePosition(redPieces,id,x,y);
        break;
      case 2: changePosition(yellowPieces,id,x,y);
        break;
      case 3: changePosition(bluePieces,id,x,y);
        break;
      case 4: changePosition(purplePieces,id,x,y);
        break;
      case 5: changePosition(greenPieces,id,x,y);
        break;
      case 6: changePosition(cyanPieces,id,x,y);
        break;
    }
  }

  private void changePosition(ArrayList<Piece> pieces, int id, int x, int y){
    for(Piece piece: pieces) {
      if (piece.id == id) {
        piece.x = x;
        piece.y = y;
      }
    }
  }

  public Window() {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle("Trylma");
    myLabel = new Label("Start", 1);
    myMenu = new MenuBar();
    menu3 = new Menu("Pomoc");
    myMenu.add(this.menu3);
    i7 = new MenuItem("Nastepny Gracz");
    i7.addActionListener(this);
    menu3.add(this.i7);
    setLayout(new GridLayout(1, 1));
    setMenuBar(this.myMenu);
    Surface();
    add(this.surface);
    setBounds(100, 100, 1280, 960);
    setVisible(true);
  }
}