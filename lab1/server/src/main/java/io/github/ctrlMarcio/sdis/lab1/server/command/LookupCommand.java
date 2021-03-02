package io.github.ctrlMarcio.sdis.lab1.server.command;

import io.github.ctrlMarcio.sdis.lab1.framework.command.Command;
import io.github.ctrlMarcio.sdis.lab1.framework.response.Response;
import io.github.ctrlMarcio.sdis.lab1.server.dns.DNSManager;

import java.util.Optional;

public class LookupCommand extends Command {

    private final DNSManager dnsManager;

    public LookupCommand(DNSManager dnsManager) {
        super("LOOKUP");

        this.dnsManager = dnsManager;
    }

    @Override
    public Response execute(String... args) {
        if (args.length != 1)
            return Response.builder().returnCode(Response.ERROR_VALUE).content("INVALID_ARGUMENTS_COUNT").build();

        String hostname = args[0];

        Optional<String> ip = dnsManager.lookup(hostname);

        int returnValue = ip.isPresent() ? dnsManager.getEntriesAmount() : Response.ERROR_VALUE;

        return Response.builder().returnCode(returnValue).content(String.join(" ", hostname, ip.orElse("NOT_FOUND"))).build();
    }
}
