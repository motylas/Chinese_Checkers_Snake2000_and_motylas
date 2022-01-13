import org.junit.Test;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class WindowTest {


  @Test
  public void setColorTest() throws NoSuchMethodException {
    Window window = new Window(6,1);
    Color colorB = window.setColor(3);
    assertEquals(colorB, Color.BLUE);
    Color colorR = window.setColor(1);
    assertEquals(colorR, Color.RED);
    Color colorY = window.setColor(2);
    assertEquals(colorY, Color.YELLOW);
    Color colorG = window.setColor(5);
    assertEquals(colorG, Color.GREEN);
    Color colorP = window.setColor(4);
    assertEquals(colorP, new Color(255,0,255));
    Color colorC = window.setColor(6);
    assertEquals(colorC, Color.CYAN);
    Color colorNull = window.setColor(123);
    assertEquals(colorNull, null);
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

  @Test
  public void setCurrentPlayerTest() {
    Window window = new Window(4,1);
    window.setCurrentPlayer("Proba;1");
    assertEquals(window.currentPlayer, 1);
  }

  @Test
  public void playerMoveTest1() {
    Window window = new Window(3,1);
    window.createBoard();
    window.addPieces();
    window.firstClick = false;
    window.playerMove(535,724,window.redPieces);
    assertTrue(window.firstClick);
  }

  @Test
  public void playerMoveTest2() {
    Window window = new Window(3,1);
    window.createBoard();
    window.addPieces();
    window.firstClick = true;
    window.playerMove(535,724,window.redPieces);
    assertFalse(window.firstClick);
  }

  @Test
  public void winTest() {
    Window window = new Window(6,1);
    window.createBoard();
    window.addPieces();
    window.win("Hejka;5;1");
    assertEquals(window.winnersLabel.getText(), ("<html>1. Player Green<br/></html>"));
  }

  @Test
  public void nextPlayerTurnTest() {
    Window window = new Window(6,1);
    window.nextPlayerTurn("Proba;true");
    assertTrue(window.hasTurn);
  }






}
