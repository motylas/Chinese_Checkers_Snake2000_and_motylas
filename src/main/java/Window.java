import java.awt.Color;
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
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.Font;

public class Window extends Board implements ActionListener {
  private final Menu menu3;
  private final MenuItem i7;
  private final Label myLabel;
  private MenuBar myMenu;
  private java.awt.geom.Ellipse2D.Float circle;
  private JPanel surface;
  public boolean firstClick = false;
  private boolean firstDraw = false;
  public int currentPlayer;
  boolean hasTurn = false;
  float cirRadius = 650f/(3*boardSize + 1f);
  JLabel label1 = new JLabel("Twoj kolor");
  JLabel label2 = new JLabel("Aktualny gracz");
  JLabel winnersLabel = new JLabel("<html></html>");


  private final int playerId;


  private void Surface() {
    JButton button = new JButton("Zakoncz ture");
    button.addActionListener(e -> {
      if(hasTurn) {
        Client.action("NEXT;"+playerList[playerId-1]);
        firstClick = false;
      }
    });
    this.surface = new JPanel() {
      public void paintComponent(Graphics var1) {
        super.paintComponent(var1);
        Window.this.firstDrawing(var1);
        Window.this.doDrawing(var1);
      }
    };
    surface.setLayout(null);
    surface.add(label1);
    surface.add(label2);
    surface.add(button);
    surface.add(winnersLabel);
    button.setBounds(1020,355,150,50);
    label2.setBounds(1020,280,150,50);
    label1.setBounds(1020,205,150,50);
    winnersLabel.setBounds(1020,430,250,200);
    label1.setFont(new Font("Verdana", Font.BOLD, 16));
    label2.setFont(new Font("Verdana", Font.BOLD, 16));
    winnersLabel.setFont(new Font("Verdana", Font.BOLD, 16));

    surface.addMouseListener(new ChooseTile());
  }

  public Color setColor(int number){
    return switch (number) {
      case 1 -> new Color(255, 0, 0);
      case 2 -> new Color(255, 255, 0);
      case 3 -> new Color(0, 0, 255);
      case 4 -> new Color(255, 0, 255);
      case 5 -> new Color(0, 255, 0);
      case 6 -> new Color(0, 255, 255);
      default -> null;
    };
  }

    public void drawingCircle(float xOffset, float yOffset, int xPos, int yPos, float rad, Graphics2D grap){
    double X = (xOffset +((xPos - (boardSize*3 - yPos) * 0.5) * cirRadius));
    double Y = (yOffset + ((yPos * 0.866) * cirRadius));
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
      drawingCircle(215 + cirRadius*0.1f,10 + (cirRadius*0.1f),obj.x, obj.y,radius,grap);
      grap.fill(this.circle);
      }
    }


  private void firstDrawing(Graphics var1) {
    if(!firstDraw){
      Graphics2D var2 = (Graphics2D)var1;
      float radius = cirRadius;
      float pieceRadius = cirRadius;
      createBoard();
      for(int x = 0; x <=4 * boardSize; x++) {
        for(int y = 0; y <= 4 * boardSize; y++) {
          if(board[x][y] == 'o'){
            drawingCircle(215, 10, x, y, radius, var2);
          }
        }
      }
      addPieces();
      firstDraw = true;
    }
  }

  private void doDrawing(Graphics var1) {
    Graphics2D var2 = (Graphics2D)var1;
    float radius = cirRadius*0.8f;
    float pieceRadius = cirRadius*0.6f;
    drawingTiles(var2,radius);
    drawingPieces(redPieces, var2, pieceRadius);
    drawingPieces(greenPieces, var2, pieceRadius);
    drawingPieces(bluePieces, var2, pieceRadius);
    drawingPieces(yellowPieces, var2, pieceRadius);
    drawingPieces(purplePieces, var2, pieceRadius);
    drawingPieces(cyanPieces, var2, pieceRadius);
    label1.setForeground(setColor(playerList[playerId-1]));
    label2.setForeground(setColor(currentPlayer));
  }

  public void nextPlayerTurn(String action) {
    String[] values = action.split(";");
    hasTurn = Boolean.parseBoolean(values[1]);
  }


  public void actionPerformed(ActionEvent var1) {
  // TODO: do usuniecia
    if (var1.getActionCommand().equals("Nastepny Gracz")) {
      System.out.println("Do usuniecia");
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
      if(hasTurn){
        switch (playerList[playerId - 1]) {
          case 1 -> playerMove(X1, Y1, redPieces);
          case 2 -> playerMove(X1, Y1, yellowPieces);
          case 3 -> playerMove(X1, Y1, bluePieces);
          case 4 -> playerMove(X1, Y1, purplePieces);
          case 5 -> playerMove(X1, Y1, greenPieces);
          case 6 -> playerMove(X1, Y1, cyanPieces);
        }
      }
    }
  }
  public void playerMove(int X1, int Y1, ArrayList<Piece> pieces){

    for(Tile obj: tiles) {
      Window.this.circle = new Ellipse2D.Float((float)(215 +((obj.x - (boardSize*3 - obj.y) * 0.5) * cirRadius)),
              (float)(10 + ((obj.y * 0.866) * cirRadius)), cirRadius*0.8f, cirRadius*0.8f);

      if(circle.contains(X1, Y1) && !firstClick) {
        for(Piece pie: pieces){
          if(obj.x == pie.x && obj.y == pie.y){
            Client.action("TRY1;"+pie.x+";"+pie.y+";"+playerList[playerId-1]);
            firstClick = true;
          }
        }
      }

      else if(circle.contains(X1, Y1) && firstClick) {
        Client.action("TRY2;"+obj.x+";"+obj.y+";"+playerList[playerId-1]);
        firstClick = false;
      }
    }
  }

  public void colorTile(String action){
    String[] values = action.split(";");
    int x = Integer.parseInt(values[1]);
    int y = Integer.parseInt(values[2]);
    int r = Integer.parseInt(values[3]);
    int g = Integer.parseInt(values[4]);
    int b = Integer.parseInt(values[5]);
    for (Tile tile: tiles){
      if (tile.x == x && tile.y == y){
        tile.R = r;
        tile.G = g;
        tile.B = b;
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

  public void setCurrentPlayer(String action){
    String[] values = action.split(";");
    currentPlayer = Integer.parseInt(values[1]);
  }

  public void win(String action){
    String[] values = action.split(";");
    int winnerId = Integer.parseInt(values[1]);
    int winnersCount = Integer.parseInt(values[2]);
    String text = winnersLabel.getText();
    String newScoreBoard = text.replace("</html>", "");
    newScoreBoard += winnersCount + ". Player ";
    switch (playerList[winnerId - 1]) {
      case 1 -> newScoreBoard += "Red";
      case 2 -> newScoreBoard += "Yellow";
      case 3 -> newScoreBoard += "Blue";
      case 4 -> newScoreBoard += "Purple";
      case 5 -> newScoreBoard += "Green";
      case 6 -> newScoreBoard += "Cyan";
    }
    newScoreBoard += "<br/></html>";
    winnersLabel.setText(newScoreBoard);
    System.out.println(newScoreBoard);
    System.out.println("Player " + winnerId + " won");
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
    setBounds(100, 0, 1280, 960);
    setVisible(false);
    this.playerId = playerId;
    playerList = new int[numberOfPlayers];
    createPlayerList(numberOfPlayers);
    if(playerId==1){
      hasTurn=true;
    }
    currentPlayer = playerList[0];
  }
}