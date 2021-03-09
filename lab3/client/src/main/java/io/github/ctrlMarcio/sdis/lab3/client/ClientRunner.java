package io.github.ctrlMarcio.sdis.lab3.client;

import io.github.ctrlMarcio.sdis.lab3.framework.request.Request;
import io.github.ctrlMarcio.sdis.lab3.framework.response.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientRunner {
    public static void main(String[] args) {
        if (args.length != 4 && args.length != 5) {
            System.err.println("Incorrect number of arguments.\nCorrect usage: java Client <host> <remote object name> <oper> " +
                    "<opnd>*");
            return;
        }

        String host = args[0];
        String remoteObjectName = args[1];
        String method = args[2].toUpperCase();

        try {
            Client client = new Client(host, remoteObjectName);

            List<String> argsList = new ArrayList<>(Arrays.asList(args));
            argsList.remove(args[0]);
            argsList.remove(args[1]);
            argsList.remove(args[2]);

            Response response = client.send(Request.builder().method(method).args(argsList).build());

            System.out.println(response);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}
