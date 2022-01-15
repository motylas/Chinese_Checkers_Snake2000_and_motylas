import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class making Lobby GUI
 */
public class Lobby extends JFrame {

  /**
   * Label to show text about current and max number of players
   */
  JLabel label = new JLabel();

  /**
   * Lobby Constructor, creating GUI, setting text on label
   */
  public Lobby() {
    JPanel panel = new JPanel();
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle("Lobby");
    setBounds(400, 200, 480, 360);
    add(panel);
    panel.setLayout(new GridLayout(1,1));
    panel.add(label);
    label.setHorizontalAlignment(JLabel.CENTER);
    label.setText("Liczba graczy: 0/6");
    setVisible(true);
  }

  /**
   * Method changing label text accordingly to current number of players connected to game
   * @param playerCount number of players currently connected to game
   */
  void playerJoin(int playerCount){
    label.setText("Liczba graczy: "+ playerCount + "/6");
  }
}
