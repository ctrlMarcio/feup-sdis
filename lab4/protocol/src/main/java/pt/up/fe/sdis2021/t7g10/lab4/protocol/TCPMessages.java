package pt.up.fe.sdis2021.t7g10.lab4.protocol;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
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
            byte[] bytes = new byte[80000];

            int bytesRead = socket.getInputStream().read(bytes);

            if (bytesRead < 0)
                return Optional.empty();

            byte[] trimmed = Arrays.copyOfRange(bytes, 0, bytesRead);

            TCPMessage tcpMessage = (TCPMessage) TCPMessage.deserialize(trimmed);

            return Optional.of(tcpMessage);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
