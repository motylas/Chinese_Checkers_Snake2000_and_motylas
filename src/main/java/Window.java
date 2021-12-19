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
  private boolean firstDraw = false;
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

  private void firstDrawing(Graphics var1) {
    if(firstDraw == false){
      Graphics2D var2 = (Graphics2D)var1;
      float radius = 40f;
      for(int x = 0; x <=16; x++) {
        for(int y = 0; y <=16; y++) {
          if(board[x][y] == 'o'){
            double X = (215 +((x - (12 - y) * 0.5) * 50));
            double Y = (10 + ((y * 0.866) * 50));
            Window.this.circle = new java.awt.geom.Ellipse2D.Float((float)(X), (float)(Y), (radius), (radius));
            System.out.printf("%d   %d\n",x,y);
            tiles.add(new Tile(x,y, false));
            var2.draw(this.circle);
            Window.this.surface.repaint();
          }
        }
      }
      firstDraw = true;
    }
  }

  private void doDrawing(Graphics var1) {
    Graphics2D var2 = (Graphics2D)var1;
    float radius = 40f;
    for(Tile obj: tiles){
      double X = (215 +((obj.x - (12 - obj.y) * 0.5) * 50));
          double Y = (10 + ((obj.y * 0.866) * 50));
          Window.this.circle = new java.awt.geom.Ellipse2D.Float((float)(X), (float)(Y), (radius), (radius));
          var2.draw(this.circle);
          Window.this.surface.repaint();
    }
  }

  public void actionPerformed(ActionEvent var1) {

    if (var1.getActionCommand().equals("Informacje")) {
      this.dialog = new JDialog(this, "Informacje", false);
      this.dialog.setSize(600, 240);
      this.dialog.setLayout(new GridLayout(3, 1));
      this.dialog.setLocationRelativeTo((Component)null);
      JLabel var17 = new JLabel("  Nazwa: Trylma");
      this.dialog.add(var17);
      this.dialog.setVisible(true);
    }
  }

//  public static void main(String[] var0) {
//    Window var1 = new Window();
//    var1.setBounds(100, 100, 1280, 960);
//    var1.setVisible(true);
//  }

  public class ChooseTile extends MouseAdapter {

    int X1;
    int Y1;

    public void mousePressed(MouseEvent e){
      X1 = e.getX();
      Y1 = e.getY();
      for(Tile obj: tiles) {
        Window.this.circle = new Ellipse2D.Float((float)(215 +((obj.x - (12 - obj.y) * 0.5) * 50)),
            (float)(10 + ((obj.y * 0.866) * 50)), 40f, 40f);
        if(circle.contains(X1, Y1)) {
          obj.x = obj.x + 2;
          System.out.println("onTile");
          for(Tile obje: tiles) {
            System.out.println(obje.x);
          }
        }

//        if(obj.active && e.getButton() == MouseEvent.BUTTON3) {
//          Color nowy = JColorChooser.showDialog(surface, "Wybierz kolor", null);
//          if(nowy != null) {
//            obj.R = nowy.getRed();
//            obj.G = nowy.getGreen();
//            obj.B = nowy.getBlue();
//          }
//        }
      }
    }
  }

  public Window() {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setTitle("Trylma");
    this.myLabel = new Label("Start", 1);
    this.myMenu = new MenuBar();
    this.menu3 = new Menu("Pomoc");
    this.myMenu.add(this.menu3);
    this.i7 = new MenuItem("Informacje");
    this.i7.addActionListener(this);
    this.menu3.add(this.i7);
    this.setLayout(new GridLayout(1, 1));
    this.setMenuBar(this.myMenu);
    this.Surface();
    this.add(this.surface);
  }
}