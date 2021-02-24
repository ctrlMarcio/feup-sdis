package io.github.ctrlMarcio.sdis.lab1.client;

import io.github.ctrlMarcio.sdis.lab1.framework.request.Request;
import io.github.ctrlMarcio.sdis.lab1.framework.request.RequestResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientRunner {
    public static void main(String[] args) {
        if (args.length != 4 && args.length != 5) {
            System.err.println("Incorrect number of arguments.\nCorrect usage: java Client <host> <port> <oper> " +
                    "<opnd>*");
            return;
        }

        String host = args[0];

        try {
            int serverPort = getServerPort(args);

            Client client = new Client(host, serverPort);

            client.open();

            List<String> argsList = new ArrayList<>(Arrays.asList(args));
            argsList.remove(args[0]);
            argsList.remove(args[1]);
            argsList.remove(args[2]);

            RequestResponse response =
                    client.send(Request.builder().method(args[2].toUpperCase()).args(argsList).hasReply(true).build());

            System.out.println(response.getContent());

            client.close();
        } catch (Exception ignored) {
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
