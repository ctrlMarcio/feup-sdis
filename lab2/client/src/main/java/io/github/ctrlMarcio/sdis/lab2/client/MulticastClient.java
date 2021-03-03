package io.github.ctrlMarcio.sdis.lab2.client;

import io.github.ctrlMarcio.sdis.lab2.framework.address.Address;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class MulticastClient {

    private final String multicastHost;

    private final int multicastPort;

    private DatagramSocket socket;

    public MulticastClient(String multicastHost, int multicastPort) {
        this.multicastHost = multicastHost;
        this.multicastPort = multicastPort;
    }

    public void open() throws SocketException {
        this.socket = new DatagramSocket(this.multicastPort);
    }

    public void close() {
        this.socket.close();
    }

    public String receive() throws IOException {
        byte[] buffer = new byte[256];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        socket.receive(packet);

        return new String(packet.getData(), 0, packet.getLength());
    }

    public Address receiveAddress() throws IOException {
        String message = this.receive();
        return new Address(message);
    }
}
