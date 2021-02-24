package io.github.ctrlMarcio.sdis.lab1.client;

import io.github.ctrlMarcio.sdis.lab1.framework.request.Request;
import io.github.ctrlMarcio.sdis.lab1.framework.request.RequestResponse;
import lombok.Getter;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client {
    @Getter
    private final int port;

    @Getter
    private String host = "localhost";

    private DatagramSocket socket;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void open() throws SocketException {
        this.socket = new DatagramSocket();
    }

    public void close() {
        this.socket.close();
    }

    public RequestResponse send(Request request) throws IOException {
        return request.send(socket, InetAddress.getByName(host), port);
    }
}