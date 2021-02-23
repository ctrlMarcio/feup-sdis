package io.github.ctrlMarcio.sdis.lab1.client;

import io.github.ctrlMarcio.sdis.lab1.framework.reply.Reply;
import io.github.ctrlMarcio.sdis.lab1.framework.request.Request;
import io.github.ctrlMarcio.sdis.lab1.framework.request.RequestType;

import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) {
        // verifies the program was called correctly
        if (args.length != 4 && args.length != 5) {
            System.err.println("Incorrect number of arguments.\nCorrect usage: java Client <host> <port> <oper> <opnd>*");
            return;
        }

        // gets the host
        String host = args[0];
        // gets the server port
        int serverPort;
        try {
            serverPort = getServerPort(args);
        } catch(Exception e) {
            return;
        }

        // creates the client
        UDPClient client = UDPClient.builder().host(host).port(serverPort).build();

        Request request = buildRequest(args);
        if (request == null) return;

        try {
            client.open();
            client.send(request);

            // get response
            String replyString = client.receive();
            client.close();

            Reply reply = Reply.fromString(replyString);

            System.out.printf("Client: %s: %s\n", request, reply.toDisplayString());
        } catch (SocketException e) {
            System.err.println("Can't create socket. Error: " + e.getMessage());
        } catch (UnknownHostException e) {
            System.err.println("Host is invalid.");
        } catch (Exception e) {
            System.err.println("Couldn't send the request. Error: " + e.getMessage());
        }
    }

    private static Request buildRequest(String[] args) {
        // gets the DNS
        String DNS = args[3];

        // verifies if the inserted operations are correct
        if (args[2].equals("register")) {
            if (args.length != 5) {
                System.err.printf("Incorrect register number of parameters.\nCorrect usage: java Client %s %s register <DNS name> <IP address>\n", args[0], args[1]);
                return null;
            }

            String IP = args[4];

            return Request.builder().requestType(RequestType.REGISTER).ip(IP).dns(DNS).build();

        } else if (args[2].equals("lookup")) {
            if (args.length != 4) {
                System.err.printf("Incorrect lookup number of parameters.\nCorrect usage: java Client %s %s lookup <DNS name>\n", args[0], args[1]);
                return null;
            }

            return Request.builder().requestType(RequestType.LOOKUP).dns(DNS).build();
        } else {
            System.err.println("Incorrect operation. Should be \"register\" or \"lookup\"");
            return null;
        }
    }

    private static int getServerPort(String[] args) {
        // verifies if the inserted port is in fact a number
        int serverPort;
        try {
            serverPort = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.err.printf("<port> should be a number. %s is not a number\n", args[0]);
            throw e;
        }
        return serverPort;
    }
}
