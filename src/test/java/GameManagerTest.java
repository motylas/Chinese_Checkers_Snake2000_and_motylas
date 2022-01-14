import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Class which contains tests for 'GameManager' class.
 */
public class GameManagerTest {

  /**
   * Test for 'getCurrentPlayer' method.
   * @throws NoSuchMethodException Exception.
   * @throws InvocationTargetException  Exception.
   * @throws IllegalAccessException Exception.
   */
  @Test
  public void getCurrentPlayerPiecesTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    GameManager gameManager = new GameManager();
    gameManager.startGame("Hejka;6");
    Method method = GameManager.class.getDeclaredMethod("getCurrentPlayerPieces", int.class);
    method.setAccessible(true);
    ArrayList<Piece> pieces = (ArrayList<Piece>) method.invoke(gameManager, 3);
    for (Piece pie: pieces){
      assertEquals(pie.R, 0);
      assertEquals(pie.G, 0);
      assertEquals(pie.B, 255);
    }

  }

  /**
   * Test for 'getCurrentPlayerEndBase' method.
   * @throws NoSuchMethodException  Exception.
   * @throws InvocationTargetException  Exception.
   * @throws IllegalAccessException Exception.
   */
  @Test
  public void getCurrentPlayerEndBaseTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    GameManager gameManager = new GameManager();
    gameManager.startGame("Hejka;2");
    Method method = GameManager.class.getDeclaredMethod("getCurrentPlayerEndBase", int.class);
    method.setAccessible(true);
    ArrayList<Piece> pieces = (ArrayList<Piece>) method.invoke(gameManager, 1);
    for (Piece pie: pieces){
      assertEquals(pie.R, 0);
      assertEquals(pie.G, 0);
      assertEquals(pie.B, 0);
    }

  }

  /**
   * Test for 'getCurrentPlayerBlockedTiles' method.
   * @throws NoSuchMethodException  Exception.
   * @throws InvocationTargetException  Exception.
   * @throws IllegalAccessException Exception.
   */
  @Test
  public void getCurrentPlayerBlockedTilesTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    GameManager gameManager = new GameManager();
    gameManager.startGame("Hejka;3");
    Method method = GameManager.class.getDeclaredMethod("getCurrentPlayerBlockedTiles", int.class);
    method.setAccessible(true);
    ArrayList<Piece> pieces = (ArrayList<Piece>) method.invoke(gameManager, 5);
    for (Piece pie: pieces){
      assertEquals(pie.R, 0);
      assertEquals(pie.G, 0);
      assertEquals(pie.B, 0);
    }

  }

  /**
   * Test of first part of 'moveHandler' method.
   * @throws NoSuchMethodException  Exception.
   * @throws InvocationTargetException  Exception.
   * @throws IllegalAccessException Exception.
   */
  @Test
  public void moveHandlerTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    GameManager gameManager = new GameManager();
    Player player = new Player(new Socket(), gameManager, 1, false);
    gameManager.startGame("Hejka;6");
    gameManager.moveHandler("TRY1;3;10;2;false",player);
    Method method = GameManager.class.getDeclaredMethod("getCurrentPlayerPieces", int.class);
    method.setAccessible(true);
    ArrayList<Piece> pieces = (ArrayList<Piece>) method.invoke(gameManager, 2);
    for (Piece pie: pieces){
      assertEquals(pie.R, 255);
      assertEquals(pie.G, 255);
      assertEquals(pie.B, 0);
    }

  }

  /**
   * Test of second part of 'moveHandler' method.
   * @throws NoSuchMethodException  Exception.
   * @throws InvocationTargetException  Exception.
   * @throws IllegalAccessException Exception.
   */
  @Test
  public void moveHandlerTry2Test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    GameManager gameManager = new GameManager();
    Player player = new Player(new Socket(), gameManager, 1, false);
    gameManager.startGame("Hejka;6");
    gameManager.moveHandler("TRY2;5;13;1;true",player);
    Method method = GameManager.class.getDeclaredMethod("getCurrentPlayerPieces", int.class);
    method.setAccessible(true);
    ArrayList<Piece> pieces = (ArrayList<Piece>) method.invoke(gameManager, 1);
    for (Piece pie: pieces){
      assertEquals(pie.R, 255);
      assertEquals(pie.G, 0);
      assertEquals(pie.B, 0);
    }

  }

}
