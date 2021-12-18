import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws Exception{
        try (var listener = new ServerSocket(55555)){
            System.out.println("Chinese checkers server is running...");
            var pool = Executors.newFixedThreadPool(20);
            while(true){
                GameManager gameManager = new GameManager();
                pool.execute(new Player(listener.accept()));
                pool.execute(new Player(listener.accept()));
            }
        }
    }
}