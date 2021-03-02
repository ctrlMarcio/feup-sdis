package io.github.ctrlMarcio.sdis.lab1.server;

import java.io.IOException;

public class ServerRunner {

    public static void main(String[] args) {
        // verifies the program was called correctly
        if (args.length != 1) {
            System.err.println("Incorrect number of arguments.\nCorrect usage: java Server <port number>");
            return;
        }

        // verifies if the inserted port is in fact a number
        int serverPort;
        try {
            serverPort = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.err.printf("<server port> should be a number. %s is not a number\n", args[0]);
            return;
        }

        // initializes and runs the server
        try {
            Server server = new Server(serverPort);
            server.run();
        } catch (IOException e) {
            System.err.printf("Can't run the server on port %d\n", serverPort);
        }
    }
}
