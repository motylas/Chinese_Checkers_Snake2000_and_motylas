import java.util.ArrayList;

public class GameManager {
    ArrayList<Player> players = new ArrayList();

    public void communication(String com){
        for (Player player: players) {
            player.sendMessage(com);
        }
    }

    public void startGame(String com){
        for (Player player: players) {
            player.sendMessage(com+";"+player.getId());
        }
    }

}
