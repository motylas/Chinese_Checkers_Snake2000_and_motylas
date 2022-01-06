import java.util.ArrayList;

public class GameManager {
    ArrayList<Player> players = new ArrayList();

    public void communication(String com){
        sendMessageToPlayers(com);
    }

    private void sendMessageToPlayers(String message) {
        for (Player player: players) {
            player.sendMessage(message);
        }
    }
}
