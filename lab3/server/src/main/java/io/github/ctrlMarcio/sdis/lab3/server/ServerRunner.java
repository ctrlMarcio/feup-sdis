package io.github.ctrlMarcio.sdis.lab3.server;

import java.io.IOException;

public class ServerRunner {

    public static void main(String[] args) {
        // verifies the program was called correctly
        if (args.length != 1) {
            System.err.println("Incorrect number of arguments.\nCorrect usage: java Server <remote object name>");
            return;
        }

        // verifies if the inserted port is in fact a number
        String remoteObjectName;
        remoteObjectName = args[0];

        // initializes and runs the server
        try {
            Server server = new Server(remoteObjectName);
            server.registerRemoteCommandHandler();
        } catch (IOException e) {
            System.err.println("Can't run the server");
        }
    }
}
