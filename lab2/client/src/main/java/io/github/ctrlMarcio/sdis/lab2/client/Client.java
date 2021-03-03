package io.github.ctrlMarcio.sdis.lab2.client;

import io.github.ctrlMarcio.sdis.lab2.framework.address.Address;
import io.github.ctrlMarcio.sdis.lab2.framework.request.Request;
import io.github.ctrlMarcio.sdis.lab2.framework.request.RequestResponse;
import lombok.Getter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client {
    private final Address serverAddress;

    private DatagramSocket socket;

    public Client(Address serverAddress) {
        this.serverAddress = serverAddress;
    }

    public Client(String host, int serverPort) {
        this.serverAddress = new Address(host, serverPort);
    }

    public void open() throws SocketException {
        this.socket = new DatagramSocket();
    }

    public void close() {
        this.socket.close();
    }

    public RequestResponse send(Request request) throws IOException {
        return request.send(socket, InetAddress.getByName(this.serverAddress.getIp()), this.serverAddress.getPort());
    }
}