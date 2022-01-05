import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lobby extends JFrame implements ActionListener {


  JLabel label = new JLabel("Test");

  @Override
  public void actionPerformed(ActionEvent e) {

  }

  public Lobby() {
    JPanel panel = new JPanel();
//    JFrame frame = new JFrame();
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle("Lobby");
    setBounds(400, 200, 480, 360);
    add(panel);
    panel.setLayout(new GridLayout(1,1));
    panel.add(label);
    label.setHorizontalAlignment(JLabel.CENTER);

    setVisible(true);
  }

  public static void main(String[] args){
    Lobby lobby = new Lobby();

  }
}
