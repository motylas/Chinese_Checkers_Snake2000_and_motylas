import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HostLobby extends JFrame implements ActionListener {

  JButton button = new JButton("Rozpocznij Gre");
  JLabel label = new JLabel("Test");

  @Override
  public void actionPerformed(ActionEvent e) {

  }

  public HostLobby() {
    JPanel panel = new JPanel();
//    JFrame frame = new JFrame();
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle("Lobby");
    setBounds(400, 200, 480, 360);
    add(panel);
    panel.setLayout(new GridLayout(2,1));
    panel.add(label);
    label.setHorizontalAlignment(JLabel.CENTER);
    panel.add(button);
    setVisible(true);
  }

  public static void main(String[] args){
    HostLobby lobby = new HostLobby();

  }
}
