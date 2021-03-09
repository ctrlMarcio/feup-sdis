package io.github.ctrlMarcio.sdis.lab3.framework.remote;

import io.github.ctrlMarcio.sdis.lab3.framework.response.Response;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CommandInterface extends Remote {

    Response lookup(String address) throws RemoteException;;

    Response register(String address, String ip) throws RemoteException;;
}
