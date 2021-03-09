package io.github.ctrlMarcio.sdis.lab3.client;

import io.github.ctrlMarcio.sdis.lab3.framework.remote.CommandInterface;
import io.github.ctrlMarcio.sdis.lab3.framework.request.Request;
import io.github.ctrlMarcio.sdis.lab3.framework.response.Response;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private final CommandInterface commandInterface;

    public Client(String host, String remoteObjectName) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host);
        this.commandInterface = (CommandInterface) registry.lookup(remoteObjectName);
    }

    public Response send(Request request) throws RemoteException {
        return request.send(this.commandInterface);
    }
}