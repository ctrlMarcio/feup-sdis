package pt.up.fe.sdis2021.t7g10.lab5.server.thread;

import lombok.AllArgsConstructor;
import pt.up.fe.sdis2021.t7g10.lab5.protocol.*;
import pt.up.fe.sdis2021.t7g10.lab5.server.dns.DNSRecord;
import pt.up.fe.sdis2021.t7g10.lab5.server.dns.DNSRecordManager;

import java.io.IOException;
import java.net.Socket;

@AllArgsConstructor
public class ClientWorkerThread extends Thread {

    private final Socket socket;

    private final DNSRecordManager dnsManager;

    @Override
    public void run() {
        while (!socket.isClosed()) {
            var message = TCPMessages.receive(this.socket);

            if (message.isEmpty()) {
                this.close();
                return;
            }

            TCPMessage tcpMessage;
            if (!((tcpMessage = message.get()) instanceof Command)) {
                System.out.println(tcpMessage);
                continue;
            }

            Command command = (Command) tcpMessage;

            switch (command.getCommandType()) {
                case LOOKUP -> {
                    var lookup = (LookupCommand) command;
                    var record = dnsManager.lookup(lookup.getName());
                    var lookupReply = new Reply(record.isPresent() ? String.format("%s %s", record.get().getIp(), record.get().getName()) : "NOT_FOUND");
                    TCPMessages.send(socket, lookupReply);
                }
                case REGISTER -> {
                    var register = (RegisterCommand) command;
                    dnsManager.register(new DNSRecord(register.getName(), register.getIp()));
                    var registerReply = new Reply(String.format("%s, %s\n%d", register.getName(), register.getIp(),
                            dnsManager.getEntriesAmount()));
                    TCPMessages.send(socket, registerReply);
                }
                default -> {
                    throw new IllegalArgumentException("Invalid command");
                }
            }

            System.out.println("Replied to new client");
        }
    }

    private void close() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.interrupt();
    }
}
