import javax.imageio.event.IIOReadProgressListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Player implements Runnable {
    private Socket socket;
    Scanner input;
    PrintWriter output;
    GameManager gm;
    int id;

    public Player(Socket socket, GameManager gm, int id, boolean gameStarted) {
        this.socket = socket;
        this.gm=gm;
        gm.players.add(this);
        this.id = id;
//        if (gameStarted){
//            output.println();
//        }
    }

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

    private void setup() throws IOException {
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);
        if(id == 1){
            output.println("HOST");
        }
        else{
            output.println("LOBBY");
        }
    }

    private void processCommands() {
        while(input.hasNextLine()){
            var command = input.nextLine();
            if (command.startsWith("START") || command.startsWith("MOVE")){
                gm.communication(command);
            }
            else if (command.startsWith("STOP")){
            }
            else if (command.startsWith("JOIN")){
                gm.communication(command+";"+id);
            }
            // TODO: 12/18/2021 mechanika -> ogarnianie inputa i wywolywanie gamemanagera
        }
        gm.communication("QUIT;"+id);
    }

    public void sendMessage(String message){
        output.println(message);
    }
}