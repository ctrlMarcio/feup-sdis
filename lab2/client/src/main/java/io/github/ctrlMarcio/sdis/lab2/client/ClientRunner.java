package io.github.ctrlMarcio.sdis.lab2.client;

import io.github.ctrlMarcio.sdis.lab2.framework.address.Address;
import io.github.ctrlMarcio.sdis.lab2.framework.request.Request;
import io.github.ctrlMarcio.sdis.lab2.framework.request.RequestResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientRunner {
    public static void main(String[] args) {
        if (args.length != 4 && args.length != 5) {
            System.err.println("Incorrect number of arguments.\nCorrect usage: java Client <mcast_addr> <mcast_port> <oper> <opnd> *");
            return;
        }

        String mcastHost = args[0];

        try {
            int mcastPort = getServerPort(args);
            MulticastClient multicastClient = new MulticastClient(mcastHost, mcastPort);

            multicastClient.open();
            Address serverAddress = multicastClient.receiveAddress();

            Client client = new Client(serverAddress);
            client.open();

            // TODO temporary, just parse it
            RequestResponse response = client.send(Request.builder().method("REGISTER").args(Arrays.asList("www.google.com", "127.0.0.1")).hasReply(true).build());
            System.out.println(response);
//            List<String> argsList = new ArrayList<>(Arrays.asList(args));
//            argsList.remove(args[0]);
//            argsList.remove(args[1]);
//            argsList.remove(args[2]);
//
//            RequestResponse response =
//                    multicastClient.send(Request.builder().method(args[2].toUpperCase()).args(argsList).hasReply(true).build());
//
//            System.out.println(response);

            multicastClient.close();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    private static int getServerPort(String[] args) {
        try {
            return Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.err.printf("<port> should be a number. %s is not a number\n", args[0]);
            throw e;
        }
    }
}
