import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lobby extends JFrame {

  JLabel label = new JLabel();

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

  void playerJoin(int playerCount){
    label.setText("Liczba graczy: "+ playerCount + "/6");
  }

  public static void main(String[] args){
    Lobby lobby = new Lobby();
  }
}
