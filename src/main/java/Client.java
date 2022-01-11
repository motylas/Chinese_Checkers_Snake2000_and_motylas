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
    }

    private void play(){
        String response;
        while(in.hasNextLine()){
            response = in.nextLine();
            if(response.startsWith("MOVE")){
                var1.otherMove(response);
            }
            else if (response.startsWith("START")){
                String[] values = response.split(";");
                int playerId = Integer.parseInt(values[2]);
                var1 = new Window(playerCount,playerId);
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
                if (playerId == -1){
                    System.out.println("Game has already started!");
                    exit(9);
                }
                else {
                    System.out.println("Player number "+ playerId + " has left.");
                    System.out.println("Game will end.");
                    exit(2);
                }
            }
            else if (response.startsWith("COLOR")){
                var1.colorTile(response);
            }
            else if (response.startsWith("NEXT")){
                var1.nextPlayerTurn(response);
            }
            else if (response.startsWith("PLAYERCOLOR")){
                var1.setCurrentPlayer(response);
            }
            else if (response.startsWith("WIN")){
                var1.win(response);
            }
            // TODO: 12/18/2021 tu sie przesyla od playera info jakies i bedzie wysylane do window zeby cos konkretnego wyswietlic
        }
        exit(-1);
    }

    public static void action(String action){
        if (action.startsWith("MOVE") || action.startsWith("STOP") || action.startsWith("TRY") || action.startsWith("NEXT")){
            out.println(action);
        }
        else if (action.startsWith("START")){
            switch (playerCount){
                case 2:
                case 3:
                case 4:
                case 6:
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
