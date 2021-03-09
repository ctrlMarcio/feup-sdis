package io.github.ctrlMarcio.sdis.lab3.server;

import io.github.ctrlMarcio.sdis.lab3.framework.remote.CommandInterface;
import io.github.ctrlMarcio.sdis.lab3.server.dns.DNSManager;
import io.github.ctrlMarcio.sdis.lab3.server.remote.CommandHandler;
import lombok.Builder;

import java.net.SocketException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    private final DNSManager dnsManager;

    private final CommandHandler commandHandler;

    private final String remoteObjectName;

    @Builder
    public Server(String remoteObjectName) throws SocketException {
        this.dnsManager = new DNSManager();
        this.commandHandler = new CommandHandler(this.dnsManager);
        this.remoteObjectName = remoteObjectName;
    }

    public void registerRemoteCommandHandler() {
        try {
            CommandInterface stub = (CommandInterface) UnicastRemoteObject.exportObject(this.commandHandler, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind(this.remoteObjectName, stub);

            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
