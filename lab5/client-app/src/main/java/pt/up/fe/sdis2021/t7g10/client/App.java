package pt.up.fe.sdis2021.t7g10.client;

import pt.up.fe.sdis2021.t7g10.lab5.protocol.*;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        try {
            var app = new App();

            app.send(new RegisterCommand("www.google.com", "142.250.200.68"));
            app.send(new LookupCommand("www.google.com"));

            app.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final SSLSocket socket;

    public App() throws IOException {
        System.setProperty("javax.net.ssl.keyStore", "./client-app/src/main/resources/client.keys");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
        System.setProperty("javax.net.ssl.trustStore", "./client-app/src/main/resources/truststore");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        var ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        this.socket = (SSLSocket) ssf.createSocket("localhost", 8000);
    }

    public void send(Command command) {
        TCPMessages.send(this.socket, command);

        var receivedMessage = TCPMessages.receive(this.socket);

        if (receivedMessage.isEmpty() || !(receivedMessage.get() instanceof Reply))
            throw new RuntimeException("Something unexpected occurred");

        var reply = (Reply) receivedMessage.get();

        System.out.println(reply);
    }

    public void close() throws IOException {
        this.socket.close();
    }
}
