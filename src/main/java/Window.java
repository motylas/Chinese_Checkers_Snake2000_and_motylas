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
  public boolean doubleJump = false;
  public boolean nextPlayer = false;
  public int actual_player = 1;
  int x2 = 0;
  int y2 = 0;
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
  public ArrayList<Piece> redBase = new ArrayList();
  public ArrayList<Piece> greenBase = new ArrayList();
  public ArrayList<Piece> blueBase = new ArrayList();
  public ArrayList<Piece> yellowBase = new ArrayList();
  public ArrayList<Piece> purpleBase = new ArrayList();
  public ArrayList<Piece> cyanBase = new ArrayList();

  public int[] playerList;
  private JDialog dialog;
  private int playerId;


  private void Surface() {
    JButton button = new JButton("Zakoncz ture");
    this.surface = new JPanel() {
      public void paintComponent(Graphics var1) {
        super.paintComponent(var1);
        Window.this.firstDrawing(var1);
        Window.this.doDrawing(var1);
      }
    };
    surface.setLayout(null);

    surface.add(button);
    button.setBounds(960,355,150,50);
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

  public void creatingBase(int t, int j_1, int j_2, int k_1, int subs, ArrayList<Piece> pieces){
    int id=1;
    int i = subs;
    int type = t;
    for (int j = j_1; j <= j_2; j++) {
      if (type == 1) {
        for (int k = k_1; k <= i; k++) {
          pieces.add(new Piece(id,j, k, false, 0, 0, 0));
          id++;
        }
        i--;
      }
      else if(type == 2){
        for (int k = i; k <= k_1; k++) {
          pieces.add(new Piece(id,j, k, false, 0, 0, 0));
          id++;
        }
        i--;

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
      /**
       * Z lobby/serwera funkacja switch-case'owa dodawać będzie odpowiednie pionki w zależności od ilości graczy
       */
      for(int i = 0; i < playerList.length; i++) {
        switch(playerList[i]) {
          case 1:
            addingPiecesToList(1,4,7,13,16,redPieces,var2,pieceRadius,255,0,0);
            creatingBase(2, 9, 12, 3, 3, redBase);
            break;
          case 2:
            addingPiecesToList(2,0,3,12,12,yellowPieces,var2,pieceRadius,255,255,0);
            creatingBase(1, 13, 16, 4, 7, yellowBase);
            break;
          case 3:
            addingPiecesToList(1,4,7,4,7,bluePieces,var2,pieceRadius,0,0,255);
            creatingBase(2, 9, 12, 12, 12, blueBase);
            break;
          case 4:
            addingPiecesToList(2,9,12,3,3,purplePieces,var2,pieceRadius,255,0,255);
            creatingBase(1, 4, 7, 13, 16, purpleBase);
            break;
          case 5:
            addingPiecesToList(1,13,16,4,7,greenPieces,var2,pieceRadius,0,255,0);
            creatingBase(2, 0, 3, 12, 12, greenBase);
            break;
          case 6:
            addingPiecesToList(2,9,12,12,12,cyanPieces,var2,pieceRadius,0,255,255);
            creatingBase(1, 4, 7, 4, 7, cyanBase);
            break;
        }
      }
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
    nextPlayerTurn();
  }

  public void nextPlayerTurn() {
    if(nextPlayer) {
      for(int i = 0; i < playerList.length; i++){
        if(actual_player == playerList[i]) {
          if(i == (playerList.length - 1)){
            actual_player = playerList[0];
          }
          else{
            actual_player = playerList[i+1];
          }
          break;
        }
      }
      firstClick = false;
      doubleJump = false;
      nextPlayer = false;
    }
  }


  public void actionPerformed(ActionEvent var1) {

    if (var1.getActionCommand().equals("Nastepny Gracz")) {
      for(int i = 0; i < playerList.length; i++){
        if(actual_player == playerList[i]) {
          if(i == (playerList.length - 1)){
            actual_player = playerList[0];
          }
          else{
            actual_player = playerList[i+1];
          }
          break;
        }
      }
      firstClick = false;
      doubleJump = false;
      nextPlayer = false;
    }
  }

  public static void main(String[] var0) {
    Window var1 = new Window(2, 1);
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
      if(actual_player == playerList[playerId-1]){
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
                  obje.x == pie.x && obje.y == pie.y - 1) && !obje.isTaken && !doubleJump){
                obje.isAvailable = true;
                obje.R = pie.R;
                obje.G = pie.G;
                obje.B = pie.B;
              }
              if(!doubleJump || (doubleJump && pie.x == x2 && pie.y == y2)){
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
            }
            pie.isActive = true;
            firstClick = true;
          }
        }
      }


      else if(circle.contains(X1, Y1) && firstClick) {
        if(obj.isAvailable){
          for(Piece pie: pieces) {
            if (((obj.x == pie.x + 1 && obj.y == pie.y) ||
                (obj.x == pie.x - 1 && obj.y == pie.y) ||
                (obj.x == pie.x + 1 && obj.y == pie.y - 1) ||
                (obj.x == pie.x - 1 && obj.y == pie.y + 1) ||
                (obj.x == pie.x && obj.y == pie.y + 1) ||
                (obj.x == pie.x && obj.y == pie.y - 1)) && pie.isActive){
                System.out.println("skok");
                nextPlayer = true;
            }
            else {
              if(pie.isActive){
                System.out.println("jump");
                doubleJump = true;
                x2 = pie.x;
                y2 = pie.y;
              }

            }

          }
          for(Piece pie: pieces){
            if(pie.isActive){
              if(pie.x == x2){
                x2 = obj.x;
              }
              if(pie.y == y2){
                y2 = obj.y;
              }
              pie.x = obj.x;
              pie.y = obj.y;
              pie.isActive = false;
              Client.action("MOVE;"+pie.x+";"+pie.y+";"+pie.getID()+";"+actual_player+";"+nextPlayer);
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
    nextPlayer = Boolean.parseBoolean(values[5]);

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
    nextPlayerTurn();
  }

  private void changePosition(ArrayList<Piece> pieces, int id, int x, int y){
    for(Piece piece: pieces) {
      if (piece.id == id) {
        piece.x = x;
        piece.y = y;
      }
    }
  }

  public Window(int numberOfPlayers, int playerId) {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle("Trylma");
    myLabel = new Label("Start", 1);
    myMenu = new MenuBar();
    menu3 = new Menu("Pomoc");
    myMenu.add(this.menu3);
    i7 = new MenuItem("Nastepny Gracz");
    i7.addActionListener(this);
    menu3.add(this.i7);
    setLayout(new GridLayout(1, 2));
    setMenuBar(this.myMenu);
    Surface();
    add(this.surface);
    setBounds(100, 100, 1280, 960);
    setVisible(false);
    this.playerId = playerId;
    playerList = new int[numberOfPlayers];
    switch(numberOfPlayers){
      case 2:
        playerList[0] = 1;
        playerList[1] = 4;
        break;
      case 3:
        playerList[0] = 1;
        playerList[1] = 3;
        playerList[2] = 5;
        break;
      case 4:
        playerList[0] = 2;
        playerList[1] = 3;
        playerList[2] = 5;
        playerList[3] = 6;
        break;
      case 6:
        playerList[0] = 1;
        playerList[1] = 2;
        playerList[2] = 3;
        playerList[3] = 4;
        playerList[4] = 5;
        playerList[5] = 6;
        break;
    }
  }
}