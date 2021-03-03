package io.github.ctrlMarcio.sdis.lab2.framework.response;

import lombok.Builder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Builder
public class Response {

    public static final int ERROR_VALUE = -1;

    @Builder.Default
    protected int returnCode = 0;

    @Builder.Default
    protected String content = "";

    public void send(DatagramSocket socket, InetAddress address, int port) throws IOException {
        String information = String.format("%d\n%s", returnCode, content);

        byte[] buffer = information.getBytes();

        socket.send(new DatagramPacket(buffer, buffer.length, address, port));
    }
}
