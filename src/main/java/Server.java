import java.net.ServerSocket;
import java.util.concurrent.Executors;

/**
 * Class responsible for starting server and creating players
 */
public class Server {
    /**
     * Number of players currently connected
     */
    private static int playerCount=0;
    /**
     * Number of max players in a game
     */
    private static int maxPlayers=6;
    /**
     * Boolean declaring if the game has already started
     */
    static boolean gameStarted = false;

    /**
     * Method setting up a server, creating players and gamemanager
     * @param args no parameters needed
     * @throws Exception
     */
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

    /**
     * Method changning gameStarted to true
     */
    public static void gameStarted(){
        gameStarted = true;
    }
}