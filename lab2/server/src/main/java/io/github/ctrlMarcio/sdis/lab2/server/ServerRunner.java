package io.github.ctrlMarcio.sdis.lab2.server;

import java.io.IOException;

public class ServerRunner {

    public static void main(String[] args) {
        // verifies the program was called correctly
        if (args.length != 3) {
            System.err.println("Incorrect number of arguments.\nCorrect usage: java Server <srvc_port> <mcast_addr> <mcast_port>");
            return;
        }

        // verifies if the inserted port is in fact a number
        int localPort;
        try {
            localPort = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.err.printf("<server port> should be a number. %s is not a number\n", args[0]);
            return;
        }

        // verifies if the multicast port is in fact a number
        int mcastPort;
        try {
            mcastPort = Integer.parseInt(args[2]);
        } catch (Exception e) {
            System.err.printf("<server port> should be a number. %s is not a number\n", args[0]);
            return;
        }

        // initializes and runs the server
        String mcastAddress = args[1];
        try {
            MulticastServer server = new MulticastServer(localPort, mcastAddress, mcastPort);
            server.run();
        } catch (IOException e) {
            System.err.printf("Can't run the server on port %d\n", localPort);
        }
    }
}
