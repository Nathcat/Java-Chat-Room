import java.net.*;
import java.io.*;

public class Server {

    private static ServerSocket serverSocket;

    static {
        try {
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Socket clientSocket;

    static {
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedReader in;


    public String start(int port) throws IOException {
        System.out.println("Server: Waiting for connection");
        String address = clientSocket.getLocalAddress().toString();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        return address + " > " + in.readLine();
    }

    public void stop() throws IOException {
        in.close();
        clientSocket.close();
        serverSocket.close();
    }

}
