import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Player class connecting with client
 * Also sends and receives messages from GameManager
 */
public class Player implements Runnable {
    /**
     * Typical socket
     */
    private Socket socket;
    /**
     * Input from client
     */
    Scanner input;
    /**
     * Output sending to client
     */
    PrintWriter output;
    /**
     * GameManager for current game
     * Same for all players
     */
    GameManager gm;
    /**
     * Player id
     */
    int id;
    /**
     * Boolean checking whether game Started or not
     */
    boolean gameStarted;
    /**
     * Boolean responisble for checking if player connected to the game
     */
    boolean isDead = false;

    /**
     * Constructor for player
     * @param socket socket we use to communicate
     * @param gm current gameManager
     * @param id invidual id
     * @param gameStarted if game started -> true
     */
    public Player(Socket socket, GameManager gm, int id, boolean gameStarted) {
        this.socket = socket;
        this.gm=gm;
        gm.players.add(this);
        this.id = id;
        this.gameStarted = gameStarted;
    }

    /**
     * run method used for class implementin runnable
     * It's called when we call execute() in server class
     */
    @Override
    public void run() {
        try{
            setup();
            processCommands();
        } catch (Exception e ){
            e.printStackTrace();
        }
        try{
            socket.close();
        } catch (IOException ignored){
        }
    }

    /**
     * Setting up a thread, declaring input, output
     * Sending message to client to create a specific type of lobby
     * @throws IOException
     */
    private void setup() throws IOException {
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);
        if (gameStarted){
            output.println("QUIT;"+-1);
            isDead = true;
        }
        if(id == 1){
            output.println("HOST");
        }
        else{
            output.println("LOBBY");
        }
    }

    /**
     * This method gets input from client and do things on specific action
     * START - calling method in gameManager to start a Game
     * MOVE - calling method in gameManager to send messages that client has moved
     * JOIN - calling method in gameManager to send messages that new player hsa joined
     * TRY - calling method in gameManager to handle player movemnt
     * NEXT - calling method in gameManager to get to next turn
     * When player loses connection or quit game player call method in gameManager to end a game
     */
    private void processCommands() {
        while(input.hasNextLine()){
            var command = input.nextLine();
            if (command.startsWith("START")){
                Server.gameStarted();
                gm.startGame(command);
            }
            else if (command.startsWith("MOVE")){
                gm.communication(command);
            }
            else if (command.startsWith("JOIN")){
                gm.communication(command+";"+id);
            }
            else if (command.startsWith("TRY")){
                gm.moveHandler(command, this);
            }
            else if(command.startsWith("NEXT")){
                gm.nextTurn(command, this);
            }
        }
        if(!isDead){
            gm.communication("QUIT;"+id);
        }
    }

    /**
     * Send message to client
     * @param message message we want to send
     */
    public void sendMessage(String message){
        try{
            output.println(message);
        } catch (Exception e){

        }
    }

    /**
     * Get id of this player
     * @return this player's id
     */
    public int getId() {
        return id;
    }
}