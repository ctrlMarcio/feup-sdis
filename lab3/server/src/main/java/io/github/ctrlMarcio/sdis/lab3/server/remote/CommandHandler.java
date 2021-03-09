package io.github.ctrlMarcio.sdis.lab3.server.remote;

import io.github.ctrlMarcio.sdis.lab3.framework.remote.CommandInterface;
import io.github.ctrlMarcio.sdis.lab3.framework.response.Response;
import io.github.ctrlMarcio.sdis.lab3.server.command.LookupCommand;
import io.github.ctrlMarcio.sdis.lab3.server.command.RegisterCommand;
import io.github.ctrlMarcio.sdis.lab3.server.dns.DNSManager;

public class CommandHandler implements CommandInterface {

    private final LookupCommand lookupCommand;

    private final RegisterCommand registerCommand;

    public CommandHandler(DNSManager dnsManager) {
        this.lookupCommand = new LookupCommand(dnsManager);
        this.registerCommand = new RegisterCommand(dnsManager);
    }

    @Override
    public Response lookup(String address) {
        return lookupCommand.execute(address);
    }

    @Override
    public Response register(String address, String ip) {
        return registerCommand.execute(address, ip);
    }
}
