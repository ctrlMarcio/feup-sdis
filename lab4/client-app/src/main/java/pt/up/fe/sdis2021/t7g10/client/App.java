package pt.up.fe.sdis2021.t7g10.client;

import pt.up.fe.sdis2021.t7g10.lab4.protocol.Command;
import pt.up.fe.sdis2021.t7g10.lab4.protocol.LookupCommand;
import pt.up.fe.sdis2021.t7g10.lab4.protocol.Reply;
import pt.up.fe.sdis2021.t7g10.lab4.protocol.TCPMessages;

import java.io.IOException;
import java.net.Socket;

public class App {

    public static void main(String[] args) {
        try {
            var app = new App();

            // app.send(new RegisterCommand("www.google.com", "142.250.200.68"));
            app.send(new LookupCommand("www.google.com"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Socket socket;

    public App() throws IOException {
        this.socket = new Socket("localhost", 8000);
    }

    public void send(Command command) {
        TCPMessages.send(this.socket, command);

        var receivedMessage = TCPMessages.receive(this.socket);

        if (receivedMessage.isEmpty() || !(receivedMessage.get() instanceof Reply))
            throw new RuntimeException("Something unexpected occurred");

        var reply = (Reply) receivedMessage.get();

        System.out.println(reply);
    }
}
