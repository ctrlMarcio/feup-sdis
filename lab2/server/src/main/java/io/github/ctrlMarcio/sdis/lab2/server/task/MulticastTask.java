package io.github.ctrlMarcio.sdis.lab2.server.task;

import io.github.ctrlMarcio.sdis.lab2.framework.request.Request;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.util.TimerTask;

public class MulticastTask extends TimerTask {

    private final DatagramSocket socket;

    private final String multicastAddress;

    private final int multicastPort;

    private final String host;

    private final int port;

    public MulticastTask(String multicastAddress, int multicastPort, String host, int port) throws SocketException {
        this.socket = new DatagramSocket();
        this.multicastAddress = multicastAddress;
        this.multicastPort = multicastPort;
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(this.multicastAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }

        String message = String.format("%s:%d", host, port);
        byte[] buffer = message.getBytes();

        try {
            socket.send(new DatagramPacket(buffer, buffer.length, address, this.multicastPort));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
