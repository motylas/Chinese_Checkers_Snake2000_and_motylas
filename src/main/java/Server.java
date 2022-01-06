import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Server {
    private static int playerCount=0;
    private static int maxPlayers=6;
    static boolean gameStarted = false;

    public static void main(String[] args) throws Exception{
        try (var listener = new ServerSocket(55555)){
            System.out.println("Chinese checkers server is running...");
            var pool = Executors.newFixedThreadPool(20);
            GameManager gm = new GameManager();

            while(true){
                if(playerCount < maxPlayers){
                    pool.execute(new Player(listener.accept(),gm, playerCount+1, gameStarted));
                    playerCount++;
                }
            }
        }
    }

    static void gameStarted(){
        gameStarted = true;
    }
}