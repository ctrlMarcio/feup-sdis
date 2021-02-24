package io.github.ctrlMarcio.sdis.server;

import io.github.ctrlMarcio.sdis.lab1.framework.command.CommandManager;
import io.github.ctrlMarcio.sdis.lab1.framework.request.Request;
import io.github.ctrlMarcio.sdis.server.command.LookupCommand;
import io.github.ctrlMarcio.sdis.server.command.RegisterCommand;
import io.github.ctrlMarcio.sdis.server.dns.DNSManager;
import lombok.Builder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {

    private final DatagramSocket socket;

    private final DNSManager dnsManager = new DNSManager();

    private final CommandManager commandManager = new CommandManager();

    private boolean running = false;

    @Builder
    public Server(int port) throws SocketException {
        this.socket = new DatagramSocket(port);
        this.commandManager.add(new LookupCommand(this.dnsManager));
        this.commandManager.add(new RegisterCommand(this.dnsManager));
    }

    public void run() {
        this.running = true;

        while (this.running) {
            try {
                byte[] buffer = new byte[256];

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String information = new String(packet.getData(), 0, packet.getLength());

                String[] fields = information.split("\\s+");

                List<String> argsList = new ArrayList<>(Arrays.asList(fields));
                argsList.remove(0);

                String[] args = new String[argsList.size()];

                args = argsList.toArray(args);

                System.out.printf("Packet received: %s\n", information);

                if (!commandManager.execute(fields[0], socket, packet.getAddress(), packet.getPort(), args)) {
                    Request.builder().content("INVALID_METHOD").build().send(socket, packet.getAddress(),
                            packet.getPort());
                }
            } catch (IOException e) {
                e.printStackTrace();
                this.running = false;
            }
        }
        socket.close();
    }
}
