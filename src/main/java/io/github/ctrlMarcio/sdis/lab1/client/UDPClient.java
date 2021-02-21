package io.github.ctrlMarcio.sdis.lab1.client;

import io.github.ctrlMarcio.sdis.lab1.request.Request;
import lombok.Builder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient {

    private final int port;

    @Builder.Default
    private String host = "localhost";

    private DatagramSocket socket;

    @Builder
    public UDPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void open() throws SocketException {
        this.socket = new DatagramSocket();
    }

    public void close() {
        this.socket.close();
    }

    public void send(Request request) throws IOException {
        InetAddress address = InetAddress.getByName(host);
        byte[] buf = request.toString().getBytes();

        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        this.socket.send(packet);
    }

    public String receive() throws IOException {
        byte[] buf = new byte[Request.MAX_LENGTH];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        this.socket.receive(packet);

        return new String(packet.getData(), 0, packet.getLength());
    }
}
