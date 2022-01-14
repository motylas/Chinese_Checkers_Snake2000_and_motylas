import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Class which contains tests for 'Lobby' and 'HostLobby' classes.
 */
public class LobbyTest {
  /**
   * Test for 'Lobby' class.
   */
  @Test
  public void labelTest() {
    Lobby lobby = new Lobby();
    lobby.playerJoin(5);
    assertEquals(lobby.label.getText(), "Liczba graczy: 5/6");
  }

  /**
   * Test for 'HostLobby' class.
   */
  @Test
  public void hostLabelTest() {
    HostLobby lobby = new HostLobby();
    lobby.playerJoin(3);
    assertEquals(lobby.label.getText(), "Liczba graczy: 3/6");
  }


}
