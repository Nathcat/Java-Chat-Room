import java.net.*;
import java.io.*;

public class Client {

    private Socket clientSocket;
    private PrintWriter out;

    public void startConnection(String ip, int port) throws IOException{
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);

    }

    public void sendMessage(String msg) {
        out.println(msg);

    }

    public void stopConnection() throws IOException {
        out.close();
        clientSocket.close();

    }
}
