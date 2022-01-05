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

    public Player(Socket socket, GameManager gm, int id) {
        this.socket = socket;
        this.gm=gm;
        gm.players.add(this);
        this.id = id;
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
    }

    private void processCommands() {
        while(input.hasNextLine()){
            var command = input.nextLine();
            if(command.startsWith("QUIT")){
                // TODO: 12/19/2021 make quit
            }
            else if (command.startsWith("MOVE")){
                gm.communication(command);
            }
            else if (command.startsWith("STOP")){
            }
            // TODO: 12/18/2021 mechanika -> ogarnianie inputa i wywolywanie gamemanagera
        }
        System.out.println("halo");
        Server.playerLeft();
    }

    public void sendMessage(String message){
        output.println(message);
    }
}
