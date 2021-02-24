package io.github.ctrlMarcio.sdis.lab1.framework.request;

import lombok.Builder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

@Builder
public class Request {

    @Builder.Default
    protected String method = "";

    @Builder.Default
    protected List<String> args = new ArrayList<>();

    @Builder.Default
    protected String content = "";

    @Builder.Default
    protected boolean hasReply = false;

    public RequestResponse send(DatagramSocket socket, InetAddress address, int port) throws IOException {
        List<String> fields = new ArrayList<>();

        fields.add(method);
        fields.addAll(args);
        fields.add(content);
        fields.removeIf(String::isEmpty);

        String information = String.join(" ", fields);

        byte[] buffer = information.getBytes();

        socket.send(new DatagramPacket(buffer, buffer.length, address, port));

        if (hasReply) {
            byte[] receiveBuffer = new byte[256];

            DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(packet);

            String content = new String(packet.getData(), 0, packet.getLength());

            return RequestResponse.builder().content(content).build();
        }

        return RequestResponse.builder().build();
    }
}
