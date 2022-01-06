import java.util.ArrayList;

public class GameManager {
    ArrayList<Player> players = new ArrayList();

    public void communication(String com){
        for (Player player: players) {
            player.sendMessage(com);
        }
    }

    public void playerJoin(int playerCount){
        for (Player player : players){
            player.sendMessage("JOIN;"+playerCount);
        }
    }

    public void startGame(){
        for (Player player : players){
            player.sendMessage("START");
        }
    }
}
