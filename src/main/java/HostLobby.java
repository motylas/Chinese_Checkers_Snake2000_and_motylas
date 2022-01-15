import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class making Host Lobby GUI extending lobby
 */
public class HostLobby extends Lobby implements ActionListener {

  /**
   * Button when clicked, send message to start a game
   */
  JButton button = new JButton("Rozpocznij Gre");

  /**
   * Host Lobby Constructor, creating GUI
   * Adding Action Listener on button to call method action in Client
   */
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

  /**
   * Action performed method
   * @param e ActionEvent
   */
  @Override
  public void actionPerformed(ActionEvent e) {
  }
}
