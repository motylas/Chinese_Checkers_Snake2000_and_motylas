import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;

/**
 * Client class connecting with player
 * Also calls methods in Window
 */
public  class Client {
    /**
     * Typical socket
     */
    private Socket socket;
    /**
     * Input from player
     */
    private Scanner in;
    /**
     * Output sending to player
     */
    private static PrintWriter out;
    /**
     * Window where we play the game
     */
    static Window var1;
    /**
     * Our lobby
     */
    Lobby lobby;
    /**
     * Number of current players
     */
    static int playerCount = 0;
    /**
     * Checking if the game has already started
     */
    static boolean gameStarted = false;

    /**
     * Client constructor, setup server needed components
     * @param serverAddress server Address specified in main args[1]
     * @throws Exception
     */
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

    /**
     * This method gets input from player and do things on specific action
     * MOVE - calling method in Window to move a specific piece
     * START - creates a Window and hiding a lobby
     * LOBBY - creates normal lobby
     * HOST - creates Host Lobby
     * JOIN - when other player Join calling playerJoin in lobby
     * QUIT - when other player quits game it quit your game too
     * COLOR - call color Tile in Window
     * NEXT - call nextPlayerTurn in Window
     * PLAYERCOLOR - call setCurrentPlayer in Window
     * WIN - call win in Window
     * Exits when loses connection with Player
     */
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
        }
        exit(-1);
    }

    /**
     * This method gets called from window to send specific info to player
     * @param action message we want to send
     */
    public static void action(String action){
        if (action.startsWith("MOVE") || action.startsWith("STOP") || action.startsWith("TRY") || action.startsWith("NEXT")){
            try{
                out.println(action);
            } catch (Exception e) {}
        }
        else if (action.startsWith("START")){
            switch (playerCount){
                case 2:
                case 3:
                case 4:
                case 6:
                    try{
                        out.println(action+";"+playerCount);
                    } catch (Exception e) {}
                    break;
                default:
                    try{
                        System.out.println("Invalid amount of players!");
                    } catch (Exception e) {}
            }
        }
    }


    /**
     * Main method checking if we pass server IP in args
     * Creates new client and calls method play
     * @param args first argument is a server IP
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1){
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
        Client client = new Client(args[0]);
        client.play();
    }
}
