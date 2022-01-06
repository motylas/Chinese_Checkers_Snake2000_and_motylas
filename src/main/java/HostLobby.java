import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HostLobby extends Lobby implements ActionListener {

  JButton button = new JButton("Rozpocznij Gre");

  public HostLobby() {
    JPanel panel = new JPanel();
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle("Lobby");
    setBounds(400, 200, 480, 360);
    add(panel);
    panel.setLayout(new GridLayout(2,1));
    panel.add(label);
    label.setHorizontalAlignment(JLabel.CENTER);
    panel.add(button);
    setVisible(true);
    button.addActionListener(e -> {
      Client.action("START");
    });
  }

  public static void main(String[] args){
    HostLobby lobby = new HostLobby();
  }

  // TODO: 1/6/2022 nmwm czy to musi byc trzeba ogarnac ocb tutaj piotrek
  @Override
  public void actionPerformed(ActionEvent e) {
  }
}
