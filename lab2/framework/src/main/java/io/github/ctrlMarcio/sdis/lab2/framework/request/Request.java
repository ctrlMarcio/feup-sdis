package io.github.ctrlMarcio.sdis.lab2.framework.request;

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
        String header = String.join(" ", method, String.join(" ", args));
        String request = !content.isEmpty() ? String.join("\n", header, content) : header;

        byte[] buffer = request.getBytes();

        socket.send(new DatagramPacket(buffer, buffer.length, address, port));

        if (hasReply) {
            byte[] receiveBuffer = new byte[256];

            DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(packet);

            String content = new String(packet.getData(), 0, packet.getLength());

            return RequestResponse.fromString(content);
        }

        return RequestResponse.builder().build();
    }
}
