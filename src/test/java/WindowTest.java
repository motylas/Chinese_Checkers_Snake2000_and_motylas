import org.junit.Test;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class WindowTest {


  @Test
  public void setColorTest() throws NoSuchMethodException {
    Window window = new Window(6,1);
    Color color = window.setColor(3);
    assertEquals(color, Color.BLUE);
  }

  @Test
  public void changePositionTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Window window = new Window(6,1);
    window.createBoard();
    window.addPieces();
    Method method = Window.class.getDeclaredMethod("changePosition", ArrayList.class, int.class, int.class, int.class);
    method.setAccessible(true);
    method.invoke(window, window.redPieces, 9,5,8);
    for(Piece piece: window.redPieces) {
      if (piece.id == 9) {
        assertEquals(piece.x, 5);
        assertEquals(piece.y,8);
      }
    }
  }

  @Test
  public void otherMoveTest() {
    Window window = new Window(6,1);
    window.createBoard();
    window.addPieces();
    window.otherMove("Hejka;6;6;8;1;true");
    window.otherMove("Hejka;5;7;8;2;true");
    window.otherMove("Hejka;7;5;8;3;true");
    window.otherMove("Hejka;7;7;8;4;true");
    window.otherMove("Hejka;7;8;8;5;true");
    window.otherMove("Hejka;8;7;8;6;true");
    for(Piece piece: window.redPieces) {
      if (piece.id == 8) {
        assertEquals(piece.x, 6);
        assertEquals(piece.y,6);
      }
    }
    for(Piece piece: window.yellowPieces) {
      if (piece.id == 8) {
        assertEquals(piece.x, 5);
        assertEquals(piece.y,7);
      }
    }
    for(Piece piece: window.bluePieces) {
      if (piece.id == 8) {
        assertEquals(piece.x, 7);
        assertEquals(piece.y,5);
      }
    }
    for(Piece piece: window.purplePieces) {
      if (piece.id == 8) {
        assertEquals(piece.x, 7);
        assertEquals(piece.y,7);
      }
    }
    for(Piece piece: window.greenPieces) {
      if (piece.id == 8) {
        assertEquals(piece.x, 7);
        assertEquals(piece.y,8);
      }
    }
    for(Piece piece: window.cyanPieces) {
      if (piece.id == 8) {
        assertEquals(piece.x, 8);
        assertEquals(piece.y,7);
      }
    }
  }

  @Test
  public void colorTileTest() {
    Window window = new Window(6,1);
    window.createBoard();
    window.addPieces();
    window.colorTile("Hejka;3;10;255;255;0");
    for(Tile tile: window.tiles) {
      if (tile.x == 3 && tile.y == 10) {
        assertEquals(tile.R, 255);
        assertEquals(tile.G,255);
        assertEquals(tile.B,0);
      }
    }
  }


}
