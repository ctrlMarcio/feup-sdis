package io.github.ctrlMarcio.sdis.lab2.client;

import io.github.ctrlMarcio.sdis.lab2.framework.address.Address;

import java.io.IOException;
import java.net.*;

public class MulticastClient {

    private final int multicastPort;

    private MulticastSocket socket;

    private final NetworkInterface networkInterface;

    private final InetSocketAddress socketAddress;

    public MulticastClient(String multicastHost, int multicastPort) throws UnknownHostException, SocketException {
        InetAddress group = InetAddress.getByName(multicastHost);

        this.multicastPort = multicastPort;
        this.socketAddress = new InetSocketAddress(group, multicastPort);

        this.networkInterface = NetworkInterface.getByInetAddress(group);
    }

    public void open() throws IOException {
        this.socket = new MulticastSocket(this.multicastPort);
        this.subscribe();
    }

    public void close() throws IOException {
        this.unsubscribe();
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

    private void subscribe() throws IOException {
        socket.joinGroup(this.socketAddress, this.networkInterface);
    }

    private void unsubscribe() throws IOException {
        socket.leaveGroup(this.socketAddress, this.networkInterface);
    }

}
