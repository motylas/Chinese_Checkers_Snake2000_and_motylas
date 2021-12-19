import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public  class Client {
    private Socket socket;
    private Scanner in;
    private static PrintWriter out;

    public Client(String serverAddress) throws Exception {
        socket = new Socket(serverAddress, 55555);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);


        // TODO: 12/19/2021 temporary tworzenie 
        Window var1 = new Window();
    }

    private void play(){
        String response;
        while(in.hasNextLine()){
            response = in.nextLine();

            // TODO: 12/18/2021 tu sie przesyla od playera info jakies i bedzie wysylane do window zeby cos konkretnego wyswietlic
        }
    }

    public static void action(String action){
        if (action.startsWith("MOVE")){
            out.println("MOVE");
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
