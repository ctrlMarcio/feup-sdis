package pt.up.fe.sdis2021.t7g10.lab5.protocol;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

public final class TCPMessages {

    public TCPMessages() {
    }

    public static void send(Socket socket, TCPMessage message) {
        try {
            socket.getOutputStream().write(TCPMessage.serialize(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Optional<TCPMessage> receive(Socket socket) {
        try {
            TCPMessage tcpMessage = (TCPMessage) TCPMessage.deserialize(socket.getInputStream());

            return Optional.of(tcpMessage);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
