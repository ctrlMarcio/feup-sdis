package io.github.ctrlMarcio.sdis.server.command;

import io.github.ctrlMarcio.sdis.lab1.framework.command.Command;
import io.github.ctrlMarcio.sdis.lab1.framework.reply.Reply;
import io.github.ctrlMarcio.sdis.lab1.framework.request.Request;
import io.github.ctrlMarcio.sdis.server.dns.DNSManager;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Optional;

public class LookupCommand extends Command {

    private final DNSManager dnsManager;

    public LookupCommand(DNSManager dnsManager) {
        super("LOOKUP");

        this.dnsManager = dnsManager;
    }

    @Override
    public void execute(DatagramSocket socket, InetAddress sender, int senderPort, String... args) throws IOException {
        if (args.length != 1) {
            Request.builder().content("INVALID_ARGUMENTS_COUNT").hasReply(false).build().send(socket, sender,
                    senderPort);
            return;
        }

        String hostname = args[0];

        Optional<String> ip = dnsManager.lookup(hostname);

        int returnValue = ip.isPresent() ? dnsManager.getEntriesAmount() : Reply.ERROR_VALUE;

        String replyContent = Reply.builder()
                .returnValue(returnValue)
                .dns(hostname)
                .ip(ip.orElse("NOT_FOUND"))
                .build().toDisplayString();

        Request.builder().content(replyContent).hasReply(false).build().send(socket, sender,
                senderPort);
    }
}
