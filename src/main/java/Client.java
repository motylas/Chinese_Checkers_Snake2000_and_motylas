import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;

public  class Client {
    private Socket socket;
    private Scanner in;
    private static PrintWriter out;
    static Window var1;
    Lobby lobby;
    static int playerCount = 0;
    static int maxPlayers = 6;
    static boolean gameStarted = false;

    public Client(String serverAddress) throws Exception {
        try{
            socket = new Socket(serverAddress, 55555);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e){
            System.out.println("Blad polaczenia!");
            exit(-1);
        }


        // TODO: 12/19/2021 temporary tworzenie 
        var1 = new Window();
    }

    private void play(){
        String response;
        while(in.hasNextLine()){
            response = in.nextLine();
            if(response.startsWith("MOVE")){
                var1.otherMove(response);
            }
            else if (response.startsWith("START")){
                var1.setVisible(true);
                lobby.setVisible(false);
                gameStarted = true;
            }
            else if (response.startsWith("LOBBY")){
                lobby = new Lobby();
                out.println("JOIN");
            }
            else if (response.startsWith("HOST")){
                lobby = new HostLobby();
                out.println("JOIN");
            }
            else if (response.startsWith("JOIN")){
                String[] values = response.split(";");
                playerCount = Integer.parseInt(values[1]);
                lobby.playerJoin(playerCount);
            }
            else if (response.startsWith("QUIT")){
                String[] values = response.split(";");
                int playerId = Integer.parseInt(values[1]);
                System.out.println("Player number "+ playerId + " has left.");
                System.out.println("Game will end.");
                exit(2);
            }
            // TODO: 12/18/2021 tu sie przesyla od playera info jakies i bedzie wysylane do window zeby cos konkretnego wyswietlic
        }
        exit(-1);
    }

    public static void action(String action){
        if (action.startsWith("MOVE") || action.startsWith("STOP")){
            out.println(action);
        }
        else if (action.startsWith("START")){
            System.out.println(playerCount);
            switch (playerCount){
                case 2:
                case 3:
                case 4:
                case 6:
                    maxPlayers = playerCount;
                    out.println(action+";"+playerCount);
                    break;
                default:
                    System.out.println("Invalid amount of players!");
            }
        }
    }


    public static void main(String[] args) throws Exception {
        if (args.length != 1){
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
        Client client = new Client(args[0]);
        client.play();
    }
}
