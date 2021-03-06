package io.github.ctrlMarcio.sdis.lab2.client;

import io.github.ctrlMarcio.sdis.lab2.framework.address.Address;
import io.github.ctrlMarcio.sdis.lab2.framework.request.Request;
import io.github.ctrlMarcio.sdis.lab2.framework.request.RequestResponse;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientRunner {
    public static void main(String[] args) {
        if (args.length != 4 && args.length != 5) {
            System.err.println("Incorrect number of arguments.\nCorrect usage: java Client <mcast_addr> <mcast_port> <oper> <opnd> *");
            return;
        }

        try {
            String multicastHost = args[0];
            int multicastPort = getServerPort(args);

            Address serviceAddress = getServiceAddress(multicastHost, multicastPort);

            RequestResponse response = sendServiceRequest(serviceAddress, args);

            System.out.println(response);
        } catch (Exception exception) {
            exception.printStackTrace();
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

    private static Address getServiceAddress(String multicastAddress, int multicastPort) throws IOException {
        MulticastClient multicastClient = new MulticastClient(multicastAddress, multicastPort);

        multicastClient.open();
        Address serverAddress = multicastClient.receiveAddress();
        multicastClient.close();

        return serverAddress;
    }

    private static RequestResponse sendServiceRequest(Address serviceAddress, String... args) throws IOException {
        int methodIndex = 2;

        String method = args[methodIndex];
        String[] requestArgs = new String[args.length - (methodIndex + 1)];

        for (int i = 0; i < requestArgs.length; ++i)
            requestArgs[i] = args[methodIndex + i + 1];

        Client client = new Client(serviceAddress);
        client.open();

        RequestResponse response = client.send(Request.builder().method(method).args(Arrays.asList(requestArgs)).hasReply(true).build());

        client.close();

        return response;
    }
}
