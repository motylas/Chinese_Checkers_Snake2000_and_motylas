import javax.imageio.event.IIOReadProgressListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Player implements Runnable {
    private Socket socket;
    Scanner input;
    PrintWriter output;

    public Player(Socket socket, GameManager gm) {
        this.socket = socket;
        gm.players.add(this);
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
        output.println("Welcome to the Game!");
        output.println("Welcome to the Game! l2");
    }

    private void processCommands() {
        while(input.hasNextLine()){
            var command = input.nextLine();
            if(command.startsWith("QUIT")){
                // TODO: 12/19/2021 make quit
            }
            else if (command.startsWith("MOVE")){
                System.out.println("nastapil ruch");
            }
            else if (command.startsWith("STOP")){
                System.out.println("stop");
            }
            // TODO: 12/18/2021 mechanika -> ogarnianie inputa i wywolywanie gamemanagera
        }
    }
}
