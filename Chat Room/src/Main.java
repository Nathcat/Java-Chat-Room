import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Main.java---------
 * Main class to create the window and manage the GUI
 * and server and client classes.
 *
 * author: Nathan "Nathcat" Baines
 */

// Main class
public class Main extends Thread {

    // Create server and client objects
    private static Server server;
    private static final Client client = new Client();

    // Create the JFrame object
    private static final JFrame root = new JFrame();

    // Create window components
    private static JTextArea messageDisplayBox;
    private static JTextField messageEntryBox;
    private static JButton sendButton;

    private static JTextField hostEntryBox;
    private static JButton setHostButton;

    private static JScrollPane scroll;

    public void run() {
        while (true) {
            try {
                server = new Server();
                String data = server.start(1234);
                System.out.println("Server: " + data);
                messageDisplayBox.append("\n" + data);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static void setHost(String text) throws IOException {
        root.getContentPane().removeAll();
        root.repaint();
        messagingDisplay(text);
    }

    public static void setHostDisplay() {
        // Create a text field to enter the host
        hostEntryBox = new JTextField();
        hostEntryBox.setBounds(0, 0, 400, 50);

        // Create button to set host
        setHostButton = new JButton("Set host");
        setHostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setHost(hostEntryBox.getText());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        setHostButton.setBounds(400, 0, 100, 50);

        // Add the components to the window
        root.getContentPane().add(hostEntryBox);
        root.getContentPane().add(setHostButton);
    }

    public static void messagingDisplay(String ip) throws IOException {
        // Start the server thread
        Main serverThread = new Main();
        serverThread.start();

        // Connect the client to the host IP address on port 1234
        client.startConnection(ip, 1234);

        // Create the JTextArea that will display the messages
        messageDisplayBox = new JTextArea();
        messageDisplayBox.setEditable(false);
        messageDisplayBox.setLineWrap(true);
        messageDisplayBox.setWrapStyleWord(true);

        // Create a scroll box
        scroll = new JScrollPane(messageDisplayBox);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        scroll.setBounds(0, 0, 500, 450);

        // Create the box to type in messages
        messageEntryBox = new JTextField();
        messageEntryBox.setBounds(0, 450, 400, 50);

        // Create the button to send the message
        sendButton = new JButton("Send");
        sendButton.setBounds(400, 450, 100, 50);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage(messageEntryBox.getText());
                messageDisplayBox.append("\nYOU > " + messageEntryBox.getText());
            }
        });

        // Add the components to root
        root.getContentPane().add(scroll);
        root.getContentPane().add(messageEntryBox);
        root.getContentPane().add(sendButton);

    }

    // main method
    public static void main(String[] args) {

        // Set the close operation for the window
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the title of the window
        root.setTitle("Nathcat's Chat room");

        // Set the size of the window
        root.setSize(500, 520);

        // Set window display
        setHostDisplay();

        // Setup and display window
        root.setLayout(null);
        root.setVisible(true);

    }

}
