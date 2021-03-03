package io.github.ctrlMarcio.sdis.lab2.server.command;

import io.github.ctrlMarcio.sdis.lab2.framework.command.Command;
import io.github.ctrlMarcio.sdis.lab2.framework.response.Response;
import io.github.ctrlMarcio.sdis.lab2.server.dns.DNSManager;

public class RegisterCommand extends Command {

    private final DNSManager dnsManager;

    public RegisterCommand(DNSManager dnsManager) {
        super("REGISTER");

        this.dnsManager = dnsManager;
    }

    @Override
    public Response execute(String... args) {
        if (args.length != 2)
            return Response.builder().returnCode(Response.ERROR_VALUE).content("INVALID_ARGUMENTS_COUNT").build();

        String hostname = args[0];
        String ip = args[1];

        int returnValue = this.dnsManager.register(hostname, ip) ? this.dnsManager.getEntriesAmount() :
                Response.ERROR_VALUE;

        return Response.builder().returnCode(returnValue).content(String.join(" ", hostname, ip)).build();
    }
}
