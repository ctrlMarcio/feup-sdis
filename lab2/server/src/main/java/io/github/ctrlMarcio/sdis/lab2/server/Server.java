package io.github.ctrlMarcio.sdis.lab2.server;

import io.github.ctrlMarcio.sdis.lab2.framework.command.CommandManager;
import io.github.ctrlMarcio.sdis.lab2.framework.response.Response;
import io.github.ctrlMarcio.sdis.lab2.server.command.LookupCommand;
import io.github.ctrlMarcio.sdis.lab2.server.command.RegisterCommand;
import io.github.ctrlMarcio.sdis.lab2.server.dns.DNSManager;
import io.github.ctrlMarcio.sdis.lab2.server.task.MulticastTask;
import lombok.Builder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

public class Server {

    private final int port;

    private final DatagramSocket privateSocket;

    private final DNSManager dnsManager = new DNSManager();

    private final CommandManager commandManager = new CommandManager();

    private boolean running = false;

    @Builder
    public Server(int port) throws SocketException {
        this.port = port;
        this.privateSocket = new DatagramSocket(this.port);

        this.commandManager.add(new LookupCommand(this.dnsManager));
        this.commandManager.add(new RegisterCommand(this.dnsManager));
    }

    public void run() {
        this.running = true;

        while (this.running) {
            try {
                byte[] buffer = new byte[256];

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                privateSocket.receive(packet);

                String information = new String(packet.getData(), 0, packet.getLength());

                String[] fields = information.split("\\s+");

                List<String> argsList = new ArrayList<>(Arrays.asList(fields));
                argsList.remove(0);

                String[] args = new String[argsList.size()];

                args = argsList.toArray(args);

                System.out.printf("Packet received: %s\n", information);

                Response response = commandManager.execute(fields[0], args);

                response.send(privateSocket, packet.getAddress(), packet.getPort());
            } catch (IOException e) {
                e.printStackTrace();
                this.running = false;
            }
        }

        privateSocket.close();
    }
}
