package io.github.ctrlMarcio.sdis.lab1.server;

import io.github.ctrlMarcio.sdis.lab1.request.Reply;
import io.github.ctrlMarcio.sdis.lab1.request.Request;
import lombok.Builder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {

    private final DatagramSocket socket;

    private DNSManager dnsManager = new DNSManager();

    private boolean running = false;

    @Builder
    public UDPServer(int port) throws SocketException {
        this.socket = new DatagramSocket(port);
    }

    public void run() {
        this.running = true;

        while (this.running) {
            try {
                // gets the request
                DatagramPacket packet = getRequest();

                // processes the request
                Request request = Request.fromPacket(packet);
                System.out.printf("Server: %s\n", request.toString());
                Reply reply = this.processRequest(request);

                // reply to client
                assert reply != null;
                sendAnswer(reply.toString(), packet);
            } catch (IOException e) {
                e.printStackTrace();
                this.running = false;
            }
        }
        socket.close();
    }

    private DatagramPacket getRequest() throws IOException {
        byte[] buf = new byte[Request.MAX_LENGTH];

        // get client request
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        return packet;
    }

    private Reply processRequest(Request request) {
        int returnValue;

        switch (request.getRequestType()) {
            case REGISTER:

                boolean done = this.dnsManager.register(request.getDns(), request.getIp());
                if (done)
                    returnValue = this.dnsManager.getEntriesAmount() - 1;
                else
                    returnValue = Reply.ERROR_VALUE;

                return Reply.builder()
                        .returnValue(returnValue)
                        .dns(request.getDns())
                        .ip(request.getIp())
                        .build();

            case LOOKUP:
                String ip = this.dnsManager.lookup(request.getDns());

                System.out.println(ip);

                if (ip == null)
                    returnValue = Reply.ERROR_VALUE;
                else
                    returnValue = this.dnsManager.getEntriesAmount();

                return Reply.builder()
                        .returnValue(returnValue)
                        .dns(request.getDns())
                        .ip(ip)
                        .build();

            default:
                // won't happen
                return null;
        }
    }

    private void sendAnswer(String answer, DatagramPacket packet) throws IOException {
        byte[] buf = answer.getBytes();
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        DatagramPacket sentPacket = new DatagramPacket(buf, buf.length, address, port);
        socket.send(sentPacket);
    }
}
