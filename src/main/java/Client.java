import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public  class Client {
    private Socket socket;
    private Scanner in;
    private static PrintWriter out;
    Window var1;

    public Client(String serverAddress) throws Exception {
        socket = new Socket(serverAddress, 55555);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);


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
            // TODO: 12/18/2021 tu sie przesyla od playera info jakies i bedzie wysylane do window zeby cos konkretnego wyswietlic
        }
    }

    public static void action(String action){
        if (action.startsWith("MOVE")){
            out.println(action);
        }
        else if (action.startsWith("STOP")){
            out.println("STOP");
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
