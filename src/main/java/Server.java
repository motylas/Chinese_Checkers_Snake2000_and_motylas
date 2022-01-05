import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Server {
    private static int playerCount = 1;
    private static int maxPlayers=2;

    public static void main(String[] args) throws Exception{
        try (var listener = new ServerSocket(55555)){
            System.out.println("Chinese checkers server is running...");
            var pool = Executors.newFixedThreadPool(20);
            while(true){
                GameManager gm = new GameManager();
                while(true){
                    if(playerCount <= maxPlayers){
                        System.out.println("siema");
                        pool.execute(new Player(listener.accept(),gm, playerCount));
                        playerCount++;
                        System.out.println(playerCount);
                    }
                    System.out.print("");
                }
            }
        }
    }

    static int getPlayerCount(){
        return playerCount;
    }
    static int getMaxPlayers(){
        return maxPlayers;
    }

    static void playerLeft(){
        playerCount--;
        System.out.println(playerCount);
    }
    static void setMaxPlayers(int maxPlayers){
        Server.maxPlayers = maxPlayers;
    }
}